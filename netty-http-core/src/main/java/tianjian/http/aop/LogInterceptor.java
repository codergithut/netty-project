package tianjian.http.aop;

import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/6
 * @description
 */
@Aspect
@Component
public class LogInterceptor {

    @Pointcut("execution(public * tianjian.http.aop.service..*.add(..))")
    public void myMethod(){}

    /*@Before("execution(public void com.bjsxt.dao.impl.UserDAOImpl.save(com.bjsxt.model.User))")*/
    @Before("myMethod()")
    public void before() {
        System.out.println("method staet");
    }
    @After("myMethod()")
    public void after() {
        System.out.println("method after");
    }
    @AfterReturning("execution(public * tianjian.http.aop.dao..*.*(..))")
    public void AfterReturning() {
        System.out.println("method AfterReturning");
    }
    @AfterThrowing("execution(public * tianjian.http.aop.dao..*.*(..))")
    public void AfterThrowing() {
        System.out.println("method AfterThrowing");
    }
}

