package spring.http;

import spring.base.BaseJunit4Test;
import spring.domain.controller.ControllerDemoTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tianjian.http.controller.BaseControllerHandle;
import tianjian.http.controller.handle.UrlHandleDetail;
import tianjian.http.controller.handle.model.FunctionMeta;
import tianjian.http.metadata.annotation.RequestMapping;
import tianjian.http.metadata.annotation.ResponseBody;
import tianjian.http.util.MyBeanUtils;
import tianjian.http.util.ReflectUtil;
import tianjian.http.view.ViewEngineManage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/2
 * @description
 */
public class ControllerTest extends BaseJunit4Test {

    @Autowired
    BaseControllerHandle baseControllerHandle;

    @Autowired
    ViewEngineManage viewEngineManage;


    @Test
    public void ControllerTest() {

        ControllerDemoTest controllerDemoTest = new ControllerDemoTest();

        Map<String,String> paramsData = new HashMap<String,String>();

        paramsData.put("username", "tianjian");

        paramsData.put("password", "tianjian");

        paramsData.put("email", "17668688789@qq.com");

        paramsData.put("haha", "test");


        Method[] methods = controllerDemoTest.getClass().getMethods();

        for(Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            if(annotations != null) {

                for(Annotation annotation : annotations) {

                    if(annotation instanceof RequestMapping) {
                        UrlHandleDetail urlMetatData = MyBeanUtils.getBean("urlHandleDetail");
                        RequestMapping requestMapping = (RequestMapping) annotation;
                        FunctionMeta functionMeta = new FunctionMeta();
                        urlMetatData.setViewEngineManage(viewEngineManage);

                        Map<String, Class> data = ReflectUtil.getParamsInfo(method);
                        functionMeta.setParams(data);
                        functionMeta.setMethod(method);

                        Annotation response = method.getAnnotation(ResponseBody.class);

                        if(response != null) {
                            functionMeta.setReturnType(true);
                        } else {
                            functionMeta.setReturnType(false);
                        }

                        baseControllerHandle.addUrlResource(requestMapping.value()[0] + "@" + requestMapping.method()[0], urlMetatData);
                    }

                }

            }
        }

        try {
            baseControllerHandle.handlRequest("/register@POST", paramsData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
