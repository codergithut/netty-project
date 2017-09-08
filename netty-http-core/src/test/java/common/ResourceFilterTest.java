package common;

import org.junit.Test;
import tianjian.http.filter.impl.ResourceFilter;

import java.io.IOException;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/2
 * @description
 */
public class ResourceFilterTest {
    @Test
    public void resourceFiletTest() throws IOException {
        ResourceFilter resourceFilter = new ResourceFilter();
        resourceFilter.extendFilterUrl("static").extendFilterUrl("resource");
        resourceFilter.HandleHttpRequest("static");
        resourceFilter.HandleHttpRequest("haha");

    }
}
