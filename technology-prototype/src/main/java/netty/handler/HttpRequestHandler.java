package netty.handler;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
 * @version 1.0, 2017/8/17
 * @description 过滤url并根据url做出正确处理
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 判断是否是ws请求，不是返回可以连接websocket的页面服务，每次浏览器请求判断
     */
    private final String wsUri;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description html websocket客户端存放位置
     */
    private static final File INDEX;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 初始化资源文件
     */
    static {

        URL location = HttpRequestHandler.class.getProtectionDomain().getCodeSource().getLocation();

        String path = null;
        try {
            path = location.toURI() + "/websocket/index.html";
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        path = !path.contains("file:") ? path : path.substring(5);

        INDEX = new File(path);

    }

    public HttpRequestHandler(String wsUri) {

        this.wsUri = wsUri;

    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param ctx ChannelHandlerContext 上下文环境
     * @param request 请求
     * @return
     * @description
     */
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest request) throws IOException {

        RandomAccessFile file = null;

        /**
         * 判断当前请求是不是ws请求如果是让下个handler处理 如果不是返回客户端html文件
         */
        if(wsUri.equalsIgnoreCase(request.getUri())) {

            ctx.fireChannelRead(request.retain());

        } else {

            if(HttpHeaders.is100ContinueExpected(request)) {

                send100Continue(ctx);

            }

            try {

                file = new RandomAccessFile(INDEX, "r");

                HttpResponse response = new DefaultHttpResponse(request.getProtocolVersion(), HttpResponseStatus.OK);

                response.headers().set(HttpHeaders.Names.CONTENT_TYPE, "text/html; charset=UTF-8");

                boolean keepAlive = HttpHeaders.isKeepAlive(request);

                if(keepAlive) {

                    response.headers().set(HttpHeaders.Names.CONTENT_LENGTH, file.length());

                    response.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);

                }

                ctx.write(response);

                if(ctx.pipeline().get(SslHandler.class) == null) {
                    ctx.write(new DefaultFileRegion(file.getChannel(), 0, file.length()));
                } else {
                    ctx.write(new ChunkedNioFile(file.getChannel()));
                }

                ChannelFuture future = ctx.writeAndFlush(LastHttpContent.EMPTY_LAST_CONTENT);

                if(!keepAlive) {
                    future.addListener(ChannelFutureListener.CLOSE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(file != null) {
                    file.close();
                }
            }
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

}
