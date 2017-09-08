package tianjian.http.model;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/6
 * @description 用户信息，单点登录等着实现呢
 */
public class User {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 用户名称
     */
    private String name;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 用户密码
     */
    private String password;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 用户权限
     */
    private String root;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
