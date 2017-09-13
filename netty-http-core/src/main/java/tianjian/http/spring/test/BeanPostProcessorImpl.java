package tianjian.http.spring.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Service;
import tianjian.http.controller.oauth2.TokenGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/13
 * @description
 */
@Service
public class BeanPostProcessorImpl implements BeanPostProcessor {

    static List<Class> depends = new ArrayList<Class>();

    static {
        depends.add(TokenGenerator.class);
    }

    boolean insertBean = false;

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {

        if(s.contains("Token")) {
            System.out.println(o.getClass() + "--------------------------------------");
        }

        if(depends.contains(o.getClass())) {

            System.out.println(s + "ssssssssssssssssssssssssssssssssssssssssssssss");

            depends.remove(o.getClass());

        }


        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
