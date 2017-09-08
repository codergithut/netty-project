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
import tianjian.http.model.User;
import tianjian.http.netty.core.handle.HttpUtil;
import tianjian.http.netty.core.handle.common.CommonHandler;
import tianjian.http.util.UUIDTool;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
 * @version 1.0, 2017/8/17
 * @description 过滤url并根据url做出正确处理
 */
@ChannelHandler.Sharable
public class HttpOauth2ServiceHandler extends SimpleChannelInboundHandler<FullHttpRequest> implements CommonHandler {

    @Autowired
    ResourceGuard resourceGuard;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param ctx ChannelHandlerContext 上下文环境
     * @param request 请求
     * @return void
     * @description 模拟单点登录服务验证模块
     */
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        String url = request.getUri();

        UrlInfo userInfo = HttpUtil.getUrlInfoByString(url);

        User user = new User();

        user.setName("tianjian");

        user.setPassword("tianjian");

        String token = UUIDTool.getUUID();

        resourceGuard.addTokenUser(token, user);

        if(userInfo.getRequest().startsWith("/validate")) {
            HttpUtil.send301Continue(ctx, "http://localhost:9999/getResource?validateid=" + UUIDTool.getUUID()
                    +"&token=" + UUIDTool.getUUID() + "&redirect=" + userInfo.getParams().get("redirect"));
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
