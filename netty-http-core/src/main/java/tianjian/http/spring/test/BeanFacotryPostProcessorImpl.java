package tianjian.http.spring.test;

import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

import java.beans.PropertyDescriptor;


/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/13
 * @description
 */
public class BeanFacotryPostProcessorImpl implements InstantiationAwareBeanPostProcessor {


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println("postProcessBeforeInitialization" + bean
                + " beanName:" + beanName);

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        // TODO Auto-generated method stub
        return bean;
    }

    /**
     *
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass,
                                                 String beanName) throws BeansException {

        System.out.println("postProcessBeforeInstantiation(class)" + beanClass
                + " beanName:" + beanName);

        //此处修改了Bean本身所需要的实例对象,阻断了Spring的创建Bean执行流                              程

        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName)
            throws BeansException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs,
                                                    PropertyDescriptor[] pds, Object bean, String beanName)
            throws BeansException {
        System.out.println("postProcessPropertyValues");
        return null;
    }
}
