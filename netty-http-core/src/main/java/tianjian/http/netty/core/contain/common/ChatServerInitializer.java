package tianjian.http.netty.core.contain.common;

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
import tianjian.http.netty.core.handle.common.HttpFilterBeforeHandler;
import tianjian.http.netty.core.handle.common.HttpRequestHandler;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
 * @version 1.0, 2017/8/17
 * @description 初始化信道事件 容器初始化类
 */
@Scope("prototype")
public class ChatServerInitializer extends ChannelInitializer<Channel> {
    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 用户Channel集合
     */
    @Autowired
    @Qualifier("client")
    ChannelGroup group;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description Http实际处理链
     */
    @Autowired
    HttpRequestHandler httpRequestHandler;

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description Http初始化过滤链
     */
    @Autowired
    HttpFilterBeforeHandler httpFilterBeforeHandler;

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
         * 前置过滤器
         */
        pipeline.addLast(httpFilterBeforeHandler);

        /**
         * url处理类
         */
        pipeline.addLast(httpRequestHandler);

    }
}
