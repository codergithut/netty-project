package netty.pipeline;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import netty.handler.HttpRequestHandler;
import netty.handler.TextWebSocketFrameHandler;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
 * @version 1.0, 2017/8/17
 * @description 初始化信道事件
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {
    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 用户Channel集合
     */
    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }


    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /**
         * http/s 编解码服务
         */
        pipeline.addLast(new HttpServerCodec());
        /**
         * 大块文件读写服务
         */
        pipeline.addLast(new ChunkedWriteHandler());
        /**
         * 获取完整的http/s请求内容，http/s 发送消息内容如果较大会分多次发送，我们只有等消息完全接受才好进一步处理
         */
        pipeline.addLast(new HttpObjectAggregator(64*1024));
        /**
         * 过滤不是websocket通信的请求，如果是将请求扔到楼下处理，如果不是按照代码逻辑处理（返回websocket客户端）
         */
        pipeline.addLast(new HttpRequestHandler("/ws"));
        /**
         * 升级普通的http/s协议为ws服务协议
         */
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        /**
         * 为wesocket添加逻辑服务，消息发送方式
         */
        pipeline.addLast(new TextWebSocketFrameHandler(group));
    }
}
