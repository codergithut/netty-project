package tianjian.http.controller;

import org.springframework.stereotype.Service;
import tianjian.http.controller.handle.UrlHandleDetail;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/1
 * @description 基础的Controler控制，包含路由信息及路由处理方法，
 * 是包含@Controller帮忙填充数据的结果，再次BaseControllerHandle表示对含有@Controller衷心的感谢
 */
@Service
public class BaseControllerHandle {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 路径路由和实际处理方法
     */
    Map<String, UrlHandleDetail> routes = new HashMap<String, UrlHandleDetail>();

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param url 添加路由的路径
     * @param urlMetalData 该路由路径实际对应的方法
     * @return void
     * @description 添加路由路径和方法，初始化框架调用
     */
    public void addUrlResource(String url, UrlHandleDetail urlMetalData) {
        routes.put(url, urlMetalData);
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param url 需要路由的url
     * @param params 路由到该方法上携带的参数
     * @return 处理结果
     * @description 根据前端的url和参数返回对应的结果
     */
    public File handlRequest(String url, Map<String, String> params) throws Exception {
         return routes.get(url).handleRequest(params);
    }



}
