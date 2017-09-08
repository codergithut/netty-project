package tianjian.http.spring;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import tianjian.http.filter.impl.ResourceFilter;
import tianjian.http.netty.core.contain.common.ChatServerInitializer;
import tianjian.http.netty.core.handle.common.HttpFilterAfterHandler;
import tianjian.http.netty.core.handle.common.HttpFilterBeforeHandler;
import tianjian.http.netty.core.handle.common.HttpRequestHandler;
import tianjian.http.netty.core.handle.oauht2.HttpOauth2ClientHandler;
import tianjian.http.netty.core.handle.oauht2.HttpOauth2ResourceHandler;
import tianjian.http.netty.core.handle.oauht2.HttpOauth2ServiceHandler;
import tianjian.http.view.FreeMarkerEngine;

/**
 * @author <a href="mailto:Administrator@gtmap.cn">Administrator</a>
 * @version 1.0, 2017/9/1
 * @description spring 资源注入
 */
@Configurable
@ComponentScan("tianjian")
@PropertySource("classpath:/application.properties")
@ImportResource("classpath:/spring/app-aop.xml")
public class NettyServerConfig {

    @Autowired
    StandardEnvironment environment;

    @Bean
    public HttpFilterBeforeHandler getHttpFilterBeforeHandler() {
        return new HttpFilterBeforeHandler();
    }

    @Bean
    public HttpFilterAfterHandler getHttpFilterAfterHandler() {
        return new HttpFilterAfterHandler();
    }

    @Bean
    public HttpRequestHandler getHttpRequestHandler() {
        return new HttpRequestHandler();
    }

    @Bean
    public ChatServerInitializer getChatServerInitializer() {
        return new ChatServerInitializer();
    }

    @Bean
    public ResourceFilter getResourceFilter() {
        ResourceFilter resourceFilter = new ResourceFilter();
        resourceFilter.setResourcePath(environment.getProperty("netty.static.resource"));
        return resourceFilter;
    }

    @Bean(value = "oauth2")
    public ChannelGroup getOauth2ChannelGroup() {
        return new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    }

    @Bean(value = "client")
    public ChannelGroup getChannelGroup() {
        return new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
    }

    @Bean
    public FreeMarkerEngine getFreeMarkerEngine() {
        FreeMarkerEngine freeMarkerEngine = new FreeMarkerEngine(environment.getProperty("netty.freemarker.suffix"), environment.getProperty("netty.freemarker.template-loader-path"));
        return freeMarkerEngine;
    }


    @Bean
    public HttpOauth2ClientHandler getHttpOauth2ClientHandler() {
        return new HttpOauth2ClientHandler();
    }

    @Bean
    public HttpOauth2ResourceHandler getHttpOauth2ResourceHandler() {
        return new HttpOauth2ResourceHandler();
    }

    @Bean
    public HttpOauth2ServiceHandler getHttpOauth2ServiceHandler() {
        return new HttpOauth2ServiceHandler();
    }

}
