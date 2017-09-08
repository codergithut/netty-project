package tianjian.http.netty.core.handle.oauht2;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/5
 * @description
 */

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import tianjian.http.model.UrlInfo;
import tianjian.http.netty.core.handle.HttpUtil;
import tianjian.http.netty.core.handle.common.CommonHandler;
import tianjian.http.util.UUIDTool;

import java.util.UUID;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
 * @version 1.0, 2017/8/17
 * @description 过滤url并根据url做出正确处理
 */
@ChannelHandler.Sharable
public class HttpOauth2ResourceHandler extends SimpleChannelInboundHandler<FullHttpRequest> implements CommonHandler {

    @Autowired
    ResourceGuard resourceGuard;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param ctx ChannelHandlerContext 上下文环境
     * @param request 请求
     * @return void
     * @description 模拟单点登录资源处理器
     */
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        String url = request.getUri();

        UrlInfo userInfo = HttpUtil.getUrlInfoByString(url);

        if(userInfo.getRequest().startsWith("/getResource") && (!userInfo.getParams().containsKey("token") && !userInfo.getParams().containsKey("validateid"))) {
            String oauth = UUIDTool.getUUID();
            resourceGuard.addResource("/getResource", oauth);
            HttpUtil.send301Continue(ctx, "http://localhost:9999/validate?validateid=" + oauth + "&redirect=" + userInfo.getParams().get("redirect"));

        } else if(userInfo.getRequest().startsWith("/getResource") && userInfo.getParams().containsKey("token")) {
            HttpUtil.send301Continue(ctx, userInfo.getParams().get("redirect") + "?user=tianjian");
        } else {
            ctx.fireChannelRead(request.retain());
        }

        request.retain();

    }



    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 异常处理
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
