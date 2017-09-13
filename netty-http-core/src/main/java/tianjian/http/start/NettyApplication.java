package tianjian.http.start;

import tianjian.http.metadata.annotation.NettySpringBoot;
import tianjian.http.spring.ScanSpringContext;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/13
 * @description
 */
public class NettyApplication {
    public static void applicationStart(Class t, String[] args) {
        NettySpringBoot nettySpringBoot = (NettySpringBoot)t.getAnnotation(NettySpringBoot.class);
        if(nettySpringBoot != null) {
        }

        ScanSpringContext scanSpringContext = new ScanSpringContext();

        scanSpringContext.addScanPath(t.getPackage().getName());

        scanSpringContext.run(args);
    }
}
