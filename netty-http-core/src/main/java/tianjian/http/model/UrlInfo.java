package tianjian.http.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/6
 * @description url信息
 */
public class UrlInfo {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description url请求信息
     */
    private String request;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description  url携带的参数
     */
    private Map<String,String> params = new HashMap<String,String>();

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description url需要执行的方法GET，POST等等
     */
    private String method;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
