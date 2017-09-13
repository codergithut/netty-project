package tianjian.http.controller.oauth2.impl;

import org.springframework.stereotype.Service;
import tianjian.http.controller.oauth2.GuardGenerator;
import tianjian.http.util.UUIDTool;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/13
 * @description
 */
@Service
public class DefaultGuardGenerator implements GuardGenerator {
    @Override
    public String getGuardString() {
        return UUIDTool.getUUID();
    }
}
