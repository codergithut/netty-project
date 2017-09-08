package tianjian.http.view;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;
import tianjian.http.util.MyBeanUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/2
 * @description 资源模板
 */
@Service
public abstract class TemplateEngine {

    protected String templatePath;

    protected String suffix;

    static Map<String, Template> templateMap = new HashMap<String, Template>();

    Template template = null;

    static ViewEngineManage viewEngineManage = MyBeanUtils.getBean("viewEngineManage");

    public abstract File changeTemplateByData(String templatePath, Object o) throws IOException, TemplateException;

    public abstract boolean templateIsExist(String templatePath);

    public abstract void InitTemplate();

    public TemplateEngine(String suffix, String templatePath) {
        this.suffix = suffix;
        this.templatePath = templatePath;
        InitTemplate();
    }


    public void addTemplate(String key, TemplateEngine templateEngine) {
        viewEngineManage.getTemplateRoute().put(key, templateEngine);
    }

}
