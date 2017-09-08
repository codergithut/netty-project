package tianjian.http.filter.impl;

import tianjian.http.filter.HttpRequestFilter;
import tianjian.http.util.FileUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/2
 * @description
 */
public class ResourceFilter extends HttpRequestFilter {

    private String resourcePath;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param url 需要过滤的url路径
     * @return 是否是静态资源
     * @description 判断该url是否是静态资源请求
     */
    public boolean filterHttpRequest(String url){
        for(String s : urlFilters) {
            if(url.contains(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param url 过滤资源url路径
     * @return 静态资源文件
     * @description 过滤静态资源文件，如果是静态文件返回该文件无需交给执行器BaseController处理
     */
    public File HandleHttpRequest(String url) throws IOException {
        File file = null;
        String detailResourcePath = resourcePath;
        if(filterHttpRequest(url)){
            detailResourcePath = resourcePath + url.replace("static", "");
            file = new File(detailResourcePath);
            if(file != null && file.isFile()) {
                return file;
            }
        }

        file = new File("error");
        FileUtil.saveStringToFile("Url is error", file);
        return file;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }
}
