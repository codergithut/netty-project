package tianjian.http.netty.core.handle.oauht2;

import org.springframework.stereotype.Service;
import tianjian.http.model.User;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/5
 * @description 资源保护信息
 */
@Service
public class ResourceGuard {
    private Map<String,Map<String,String>> resources = new HashMap<String,Map<String,String>>();

    private Map<String,User> userinfo = new HashMap<String,User>();

    public Map<String, Map<String,String>> getResources() {
        return resources;
    }

    public void setResources(Map<String, Map<String,String>> resources) {
        this.resources = resources;
    }

    public void addResource(String url, String oauth) {
        Map<String, String> resource = new HashMap<String,String>();
        resource.put(url, "");
        resources.put(oauth, resource);
    }

    public void addTokenToResource(String url, String oauth, String token) {
        resources.get(oauth).put(url, token);
    }

    public Map<String, User> getUserinfo() {
        return userinfo;
    }

    public void addTokenUser(String token, User user) {
        userinfo.put(token, user);
    }

    public void setUserinfo(Map<String, User> userinfo) {
        this.userinfo = userinfo;
    }


}
