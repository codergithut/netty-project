package tianjian.http.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/9
 * @description
 */
public class Oauth2ResourceGuard {

    static Map<String,Oauth2ResourceGuard> resourceGuardHash = new HashMap<String,Oauth2ResourceGuard>();

    private String token;

    private User user;

    private List<String> resources;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<String> getResources() {
        return resources;
    }

    public void setResources(List<String> resources) {
        this.resources = resources;
    }

    public static void addGuard(String token, Oauth2ResourceGuard oauth2ResourceGuard) {
        resourceGuardHash.put(token, oauth2ResourceGuard);
    }
}
