package netty.tianjian.common.util.proxy;

import netty.tianjian.common.util.proxy.model.IDBQuery;
import org.junit.Test;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/6
 * @description
 */
public class ProxyTest {
    @Test
    public void testProxy() {

        IDBQuery query = JdkDbQueryHandler.createJdkProxy();

        System.out.println(query.request());

    }
}
