package tianjian.controller;

import netty.tianjian.common.util.qrcode.Qrest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import tianjian.http.metadata.RequestMethod;
import tianjian.http.metadata.annotation.RequestMapping;
import tianjian.http.metadata.annotation.ResponseBody;
import tianjian.http.model.*;
import tianjian.http.util.UUIDTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/1
 * @description controller测试类
 */
@Controller
public class Oauth2Controller {

    @Autowired
    Oauth2ResourceGuard oauth2ResourceGuard;

    private String token = null;

    //注册添加用户信息
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object registUser(Model model, String haha) {

        Map<String, Object> data = new HashMap<String, Object>();

        System.out.println(haha);

        ModelAndViewer modelAndViewer = new ModelAndViewer("login.ftl", data);

        return modelAndViewer;
    }

    //注册添加用户信息
    @RequestMapping(value = "/userinfo", method = RequestMethod.POST)
    @ResponseBody
    public Object UserInfo(Model model, String haha) {

        User user = new User();

        user.setPassword("tianjian");

        user.setName("tianjian");

        return user;
    }


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Object loginUser(String guard) {

        Map<String,Object> model = new HashMap<String, Object>();
        model.put("gurad", guard);
        return new ModelAndViewer("pc.ftl", model);
    }



    //login 验证 可以通过手机验证也可以通过pc页面验证
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public Object checkUser(String guard, String id, String password) {

        System.out.println("test");

        System.out.println("tsestttt");

        if("tianjian".equals(password) && "id".equals(id)) {
            return "redirect:http://localhost:8080/getResourceToken?guard=" + guard + "&type=ajax";
        }

        return "redirect:http://localhost:8080/getResourceToken?guard=" + guard + "&type=ajax";

    }

    //注册添加用户信息
    @RequestMapping(value = "/getResource", method = RequestMethod.GET)
    @ResponseBody
    public Object getResource(Model model) {

        if(token == null) {
            return "redirect:http://localhost:8080/getResourceID";
        } else {

            String resource = Oauth2ResourceGuard.getGrard(token).getResources().get(0);

            return "redirect:http://localhost:8080/" + resource;
        }

    }

    //注册添加用户信息
    @RequestMapping(value = "/getResourceID", method = RequestMethod.GET)
    @ResponseBody
    public Object getResourceID() {

        Map<String,Object> model = new HashMap<String,Object>();
        String guard = UUIDTool.getUUID();

        model.put("img", guard);

//        Qrest.qrest(guard, "http://www.baidu.com?gurad=" + guard);
//
//        return new ModelAndViewer("mobile.ftl", model);
        return "redirect:http://localhost:8080/login?guard=" + guard;
    }

    //授权方法 携带验证方式 如果手机扫码type 传入phone 如果是用户登录就 pc
    @RequestMapping(value = "/mobile", method = RequestMethod.GET)
    @ResponseBody
    public Object loginByMobile(String guard) {
        return new ModelAndViewer("mobile.ftl", null);
    }

    //注册添加用户信息
    @RequestMapping(value = "/getResourceToken", method = RequestMethod.GET)
    @ResponseBody
    public Object getResourceToken(String guard, String type) {

        token = UUIDTool.getUUID();

        User user = new User();

        user.setName("tianjian");

        user.setPassword("hahah");

        List<String> resources = new ArrayList<String>();

        resources.add("userinfo");

        oauth2ResourceGuard.setToken(token);

        oauth2ResourceGuard.setResources(resources);

        oauth2ResourceGuard.setUser(user);

        oauth2ResourceGuard.setGuard(guard);

        Oauth2ResourceGuard.addGuard(token, oauth2ResourceGuard);

        if(type.equals("ajax")) {
            return "ajax";
        }

        return "redirect:http://localhost:8080/getResource";
    }

    //注册添加用户信息
    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    @ResponseBody
    public Object getToken(String guard) {

        Map<String, Oauth2ResourceGuard> all = Oauth2ResourceGuard.getResourceGuardHash();

        for(Map.Entry<String,Oauth2ResourceGuard> entry : all.entrySet()) {
            if(entry.getValue().getGuard().equals(guard)) {
                return entry.getKey();
            }
        }

        return "false";

    }

}
