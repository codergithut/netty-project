package netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import netty.handler.SecureChatServerInitializer;

import java.net.InetSocketAddress;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/8/17
 * @description
 */
public class SecureChatServer extends ChatServer {
    private final SslContext context;

    public SecureChatServer(SslContext context) {
        this.context = context;
    }

    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new SecureChatServerInitializer(group, context);
    }

    public static void main(String[] args)  {
        int port = 8080;
        try {
            SelfSignedCertificate cert = new SelfSignedCertificate();
            SslContext context = SslContext.newServerContext(cert.certificate(), cert.privateKey());
            final SecureChatServer endpoint = new SecureChatServer(context);
            ChannelFuture future = endpoint.start(new InetSocketAddress(port));
            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run() {
                    endpoint.destory();
                }
            });
            future.channel().closeFuture().syncUninterruptibly();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
