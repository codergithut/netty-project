package spring.aop;

import netty.tianjian.log.annotation.AopLog;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import spring.base.BaseJunit4Test;
import tianjian.http.aop.model.User;
import tianjian.http.aop.service.UserService;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/6
 * @description
 */
public class AopTest extends BaseJunit4Test {

    @Autowired
    UserService userService;

    @Test
    public void testAdd() throws Exception {
        System.out.println(userService.getClass());
        userService.add(new User());
        System.out.println("###");

    }


}
