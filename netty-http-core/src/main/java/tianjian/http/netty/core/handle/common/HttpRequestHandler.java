package tianjian.http.netty.core.handle.common;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import org.springframework.beans.factory.annotation.Autowired;
import tianjian.http.controller.BaseControllerHandle;
import tianjian.http.model.UrlInfo;
import tianjian.http.netty.core.handle.*;
import tianjian.http.netty.core.handle.common.CommonHandler;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
 * @version 1.0, 2017/8/17
 * @description 过滤url并根据url做出正确处理
 */
@ChannelHandler.Sharable
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> implements CommonHandler {

    @Autowired
    BaseControllerHandle baseControllerHandle;


    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param ctx ChannelHandlerContext 上下文环境
     * @param request 请求
     * @return void
     * @description 成功url实际处理方法
     */
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws Exception {

        String url = request.getUri();

        UrlInfo userInfo = tianjian.http.netty.core.handle.HttpUtil.getUrlInfoByString(url);

        File file = baseControllerHandle.handlRequest(userInfo.getRequest(), null);

        RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

        writeResponseToClient(request, randomAccessFile, ctx, false);

        writeContentToClient(randomAccessFile, request, ctx);

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
