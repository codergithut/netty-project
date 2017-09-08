package netty.tianjian.log.annotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/1/3
 * @description
 */
@Component
@Aspect
@Async
public class HandleAspect {
    long start=0;
    long end=0;
    @Pointcut(value = "@annotation(AopLog)")
    private void cut() { }

    @Before("cut()")
    public void getStartSystemTime(JoinPoint joinPoint){
        start=System.currentTimeMillis();
    }

    @AfterReturning("cut()")
    public void getEndSystemTime(JoinPoint joinPoint){
        end=System.currentTimeMillis();
        System.out.println(joinPoint.getSignature().getDeclaringTypeName()+ "." + joinPoint.getSignature().getName()+"执行时间"+(end-start)/1000+"s");
    }

    @Around("cut()")
    public void advice(ProceedingJoinPoint joinPoint){
        System.out.println("环绕通知之开始");
        try {
            joinPoint.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        }
        System.out.println("环绕通知之结束");
    }

}
