package tianjian.http.netty.core.handle.common;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:tianajian@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/4
 * @description  HttpHandle 容器管理类
 */

@Service
public class HttpHandleContains implements HttpHandlerListener{
    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 现在程序中注册的HttpHandle容器
     */
    List<HttpHandlerListener> httpHandleInstances = new ArrayList<HttpHandlerListener>();

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param simpleHandler 需要注册的处理对象
     * @return void
     * @description 注册
     */
    public void registSimpleHandler(HttpHandlerListener simpleHandler) {
        httpHandleInstances.add(simpleHandler);
        //handleWake();
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param simpleHandler 需要移除的处理对象
     * @return void
     * @description 将处理对象移除容器
     */
    public void removeSimpleHandler(HttpHandlerListener simpleHandler) {
        httpHandleInstances.remove(simpleHandler);
    }


    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param
     * @return void
     * @description 接口方法用户暂时代码执行
     */
    @Override
    public void handleWaite(){
        for(HttpHandlerListener listener : httpHandleInstances) {
            listener.handleWaite();
        }

    }


    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param
     * @return void
     * @description 唤醒代码方法
     */
    @Override
    public void handleWake() {
        int i = 0;

        while(i < 10) {
            try {
                System.out.println(i + "sssssssssssssssssssss");
                Thread.sleep(1000);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for(HttpHandlerListener listener : httpHandleInstances) {
            listener.handleWake();
            removeSimpleHandler(listener);
        }

    }
}
