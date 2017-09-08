package tianjian.http.aop.service;

import org.springframework.stereotype.Component;
import tianjian.http.aop.dao.UserDAO;
import tianjian.http.aop.model.User;

import javax.annotation.Resource;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/6
 * @description
 */
@Component("userService")
public class UserService {

    private UserDAO userDAO;

    public void init() {
        System.out.println("init");
    }

    public void add(User user) {
        userDAO.save(user);
    }
    public UserDAO getUserDAO() {
        return userDAO;
    }

    @Resource(name="u")
    public void setUserDAO( UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void destroy() {
        System.out.println("destroy");
    }
}
