package tianjian.http.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/1
 * @description spring 扫描器
 */
public class ScanSpringContext {
    public void run(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(NettyServerConfig.class);
        ctx.refresh();
    }
}
