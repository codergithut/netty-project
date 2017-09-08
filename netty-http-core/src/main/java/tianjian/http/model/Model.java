package tianjian.http.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/2
 * @description 系统自定义数据封装，可以带入视图层
 */
public class Model {
    Map<String, Object> viewData = new HashMap<String, Object>();

    public Map<String, Object> getViewData() {
        return viewData;
    }

    public void setViewData(Map<String, Object> viewData) {
        this.viewData = viewData;
    }

    public void addKeyAndValue(String key, Object value) {

        viewData.put(key, value);

    }
}
