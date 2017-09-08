package tianjian.http.filter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tianjian.http.controller.BaseControllerHandle;
import tianjian.http.filter.HttpRequestFilter;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/4
 * @description 检验通过的url
 */
@Service
public class IegalUrlFilter extends HttpRequestFilter {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 处理通过检查的url管理中心
     */
    @Autowired
    BaseControllerHandle baseControllerHandle;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param url 需要过滤的url
     * @return 是否过滤成功
     * @description 简单查看是否包含敏感字来处理，后面可以换成正则式处理
     */
    @Override
    public boolean filterHttpRequest(String url) {
        for(String s : urlFilters) {
            if(url.contains(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param
     * @return
     * @description 未进行进一步处理
     */
    @Override
    public Object HandleHttpRequest(String url) {
        return null;
    }
}
