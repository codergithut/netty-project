package tianjian.http.controller.handle.conversion;

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/5
 * @description 简单对象转换
 */
public interface ConversionBean {
    default Object baseTypeConversion(Object param, String value) {
        if(param instanceof String) {
            return value;
        }

        if(param instanceof Integer) {
            return Integer.valueOf(value);
        }

        if(param instanceof Long) {
            return Long.valueOf(value);
        }

        if(param instanceof Boolean) {
            if("true".equals(value)) {
                return true;
            } else if("false".equals(value)){
                return false;
            } else {
                try {
                    throw new Exception("Change Param is Error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if(param instanceof Double) {
            return Double.valueOf(value);
        }

        if(param instanceof Short) {
            return Short.valueOf(value);
        }

        if(param instanceof Byte) {
            return Byte.valueOf(value);
        }

        return null;
    }

    default Object refBeanConversion(Object o, Map<String,String> datas) {
        for(Map.Entry<String,String> value : datas.entrySet()) {
            try {
                BeanUtils.setProperty(o, value.getKey(), value.getValue());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return o;
    }
}
