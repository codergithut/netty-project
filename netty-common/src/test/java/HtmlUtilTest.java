import netty.tianjian.common.util.fork.ForkJoinCalculator;
import netty.tianjian.common.util.http.HtmlContent;
import netty.tianjian.common.util.fork.ThreadManagerSchedule;
import org.junit.Test;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.IOException;
import java.util.*;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/7
 * @description
 */
public class HtmlUtilTest {
    @Test
    public void htmlUtilTest() throws IOException {

        List<HtmlContent> htmls = new ArrayList<HtmlContent>();

        Set<String> visted = new HashSet<String>();

        Set<String> urls = new HashSet<String>();

        Set<String> waitUrls = new HashSet<String>();

        int depth = 0;

        ThreadPoolTaskExecutor executor = ThreadManagerSchedule.getThreadPoolTaskExecutor();


        //http://geek.csdn.net/news/detail/235638 正常 http://www.jb51.net/article/99336.htm网站乱码
        urls.add("https://www.zhihu.com/question/20103385");

        while(depth < 6) {
            waitUrls.clear();
            waitUrls = ForkJoinCalculator.forkJoinSum(urls);
//            for(String url : urls) {
//                HtmlContent contents = new HtmlContent(url);
//                visted.add(url);
//                Set<String> linkeds = contents.getLinked();
//                for(String linked : linkeds ) {
//                    if(!visted.contains(linked)){
//                        waitUrls.add(linked);
//                    }
//                }
//            }
            System.out.println("sdfasdfasdfasdfasdfasdf");
            urls.clear();
            for(String s : waitUrls) {
                urls.add(s);
            }
            depth ++;
        }

        System.out.println("this is test ok");

    }
}
