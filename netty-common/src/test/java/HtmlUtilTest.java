import netty.tianjian.common.util.http.HtmlSearch;
import org.junit.Test;

import java.io.IOException;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/7
 * @description
 */
public class HtmlUtilTest {
    @Test
    public void htmlUtilTest() throws IOException {
        HtmlSearch.getHtmlSerarchByUrl("https://www.zhihu.com/question/20103385", 5);
    }
}
