package tianjian.http.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/2
 * @description http url 过滤规则，现在实现了静态资源过滤和页面请求过滤，后期将加入单点登录过滤。
 */
public abstract class HttpRequestFilter {
    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 过滤规则
     */
    protected List<String> urlFilters = new ArrayList<String>();

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 过滤方式
     */
    public abstract boolean filterHttpRequest(String url);

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 通过过滤处理方式
     */
    public abstract Object HandleHttpRequest(String url) throws IOException;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param regUrl 过滤规则
     * @return HttpRequestFilter
     * @description 流式创建对象
     */
    public HttpRequestFilter extendFilterUrl(String regUrl) {
        urlFilters.add(regUrl);
        return this;
    }


}
