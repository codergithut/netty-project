package netty.tianjian.common.util.proxy;

import netty.tianjian.common.util.proxy.model.DBQuery;
import netty.tianjian.common.util.proxy.model.IDBQuery;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/6
 * @description
 */
public class CglibDbQueryInterceptor implements MethodInterceptor {
    IDBQuery real = null;

    /**
     * 处理代理逻辑的切入类
     */
    public Object intercept(Object arg0, Method arg1, Object[] arg2,
                            MethodProxy arg3) throws Throwable {
        if (real == null) {    //代理类 的内部逻辑
            real = new DBQuery();
            return real.request();
        }
        return null;
    }

    /**
     * 生成动态代理
     * @return
     */
    public static IDBQuery createCglibProxy(){
        Enhancer enhancer = new Enhancer();
        //指定切入器，定义代理类逻辑
        enhancer.setCallback(new CglibDbQueryInterceptor());
        //指定实现的接口
        enhancer.setInterfaces(new Class[]{IDBQuery.class});

        IDBQuery cglibProxy = (IDBQuery) enhancer.create();
        return cglibProxy;
    }

}