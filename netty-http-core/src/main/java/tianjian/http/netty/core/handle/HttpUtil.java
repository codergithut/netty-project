package tianjian.http.netty.core.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import tianjian.http.model.UrlInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
 * @version 1.0, 2017/9/5
 * @description Http服务常用的消息封装
 */
public class HttpUtil {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param ctx 容器对象
     * @return void
     * @description 发送继续消息
     */
    public static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param ctx 容器对象
     * @param url 重新定向的url
     * @return void
     * @description 发送重新定向消息
     */
    public static void send301Continue(ChannelHandlerContext ctx, String url) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.MOVED_PERMANENTLY);
        response.headers().add("Location", url);
        ctx.writeAndFlush(response);
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param ctx 容器对象
     * @return void
     * @description 发送无内容消息
     */
    public static void send204Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NO_CONTENT);
        ctx.writeAndFlush(response);
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param url 需要提取消息的url
     * @return UrlInfo url信息封装
     * @description 提取url的信息并封装
     */
    public static UrlInfo getUrlInfoByString(String url) {
        UrlInfo urlInfo = new UrlInfo();
        Map<String,String> params = new HashMap<String,String>();
        if(url.contains("?")) {
            String[] listParams = new String[1];

            Object o = url.split("\\?");
            urlInfo.setRequest(url.split("\\?")[0]);
            String paramsString = url.split("\\?")[1];
            if(paramsString.contains("&")) {
                listParams = paramsString.split("&");
            } else {
                listParams[0] = paramsString;
            }

            for(String param : listParams) {
                String[] details = param.split("=");
                if(details.length == 2) {
                    params.put(details[0], details[1]);
                }
            }

        }else {
            urlInfo.setRequest(url);
        }
        urlInfo.setParams(params);
        return  urlInfo;
    }

}
