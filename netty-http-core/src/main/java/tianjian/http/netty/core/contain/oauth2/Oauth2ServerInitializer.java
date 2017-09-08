package tianjian.http.netty.core.contain.oauth2;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.stream.ChunkedWriteHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import tianjian.http.netty.core.handle.common.HttpFilterBeforeHandler;
import tianjian.http.netty.core.handle.common.HttpRequestHandler;
import tianjian.http.netty.core.handle.oauht2.HttpOauth2ClientHandler;
import tianjian.http.netty.core.handle.oauht2.HttpOauth2ResourceHandler;
import tianjian.http.netty.core.handle.oauht2.HttpOauth2ServiceHandler;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
 * @version 1.0, 2017/8/17
 * @description 初始化信道事件
 */
@Scope("prototype")
@Service
public class Oauth2ServerInitializer extends ChannelInitializer<Channel> {
    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 用户Channel集合 同上描述
     */
    @Autowired
    @Qualifier("oauth2")
    ChannelGroup group;

    @Autowired
    HttpOauth2ClientHandler httpOauth2ClientHandler;

    @Autowired
    HttpOauth2ResourceHandler httpOauth2ResourceHandler;

    @Autowired
    HttpOauth2ServiceHandler httpOauth2ServiceHandler;

    @Override
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
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        /**
         * 过滤不是websocket通信的请求，如果是将请求扔到楼下处理，如果不是按照代码逻辑处理（返回websocket客户端）
         */
        pipeline.addLast(httpOauth2ClientHandler);

        pipeline.addLast(httpOauth2ResourceHandler);

        pipeline.addLast(httpOauth2ServiceHandler);



    }
}
