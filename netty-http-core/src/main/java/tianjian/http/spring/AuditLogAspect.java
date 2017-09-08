package tianjian.http.spring;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import netty.tianjian.log.annotation.AuditLog;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import tianjian.http.model.Model;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Aspect
public class AuditLogAspect {

    private ExecutorService executor = Executors.newSingleThreadExecutor();
    private final static String ANONYMOUS_USER_ID = "-999";
    private final static String ANONYMOUS_USER_NAME = "匿名用户";
    //登录用户实际ip
    public static final String LOGIN_USER_IP = "login_user_ip";
    public static final String LOGIN_USER_MAC = "login_user_mac";
    public static final String LOGIN_USER_COMPUTERNAME = "login_user_computername";

    @Pointcut("@annotation(netty.tianjian.log.annotation.AuditLog)")
    public void auditLogServiceAspect() {
    }

    @Before("auditLogServiceAspect()")
    public void doBefore(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        AuditLog auditMethodLog = method.getAnnotation(AuditLog.class);
        auditLog(getAuditContent(joinPoint, auditMethodLog));
    }

    @After("auditLogServiceAspect()")
    public void doAfter(JoinPoint joinPoint) {

    }

    private void auditLog(String content) {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(content);
            }
        });
    }

    private AuditLog getMethodLog(JoinPoint joinPoint) throws Exception {
        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class[] clazzs = method.getParameterTypes();
                    if (clazzs.length == arguments.length) {
                        return method.getAnnotation(AuditLog.class);
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            throw new Exception(e.getMessage());
        }

        return null;
    }

    private String getAuditContent(JoinPoint joinPoint, AuditLog auditLog) {
        Map contentMap = Maps.newHashMap();
        String description = null;
        if (StringUtils.isNotBlank(auditLog.description())) {
            description = auditLog.description();
        } else
            description = "操作内容";
        contentMap.put(description, filterArguments(joinPoint.getArgs()));
        return JSON.toJSONString(contentMap);
    }

    /**
     * 过滤掉一些无关的参数
     *
     * @param args
     * @return
     */
    private Object filterArguments(Object[] args) {
        List<Object> arguments = Lists.newArrayList();
        for (Object arg : args) {
            if (arg instanceof Model)
                continue;
            arguments.add(arg);
        }
        return arguments;
    }

    /**
     * @param
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @version 1.0, 2016/7/29
     * @description 根据SPEL表达式获取注解中的参数值
     */
    private String parseUserId(String userId, Method method, Object[] args) {
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] paraNameArr = u.getParameterNames(method);

        //使用SPEL进行key的解析
        ExpressionParser parser = new SpelExpressionParser();
        //SPEL上下文
        StandardEvaluationContext context = new StandardEvaluationContext();
        //把方法参数放入SPEL上下文中
        for (int i = 0; i < paraNameArr.length; i++) {
            context.setVariable(paraNameArr[i], args[i]);
        }
        return parser.parseExpression(userId).getValue(context, String.class);
    }

    /**
     * @param joinPoint 注解点
     * @return
     * @author <a href="mailto:shenjian@gtmap.cn">shenjian</a>
     * @version 1.0, 2016/7/29
     * @description 返回注解的方法对象
     */
    private Method getMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Class[] argTypes = new Class[joinPoint.getArgs().length];
        for (int i = 0; i < args.length; i++) {
            argTypes[i] = args[i].getClass();
        }
        Method method = null;
        try {
            method = joinPoint.getTarget().getClass().getMethod(joinPoint.getSignature().getName(), argTypes);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return method;
    }
}

