package netty.tianjian.common.util.http;

import netty.tianjian.common.util.fork.ForkJoinCalculator;

import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/11
 * @description
 */
public class HtmlSearch {
    public static void getHtmlSerarchByUrl(String url, int depth) {

        Set<String> urls = new HashSet<String>();

        Set<String> waitUrls = new HashSet<String>();

        int realDepth = 0;

        //http://geek.csdn.net/news/detail/235638 正常 http://www.jb51.net/article/99336.htm网站乱码
        urls.add(url);

        while(realDepth < depth) {
            waitUrls.clear();
            waitUrls = ForkJoinCalculator.forkJoinSum(urls);
            urls.clear();
            for(String s : waitUrls) {
                urls.add(s);
            }
            realDepth ++;
        }
    }
}
