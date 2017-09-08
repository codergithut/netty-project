package tianjian.http.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/1
 * @description 视图封装对象，包含视图路径视图参数
 */
public class ModelAndViewer {
    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 视图路径
     */
    private String templatePath;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 视图参数
     */
    private Map<String,Object> templateData = new HashMap<String,Object>();


    public ModelAndViewer(String templatePath, Map<String, Object> templateData) {
        this.templatePath = templatePath;
        this.templateData = templateData;
    }

    public ModelAndViewer(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public Map<String, Object> getTemplateData() {
        return templateData;
    }

    public void setTemplateData(Map<String, Object> templateData) {
        this.templateData = templateData;
    }
}
