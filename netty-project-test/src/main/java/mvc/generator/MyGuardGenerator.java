package mvc.generator;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tianjian.http.controller.oauth2.GuardGenerator;
import tianjian.http.util.UUIDTool;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/13
 * @description
 */
@Service
@Primary
public class MyGuardGenerator implements GuardGenerator {

    public String getGuardString() {
        System.out.println("client guard");
        return UUIDTool.getUUID();
    }
}
