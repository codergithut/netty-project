package tianjian.http.controller.handle.model;

import tianjian.http.controller.handle.conversion.ConversionBean;
import tianjian.http.controller.handle.conversion.ConversionBeanImpl;
import tianjian.http.metadata.annotation.RequestMapping;
import tianjian.http.model.Model;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/5
 * @description 方法对象元数据抽取
 */
public class FunctionMeta {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description requestMapping 用于存放方法信息
     */
    private RequestMapping requestMapping;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 控制类
     */
    private Object controller;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 方法名称
     */
    private String functionName;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description  动态反射方法
     */
    private Method method;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 方法参数信息
     */
    private Map<String, Class> params;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 是否返回值
     */
    private boolean returnType;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 消息转换为java bean
     */
    private ConversionBean conversionBean = new ConversionBeanImpl();

    public RequestMapping getRequestMapping() {
        return requestMapping;
    }

    public void setRequestMapping(RequestMapping requestMapping) {
        this.requestMapping = requestMapping;
    }

    public Object getController() {
        return controller;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Map<String, Class> getParams() {
        return params;
    }

    public void setParams(Map<String, Class> params) {
        this.params = params;
    }

    public boolean isReturnType() {
        return returnType;
    }

    public void setReturnType(boolean returnType) {
        this.returnType = returnType;
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param datas 表单存放的数据
     * @param viewMeta 视图封装对象
     * @return 将表单数据转换为对象的集合
     * @description 更具参数信息和表单存放数据进行数据转换给后台使用
     */
    public Object[] initParamByData(Map<String, String> datas, ViewMeta viewMeta) throws IllegalAccessException, InstantiationException {

        Object[] args = new Object[getParams().size()];
        int i = 0;
        if(datas != null) {
            for(Map.Entry<String, Class> param : getParams().entrySet()) {

                String paramName = datas.get(param.getKey());

                if(param.getValue().newInstance() instanceof Model) {
                    viewMeta.setModel((Model)param.getValue().newInstance());
                    viewMeta.getModel().addKeyAndValue("this", "is test");
                    args[i] = viewMeta.getModel();
                    i++;
                }else if(paramName != null) {
                    Object o = param.getValue().newInstance();
                    args[i] = conversionBean.baseTypeConversion(o, datas.get(param.getKey()));
                    i++;
                }else if(paramName == null) {

                    Object o = getParams().get(param.getKey()).newInstance();

                    args[i] = conversionBean.refBeanConversion(o, datas);

                    i++;
                }
            }
        }

        return args;

    }
}
