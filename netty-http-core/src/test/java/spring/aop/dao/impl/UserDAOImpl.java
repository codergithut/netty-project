package spring.aop.dao.impl;

import org.springframework.stereotype.Component;
import spring.aop.dao.UserDAO;
import spring.aop.model.User;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/6
 * @description
 */
@Component("u")
public class UserDAOImpl implements UserDAO {

    public void save(User user) {

        System.out.println("user save11d!");
        /*throw new RuntimeException("exception");*/ //抛异常
    }

}
