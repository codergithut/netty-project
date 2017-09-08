package tianjian.http.util;

import org.apache.commons.collections.map.LinkedMap;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/1
 * @description 反射工具
 */
public class ReflectUtil {
    public static Map<String, Class> getParamsInfo(Method method) {
        Map<String, Class> datas = new LinkedMap();
        Class[] types = method.getParameterTypes();
        LocalVariableTableParameterNameDiscoverer u =
                new LocalVariableTableParameterNameDiscoverer();
        String[] params = u.getParameterNames(method);
        for (int i = 0; i < params.length; i++) {
            datas.put(params[i], types[i]);
        }
        return datas;
    }
}
