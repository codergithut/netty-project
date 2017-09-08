package tianjian.http.netty.core.contain.oauth2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.InetSocketAddress;

/**
 * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
 * @version 1.0, 2017/8/17
 * @description websocket服务端引导
 */
@Service
public class Oauth2Server {
    /**
     * @author <a href="mailto:tianjian@gtmap.cn">tianjian</a>
     * @description 同上描述
     */
    @Autowired
    @Qualifier("oauth2")
    ChannelGroup channelGroup;

    @Autowired
    Oauth2ServerInitializer oauth2ServerInitializer;

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
        bootstrap.group(group).channel(NioServerSocketChannel.class).childHandler(oauth2ServerInitializer);
        ChannelFuture future = bootstrap.bind(address);
        future.syncUninterruptibly();
        channel = future.channel();
        return future;
    }


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

    public void run(String[] args) {
        int port = 9999;
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
