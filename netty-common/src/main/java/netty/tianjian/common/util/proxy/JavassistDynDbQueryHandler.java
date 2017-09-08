package netty.tianjian.common.util.proxy;

import javassist.*;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import netty.tianjian.common.util.proxy.model.DBQuery;
import netty.tianjian.common.util.proxy.model.IDBQuery;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/6
 * @description
 */
public class JavassistDynDbQueryHandler implements MethodHandler {

    IDBQuery real = null;

    /*
     * (non-Javadoc) 实现用于代理逻辑处理的Handler
     * @see javassist.util.proxy.MethodHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.reflect.Method, java.lang.Object[])
     */
    public Object invoke(Object arg0, Method arg1, Method arg2, Object[] arg3)
            throws Throwable {
        if (real == null)
            real = new DBQuery();
        return real.request();

    }

    /**
     * 创建动态代理
     * @return
     * @throws Exception
     */
    public static IDBQuery createJavassistDynProxy() throws Exception{
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setInterfaces(new Class[]{IDBQuery.class});  //指定接口
        Class proxyClass = proxyFactory.createClass();
        IDBQuery javassistProxy = (IDBQuery) proxyClass.newInstance(); //设置Handler处理器
        ((ProxyObject) javassistProxy).setHandler(new JavassistDynDbQueryHandler());
        return javassistProxy;
    }

    /**
     * 运行时生成业务逻辑
     * @return
     * @throws Exception
     */
    public static IDBQuery createJavassistBytecodeDynamicProxy() throws Exception{
        ClassPool mPool = new ClassPool(true);
        //定义类名
        CtClass mCtc = mPool.makeClass(IDBQuery.class.getName() + "JavaassistBytecodeProxy");
        //需要实现接口
        mCtc.addInterface(mPool.get(IDBQuery.class.getName()));
        //添加构造函数
        mCtc.addConstructor(CtNewConstructor.defaultConstructor(mCtc));

        //添加类的字段信息，使用动态Java代码
        mCtc.addField(CtField.make("public" + IDBQuery.class.getName() + "real;", mCtc));
        String dbQueryname = DBQuery.class.getName();
        //添加方法，这里使用动态Java代码指定内部逻辑
        mCtc.addMethod(CtNewMethod.make("public String request() { if(real==null) real = new " +dbQueryname+"(); return real.request();}", mCtc));

        //基于以上信息，生成动态类
        Class pc = mCtc.toClass();
        //生成动态类的实例
        IDBQuery bytecodeProxy = (IDBQuery) pc.newInstance();
        return bytecodeProxy;
    }

}
