package tianjian.http.netty.core.contain.common;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import netty.tianjian.log.annotation.AopLog;
import netty.tianjian.log.annotation.AuditLog;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
 * @version 1.0, 2017/8/17
 * @description websocket服务端引导
 */
@Service
public class ChatServer {

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description websocket通信信道
     */
    @Autowired
    @Qualifier("client")
    ChannelGroup channelGroup;

    @Autowired
    ChatServerInitializer chatServerInitializer;
    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 事件池
     */
    private final EventLoopGroup group =new NioEventLoopGroup();

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 本地监听信道
     */
    private Channel channel;

    public ChannelFuture start(InetSocketAddress address) {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(group).channel(NioServerSocketChannel.class).childHandler(chatServerInitializer);
        ChannelFuture future = bootstrap.bind(address);
        future.syncUninterruptibly();
        channel = future.channel();
        return future;
    }


    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param
     * @return
     * @description 信道销魂
     */
    public void destory() {
        if(channel != null) {
            channel.close();
        }
        channelGroup.close();
        group.shutdownGracefully();
    }

    public ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @param args 运行参数
     * @return void
     * @description 开启服务
     */
    @AopLog
    public void run(String[] args) {
        int port = 8080;
        ChannelFuture future = start(new InetSocketAddress(port));
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                destory();
            }
        });
        future.channel().closeFuture().syncUninterruptibly();
    }
}
