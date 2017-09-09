package tianjian.http.controller.handle;

import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tianjian.http.controller.handle.model.FunctionMeta;
import tianjian.http.controller.handle.model.ViewMeta;
import tianjian.http.util.FileUtil;
import tianjian.http.view.ViewEngineManage;

import java.io.File;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/1
 * @description
 */
@Service
@Scope("prototype")
public class UrlHandleDetail {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 视图分发器管理类
     */
    ViewEngineManage viewEngineManage;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 方法封装对象类
     */
    private FunctionMeta functionMeta;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 视图消息封装对象
     */
    private ViewMeta viewMeta;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 最终返回的字符串
     */
    private String returnFileString = null;

    public void setViewEngineManage(ViewEngineManage viewEngineManage) {
        this.viewEngineManage = viewEngineManage;
    }

    public FunctionMeta getFunctionMeta() {
        return functionMeta;
    }

    public void setFunctionMeta(FunctionMeta functionMeta) {
        this.functionMeta = functionMeta;
    }

    public ViewMeta getViewMeta() {
        return viewMeta;
    }

    public void setViewMeta(ViewMeta viewMeta) {
        this.viewMeta = viewMeta;
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param datas 表单数据，系统数据
     * @return 经过处理的文件信息
     * @description 文件路由到这边实际处理
     */
    public File handleRequest(Map<String, String> datas) throws Exception {

        Object[] args = functionMeta.initParamByData(datas, viewMeta);

        /**
         * 通过反射调用controller注解类的实际方法
         */
        Object returnObject = functionMeta.getMethod().invoke(functionMeta.getController(),args);

        if(functionMeta.isReturnType()) {

        }

        File returnFile = null;

        String url = null;
        if(returnObject instanceof String) {
            url = (String) returnObject;
        }

        if(url != null && url.startsWith("redirect:")) {

            returnFile = new File("returnContent");
            FileUtil.saveStringToFile(url, returnFile);

        } else {

            if(viewMeta.isView()) {
                returnFile = viewMeta.viewHandle(returnObject, viewEngineManage);
            } else {
                returnFileString = JSON.toJSONString(returnObject);
            }

            if(returnFileString == null && returnObject instanceof String) {
                returnFileString = (String) returnObject;
            }

            if(returnFile == null && returnFileString != null) {
                returnFile = new File("returnContent");
                FileUtil.saveStringToFile(returnFileString, returnFile);
            }
        }
        return returnFile;
    }

}
