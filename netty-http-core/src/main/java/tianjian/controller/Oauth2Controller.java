package tianjian.controller;

import org.apache.commons.lang.StringUtils;
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
    @RequestMapping(value = "/common", method = RequestMethod.POST)
    @ResponseBody
    public Object common(Model model, String username, String token) {
        if (StringUtils.isBlank(username) && StringUtils.isBlank(token)) {
            //进行资源请求
            return "redirect:http://localhost:8080/getResource?redirect=http://localhost:8080/common";
        } else if(!StringUtils.isBlank(token)) {
            return "redirect:http://localhost:8080/getResource?token=" + token + "&redirect=http://localhost:8080/common";
        } else {
            return username;
        }
    }


    //注册添加用户信息
    @RequestMapping(value = "/userinfo", method = RequestMethod.POST)
    @ResponseBody
    public String UserInfo(Model model, String redirect) {

        User user = new User();

        user.setPassword("tianjian");

        user.setName("tianjian");

        return "redirect:" + redirect + "?username=" + user.getName();
    }


    //授权方法 携带验证方式 如果手机扫码type 传入phone 如果是用户登录就 pc
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Object loginUser(String guard, String id, String password) {

        if("tianjian".equals(id) && "tianjian".equals(password)) {
            return "redirect:http://localhost:8080/getResourceToken?guard=" + guard;
        }
        return null;

    }

    //注册添加用户信息
    @RequestMapping(value = "/getResource", method = RequestMethod.GET)
    @ResponseBody
    public Object getResource(Model model, String redirect, String token) {

        if(StringUtils.isBlank(token)) {
            //为资源生产token
            return "redirect:http://localhost:8080/getResourceID?redirect=" + redirect;
        } else {

            //根据token返回资源
            String resource = Oauth2ResourceGuard.getGrard(token).getResources().get(0);

            return "redirect:http://localhost:8080/" + resource + "?redirect=" + redirect;
        }

    }

    //注册添加用户信息
    @RequestMapping(value = "/getResourceID", method = RequestMethod.GET)
    @ResponseBody
    public Object getResourceID(Model model, String redirect) {

        String guard = UUIDTool.getUUID();

        Map<String,Object> map = new HashMap<String,Object>();

        map.put("ResourceID", guard);

        map.put("redirect", redirect);

        System.out.println(guard);

        return new ModelAndViewer("pc.ftl", map);
    }

    //注册添加用户信息
    @RequestMapping(value = "/getResourceToken", method = RequestMethod.GET)
    @ResponseBody
    public void getResourceToken(String guard) {

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

        System.out.println(token + "=======================================");
    }

    //注册添加用户信息
    @RequestMapping(value = "/getToken", method = RequestMethod.GET)
    @ResponseBody
    public Object getToken(String guard) {

        Map<String, Oauth2ResourceGuard> all = Oauth2ResourceGuard.getResourceGuardHash();

        Map<String,String> data = new HashMap<String,String>();

        for(Map.Entry<String,Oauth2ResourceGuard> entry : all.entrySet()) {
            if(entry.getValue().getGuard().equals(guard)) {
                data.put("token", entry.getKey().toLowerCase());
                return data;
            }
        }
        return null;

    }

}
