package spring.domain.controller;


import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Value;
import tianjian.http.metadata.RequestMethod;
import tianjian.http.metadata.annotation.RequestMapping;
import tianjian.http.metadata.annotation.RequestParam;
import tianjian.http.metadata.annotation.ResponseBody;
import tianjian.http.metadata.annotation.RestController;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/8/4
 * @description
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    //注册添加用户信息
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object registUser(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email) {

        return "sldfajsdlfasdlfasdj";
    }

    /**
     * 页面需要获取code，然后修改用户信息
     */
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    @ResponseBody
    public Object changeInfo() {

        return "changeworld";
    }

    //根据用户名进行用户信息验证
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public Object loignInfo(@RequestParam("account") String account, @RequestParam("password") String password) {
        return "object";
    }


    @Value("${spring.mail.username}")
    private String username;

    //根据用户信息发送消息到用户邮箱，用户提供邮箱信息
    @RequestMapping(value = "/sendMailInfo", method = RequestMethod.POST)
    @ResponseBody
    public String getMail(HttpResponse response) {

        return "hahahaha";
    }


    //用户发送邀请码
    @RequestMapping(value = "/sendInviteCode", method = RequestMethod.POST)
    @ResponseBody
    public String getInviteCode() {

        return "hahahaha";
    }

    @RequestMapping(value = "/checkToken", method = RequestMethod.POST)
    @ResponseBody
    public Object checkToken() {
        return true;
    }


}
