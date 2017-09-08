package spring.http;

import spring.base.BaseJunit4Test;
import spring.domain.model.ValueObject;
import freemarker.template.TemplateException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tianjian.http.view.ViewEngineManage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/2
 * @description
 */
public class FreeMarkerTest extends BaseJunit4Test {
    @Autowired
    ViewEngineManage viewEngineManage;

    @Test
    public void freeMarkerTest() throws IOException, TemplateException {
        ValueObject val = new ValueObject();
        val.setFname("Yao");
        val.setGname("Yorker");
        val.setEmail("test@mail.com");
        val.setManager("manager");
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("p", val);
        viewEngineManage.getTemplateByData("test.ftl", parameters);
    }


}
