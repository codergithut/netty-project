package tianjian.http.controller.handle.model;

import com.alibaba.fastjson.JSON;
import freemarker.template.TemplateException;
import tianjian.http.model.Model;
import tianjian.http.model.ModelAndViewer;
import tianjian.http.util.FileUtil;
import tianjian.http.view.ViewEngineManage;

import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/5
 * @description
 */
public class ViewMeta {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 是否是视图显示
     */
    private boolean isView;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 系统自定义model对象，存放信息，可以存放视图信息
     */
    private Model model = null;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 系统定义的视图消息对象，对象包含视图路径和视图参数
     */
    private ModelAndViewer modelAndViewer = null;

    public boolean isView() {
        return isView;
    }

    public void setView(boolean view) {
        isView = view;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public ModelAndViewer getModelAndViewer() {
        return modelAndViewer;
    }

    public void setModelAndViewer(ModelAndViewer modelAndViewer) {
        this.modelAndViewer = modelAndViewer;
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param result 实际返回的对象
     * @param viewEngineManage 视图分发器管理类
     * @return 经过视图封装过的文件（也可能没有封装过哦笑）
     * @description 更具结果对视图进行封装
     */
    public File viewHandle(Object result, ViewEngineManage viewEngineManage) throws IOException, TemplateException {
        String templatePath;

        File returnFile = null;

        String returnFileString = null;

        if(result instanceof ModelAndViewer) {
            setModelAndViewer((ModelAndViewer) result);
        }

        if(result instanceof String) {
            templatePath = (String) result;

            returnFile = viewEngineManage.getTemplateByData(templatePath, getModel());
            if(returnFile == null) {
                returnFileString = templatePath;
            }
        }

        if(getModelAndViewer() != null) {
            templatePath = getModelAndViewer().getTemplatePath();
            returnFile = viewEngineManage.getTemplateByData(templatePath, getModelAndViewer().getTemplateData());
            if(returnFile == null) {
                returnFileString = "We can't find your template please check";
            }
        } else if(getModelAndViewer() == null && !(result instanceof String)) {
            returnFileString = JSON.toJSONString(result);
        }

        if(returnFile == null) {
            returnFile = new File("returnContent");
            FileUtil.saveStringToFile(returnFileString, returnFile);
        }

        return returnFile;
    }
}
