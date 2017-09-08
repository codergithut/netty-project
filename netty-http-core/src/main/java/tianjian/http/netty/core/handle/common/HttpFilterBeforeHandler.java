package tianjian.http.netty.core.handle.common;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import netty.tianjian.common.util.qrcode.Qrest;
import netty.tianjian.log.annotation.AopLog;
import org.springframework.beans.factory.annotation.Autowired;
import tianjian.http.filter.impl.IegalUrlFilter;
import tianjian.http.filter.impl.ResourceFilter;
import tianjian.http.netty.core.handle.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
 * @version 1.0, 2017/8/17
 * @description 过滤url并根据url做出正确处理
 */
@ChannelHandler.Sharable
public class HttpFilterBeforeHandler extends SimpleChannelInboundHandler<FullHttpRequest> implements CommonHandler, HttpHandlerListener{

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 静态资源过滤器
     */
    @Autowired
    ResourceFilter resourceFilter;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 合法资源过滤器
     */
    @Autowired
    IegalUrlFilter iegalUrlFilter;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 合法资源处理类
     */
    @Autowired
    HttpHandleContains httpHandleContainsl;


    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 临时添加字段用户测试
     */
    boolean flag = true;


    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param ctx ChannelHandlerContext 上下文环境
     * @param request 请求
     * @return void
     * @description
     */
    @AopLog
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws IOException {

        String url = request.getUri();

        resourceFilter.extendFilterUrl("static");

        if(resourceFilter.filterHttpRequest(url)) {

            File file = resourceFilter.HandleHttpRequest(request.getUri());

            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");

            writeResponseToClient(request, randomAccessFile, ctx, true);


            writeContentToClient(randomAccessFile, request, ctx);

            request.retain();

        } else if(iegalUrlFilter.filterHttpRequest(url)) {
            ctx.fireChannelRead(request.retain());
        }
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 发送100消息
     */
    private static void send100Continue(ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush(response);
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

    @Override
    public void handleWaite() {
        httpHandleContainsl.registSimpleHandler(this);
        while (flag) {

        }
    }

    @Override
    public void handleWake() {
        flag = false;
    }
}
