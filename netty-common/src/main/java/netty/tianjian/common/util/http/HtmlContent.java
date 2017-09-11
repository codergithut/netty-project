package netty.tianjian.common.util.http;

import abstracts.AbstractEntrance;
import netty.tianjian.common.util.elastic.server.ElasticServer;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/7
 * @description
 */
public class HtmlContent {
    private String url;

    private String origincontent;

    private String content;

    private List<String> css;

    private List<String> js;

    private Set<String> linked;

    private String root;

    private String title;

    private String summary;



    public HtmlContent(String url) throws IOException {
        this.url = url;
        System.out.println(url);
        origincontent = HttpClientUtils.getConnect(url, null, null);
        if(origincontent != null) {
            content = HttpUtil.removeHtmlTag(origincontent).replaceAll("\n{2,}", "");
            css = HttpUtil.getCSS(origincontent);
            title = HttpUtil.getTitle(origincontent);
            root = HttpUtil.getTopDomain(url);
            linked = HttpUtil.getLink(origincontent, root);
            js = HttpUtil.getScript(origincontent);
            //AbstractEntrance abstractEntrance = new AbstractEntrance();
            //summary = abstractEntrance.getAbstractByContent(content, 100);
        }
    }

    public void saveElasticData() throws UnknownHostException {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("url", url);
        params.put("data", content);
        params.put("root", root);
        params.put("date", new Date().toString());
        params.put("title", title);
        ElasticServer.saveDataToEs(params, "search", "html");
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getJs() {
        return js;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public Set<String> getLinked() {
        return linked;
    }

    public void setLinked(Set<String> linked) {
        this.linked = linked;
    }

    public String getRoot() {
        return root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    public String getOrigincontent() {
        return origincontent;
    }

    public void setOrigincontent(String origincontent) {
        this.origincontent = origincontent;
    }

    public static void main(String[] args) {
        String url = "http://bbs.jointforce.com/forum.php?mod=viewthread&amp;tid=24240&amp;page=1&amp;extra#=pid181924";
        HttpUtil.isUrl(url);
        System.out.println("hahah");
    }


    private static class HttpUtil {
        private static Pattern pattern;

        // 定义正则表达式，域名的根需要自定义，这里不全
        private static final String RE_TOP = "[\\w-]+\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)\\b()*";


        private Set<String> getHrefLinked(String content) {
            Set<String> linkeds = new HashSet<String>();
            Pattern p = Pattern.compile("<a[^>]*>([^<]*)</a>");
            Matcher m = p.matcher(content);
            while(m.find()) {
                linkeds.add(m.group(1));
            }
            return linkeds;
        }

        /**
         *
         * @param s
         * @return 获得网页标题
         */
        private static String getTitle(final String s)
        {
            String regex;
            String title = "";
            final List<String> list = new ArrayList<String>();
            regex = "<title>.*?</title>";
            final Pattern pa = Pattern.compile(regex, Pattern.CANON_EQ);
            final Matcher ma = pa.matcher(s);
            while (ma.find())
            {
                list.add(ma.group());
            }
            for (int i = 0; i < list.size(); i++)
            {
                title = title + list.get(i);
            }
            return outTag(title);
        }

        /**
         *
         * @param s
         * @return 获得链接
         */
        private static Set<String> getLink(final String s, String root)
        {
            int number = 0;
            String regex;
            final Set<String> list = new HashSet<String>();
            regex = "<a[^>]*href=(\"([^\"]*)\"|\'([^\']*)\'|([^\\s>]*))[^>]*>(.*?)</a>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(s);
            System.out.println(root);
            while (ma.find())
            {
                String linked = ma.group();
                linked = getHrefContent(linked);
                if(linked != null) {
                    if(!linked.startsWith("http") && !linked.equals("#")) {
                        linked = "http://" + root + "/" + linked;
                    }

                    if(!linked.contains("=#") && isUrl(linked)) {
                        if(linked.contains(root)){
                            list.add(linked);
                        }
                    }
                }
            }


            return list;
        }

        //https://github.com/mike-zhang/mikeBlogEssays/blob/master/2017/20170907_python%E7%A8%8B%E5%BA%8F%E4%B9%8Bprofile%E5%88%86%E6%9E%90.rst#问题描述
        private static boolean isUrl(String url) {
            System.out.println(url);

            if(isContainChinese(url) || url == null) {
                return false;
            }

            Pattern pattern = Pattern
                    .compile("^([hH][tT]{2}[pP]://|[hH][tT]{2}[pP][sS]://)(([A-Za-z0-9-~]+).)+([A-Za-z0-9-~\\/])+$");
            return pattern.matcher(url).matches();
        }

        /**
         *
         * @param s
         * @return 获得脚本代码
         */
        private static List<String> getScript(final String s)
        {
            String regex;
            final List<String> list = new ArrayList<String>();
            regex = "<script.*?</script>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(s);
            while (ma.find())
            {
                list.add(ma.group());
            }
            return list;
        }

        /**
         *
         * @param s
         * @return 获得CSS
         */
        private static List<String> getCSS(final String s)
        {
            String regex;
            final List<String> list = new ArrayList<String>();
            regex = "<style.*?</style>";
            final Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
            final Matcher ma = pa.matcher(s);
            while (ma.find())
            {
                list.add(ma.group());
            }
            return list;
        }

        /**
         *
         * @param s
         * @return 去掉标记
         */
        private static String outTag(final String s)
        {
            return s.replaceAll("<.*?>", "");
        }


        /**
         * 删除Html标签
         * @param inputString
         * @return
         */
        private static String removeHtmlTag(String inputString) {
            if (inputString == null)
                return null;
            String htmlStr = inputString; // 含html标签的字符串
            String textStr = "";
            java.util.regex.Pattern p_script;
            java.util.regex.Matcher m_script;
            java.util.regex.Pattern p_style;
            java.util.regex.Matcher m_style;
            java.util.regex.Pattern p_html;
            java.util.regex.Matcher m_html;
            java.util.regex.Pattern p_special;
            java.util.regex.Matcher m_special;
            try {
                //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
                String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
                //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
                String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
                // 定义HTML标签的正则表达式
                String regEx_html = "<[^>]+>";
                // 定义一些特殊字符的正则表达式 如：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                String regEx_special = "\\&[a-zA-Z]{1,10};";

                p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
                m_script = p_script.matcher(htmlStr);
                htmlStr = m_script.replaceAll(""); // 过滤script标签
                p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
                m_style = p_style.matcher(htmlStr);
                htmlStr = m_style.replaceAll(""); // 过滤style标签
                p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
                m_html = p_html.matcher(htmlStr);
                htmlStr = m_html.replaceAll(""); // 过滤html标签
                p_special = Pattern.compile(regEx_special, Pattern.CASE_INSENSITIVE);
                m_special = p_special.matcher(htmlStr);
                htmlStr = m_special.replaceAll(""); // 过滤特殊标签
                textStr = htmlStr;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return textStr;// 返回文本字符串
        }

        public static String getTopDomain(String url) {
            String result = url;
            try {
                pattern = Pattern.compile(RE_TOP , Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(url);
                matcher.find();
                result = matcher.group();
            } catch (Exception e) {
                System.out.println("[getTopDomain ERROR]====>");
                e.printStackTrace();
            }
            return result;
        }

        public static String getHrefContent(String hrefContent) {
            Pattern pattern = Pattern.compile("href=['\"]([^'\"]*)['\"]");
            //System.out.println(pattern);

            String href = null;

            Matcher matcher = pattern.matcher(hrefContent);

            if (matcher.find()) {
                return href = matcher.group(1);
            }

            return null;
        }

        public static boolean isContainChinese(String str) {
            if(str == null) {
                return false;
            }
            Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
            Matcher m = p.matcher(str);
            if (m.find()) {
                return true;
            }
            return false;
        }



    }


}
