package mvc;

import tianjian.http.metadata.annotation.NettySpringBoot;
import tianjian.http.start.NettyApplication;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/13
 * @description
 */
@NettySpringBoot
public class NettyApplicationStart {

    public static void main(String[] args) {

        NettyApplication.applicationStart(NettyApplicationStart.class, args);

    }

}
