package tianjian.http.netty.core.handle.common;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/4
 * @description 容器监听对象
 */
public interface HttpHandlerListener {
    void handleWaite();
    void handleWake();
}
