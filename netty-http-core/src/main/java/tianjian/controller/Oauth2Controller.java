package tianjian.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tianjian.http.metadata.RequestMethod;
import tianjian.http.metadata.annotation.RequestMapping;
import tianjian.http.metadata.annotation.ResponseBody;
import tianjian.http.model.Model;
import tianjian.http.model.ModelAndViewer;
import tianjian.http.model.ResourceGuard;
import tianjian.http.model.User;
import tianjian.http.util.UUIDTool;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/1
 * @description controller测试类
 */
@Controller
public class Oauth2Controller {

    @Autowired
    ResourceGuard resourceGuard;

    //注册添加用户信息
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object registUser(Model model, String haha) {

        Map<String, Object> data = new HashMap<String, Object>();

        System.out.println(haha);

        ModelAndViewer modelAndViewer = new ModelAndViewer("index.ftl", data);

        return modelAndViewer;
    }


    //授权方法
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Object loginUser(Model model) {

        //todo 验证用户信息

        return "redirect:http://localhost:8080/getResourceToken";
    }

    //注册添加用户信息
    @RequestMapping(value = "/getResource", method = RequestMethod.GET)
    @ResponseBody
    public Object getResource(Model model) {


        if(resourceGuard.getUserinfo().size() == 0) {

            return "redirect:http://localhost:8080/getResourceID";

        } else {
            User user = new User();

            user.setPassword("hahh");

            user.setName("tianjian");

            return user;
        }

    }

    //注册添加用户信息
    @RequestMapping(value = "/getResourceID", method = RequestMethod.GET)
    @ResponseBody
    public Object getResourceID(Model model) {

        ModelAndViewer modelAndViewer = new ModelAndViewer("index.ftl", null);

        return "redirect:http://localhost:8080/login";
    }

    //注册添加用户信息
    @RequestMapping(value = "/getResourceToken", method = RequestMethod.GET)
    @ResponseBody
    public Object getResourceToken(Model model) {

        String token = UUIDTool.getUUID();

        User user = new User();

        user.setName("tianjian");

        user.setPassword("hahah");

        resourceGuard.addTokenUser(token, user);

        ModelAndViewer modelAndViewer = new ModelAndViewer("index.ftl", null);

        return "redirect:http://localhost:8080/getResource";
    }

}
