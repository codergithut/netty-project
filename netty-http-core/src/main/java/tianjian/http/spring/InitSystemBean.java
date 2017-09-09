package tianjian.http.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import tianjian.http.controller.BaseControllerHandle;
import tianjian.http.controller.handle.UrlHandleDetail;
import tianjian.http.controller.handle.model.FunctionMeta;
import tianjian.http.controller.handle.model.ViewMeta;
import tianjian.http.filter.impl.IegalUrlFilter;
import tianjian.http.metadata.annotation.RequestMapping;
import tianjian.http.metadata.annotation.ResponseBody;
import tianjian.http.metadata.annotation.RestController;
import tianjian.http.netty.core.contain.common.ChatServer;
import tianjian.http.util.MyBeanUtils;
import tianjian.http.util.ReflectUtil;
import tianjian.http.view.ViewEngineManage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/1
 * @description
 */
@Service
public class InitSystemBean implements ApplicationListener<ContextRefreshedEvent> {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 线程执行器
     */
    private ExecutorService executor = Executors.newCachedThreadPool();

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description controller中心组件
     */
    @Autowired
    BaseControllerHandle baseControllerHandle;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 视图组件
     */
    @Autowired
    ViewEngineManage viewEngineManage;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 合法url收集器
     *
     */
    @Autowired
    IegalUrlFilter iegalUrlFilter;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description http服务模拟
     */
    @Autowired
    ChatServer chatServer;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param contextRefreshedEvent spring上下文环境
     * @return void
     * @description spring容器启动执行组装mvc套件并启动测试服务
     */
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        Logger logger = LoggerFactory.getLogger(InitSystemBean.class);

        logger.error("this is my test logger");

        Map<String,Object> controllerContents = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(Controller.class);

        InitControllerParam(controllerContents.entrySet(), "common");

        Map<String,Object> restControllerContents = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(RestController.class);

        InitControllerParam(restControllerContents.entrySet(), "rest");

        executor.execute(
                new Thread() {
                    public void run() {
                        chatServer.run(null);
                    }
                }
        );


    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param controllerSets 需要注入的包含@Controller 或 @RestController 类对象
     * @param controllerType Rest 风格， common 风格
     * @return void
     * @description 系统根据注解初始化当前项目资源组件
     */
    private void InitControllerParam(Set<Map.Entry<String, Object>> controllerSets, String controllerType) {
        for(Map.Entry<String, Object> value : controllerSets) {

            Object o = value.getValue();

            Method[] methods = o.getClass().getMethods();

            for(Method method : methods) {
                Annotation[] annotations = method.getAnnotations();
                if(annotations != null) {
                    for(Annotation annotation : annotations) {

                        if(annotation instanceof RequestMapping) {
                            UrlHandleDetail urlMetatData = MyBeanUtils.getBean("urlHandleDetail");

                            urlMetatData.setViewEngineManage(viewEngineManage);

                            ViewMeta viewMeta = new ViewMeta();

                            FunctionMeta functionMeta = new FunctionMeta();

                            urlMetatData.setFunctionMeta(functionMeta);

                            urlMetatData.setViewMeta(viewMeta);

                            RequestMapping requestMapping = (RequestMapping) annotation;
                            functionMeta.setRequestMapping(requestMapping);
                            functionMeta.setFunctionName(method.getName());
                            functionMeta.setController(o);

                            Map<String, Class> data = ReflectUtil.getParamsInfo(method);
                            functionMeta.setParams(data);
                            functionMeta.setMethod(method);

                            if(controllerType.equals("common")) {

                                Annotation response = method.getAnnotation(ResponseBody.class);

                                if(response != null) {
                                    functionMeta.setReturnType(true);
                                } else {
                                    functionMeta.setReturnType(false);
                                }

                                viewMeta.setView(true);
                            }

                            if(controllerType.equals("rest")) {

                                functionMeta.setReturnType(true);

                                viewMeta.setView(false);

                            }
                            iegalUrlFilter.extendFilterUrl(requestMapping.value()[0]);

//                            baseControllerHandle.addUrlResource(requestMapping.value()[0] + "@" + requestMapping.method()[0], urlMetatData);
                            baseControllerHandle.addUrlResource(requestMapping.value()[0], urlMetatData);
                        }

                    }

                }
            }
        }
    }


}
