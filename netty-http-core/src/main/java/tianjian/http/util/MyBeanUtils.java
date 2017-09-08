package tianjian.http.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/4/17
 * @description bean获取工具
 */
@Service
public class MyBeanUtils implements BeanFactoryAware {
    // Spring的bean工厂
    private static BeanFactory beanFactory;

    public void setBeanFactory(BeanFactory factory) throws BeansException {
        beanFactory = factory;
    }

    public static<T> T getBean(String name){
        return (T)beanFactory.getBean(name);
    }
}