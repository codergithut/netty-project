package tianjian.http.view;

import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/2
 * @description 视图模型分发管理器
 */
@Service
public class ViewEngineManage {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 路径 模板处理器映射
     */
    private Map<String, TemplateEngine> templateRoute = new HashMap<String, TemplateEngine>();

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 模板实际处理
     */
    public File getTemplateByData(String templatePath, Object o) throws IOException, TemplateException {
        for(Map.Entry<String, TemplateEngine> val : templateRoute.entrySet()) {
            if(val.getValue().templateIsExist(templatePath)) {
                return val.getValue().changeTemplateByData(templatePath, o);
            }

        }

        return null;
    }

    public Map<String, TemplateEngine> getTemplateRoute() {
        return templateRoute;
    }

    public void setTemplateRoute(Map<String, TemplateEngine> templateRoute) {
        this.templateRoute = templateRoute;
    }
}
