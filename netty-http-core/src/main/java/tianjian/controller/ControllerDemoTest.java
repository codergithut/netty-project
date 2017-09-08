package tianjian.controller;

import org.springframework.stereotype.Controller;
import tianjian.http.metadata.RequestMethod;
import tianjian.http.metadata.annotation.RequestMapping;
import tianjian.http.metadata.annotation.ResponseBody;
import tianjian.http.model.Model;
import tianjian.http.model.ModelAndViewer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/1
 * @description controller测试类
 */
@Controller
public class ControllerDemoTest {

    //注册添加用户信息
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Object registUser(Model model, String haha) {

        Map<String, Object> data = new HashMap<String, Object>();

        System.out.println(haha);

        ModelAndViewer modelAndViewer = new ModelAndViewer("index.ftl", data);

        return modelAndViewer;
    }

}
