package tianjian.http.netty.core.handle.common;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/4
 * @description 实际消息处理工具
 */
public interface CommonHandler {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param randomAccessFile 需要写入客户端的用户请求文件
     * @param request 请求对象
     * @param ctx 需要写入的netty上下文环境
     * @return 是否写入成功
     * @description 将消息头写入服务器中
     */
    default boolean writeContentToClient(RandomAccessFile randomAccessFile, FullHttpRequest request, ChannelHandlerContext ctx) throws IOException {

        if(ctx.pipeline().get(SslHandler.class) == null) {
            ctx.write(new DefaultFileRegion(randomAccessFile.getChannel(), 0, randomAccessFile.length()));
        } else {
            ctx.write(new ChunkedNioFile(randomAccessFile.getChannel()));
        }

        ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

        if(!HttpHeaders.isKeepAlive(request)) {
            future.addListener(ChannelFutureListener.CLOSE);
        }

        return true;
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param request 需要写入客户端的头文件
     * @param request 请求对象
     * @param ctx 需要写入的netty上下文环境
     * @return 是否写入成功
     * @description 将消息头写入服务器中
     */
    default boolean writeResponseToClient(FullHttpRequest request, RandomAccessFile file, ChannelHandlerContext ctx, boolean isResource)throws IOException {
        HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);

        if(!isResource) {
            response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");
        }


        if(HttpHeaders.isKeepAlive(request)) {

            response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());

            response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);

        }

        ctx.write(response);

        return true;
    }

}
