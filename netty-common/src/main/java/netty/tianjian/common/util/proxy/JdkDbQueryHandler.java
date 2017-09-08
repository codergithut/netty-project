package netty.tianjian.common.util.proxy;

import netty.tianjian.common.util.proxy.model.DBQuery;
import netty.tianjian.common.util.proxy.model.IDBQuery;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/6
 * @description
 */
public class JdkDbQueryHandler implements InvocationHandler {

    IDBQuery real = null; //主题接口

    /**
     * 生成Handler
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        if(real == null)
            real = new DBQuery(); //如果是第一次调用，则生成真实对象
        return real.request();  //使用真实主题完成实际的操作
    }

    /**
     * 利用Handler生成动态代理对象
     * @return
     */
    public static IDBQuery createJdkProxy(){

        //根据指定的类加载器和接口以及截获器，返回代理类的一个实例对象
        //ClassLoader loader :指定被代理对象的类加载器
        //Class[] Interfaces ：指定被代理对象所以事项的接口
        //InvocationHandler h :指定需要调用的InvocationHandler对象
        IDBQuery jdkProxy = (IDBQuery) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{IDBQuery.class}, new JdkDbQueryHandler());
        return jdkProxy;
    }


}