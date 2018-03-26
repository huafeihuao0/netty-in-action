package nia.chapter8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.InetSocketAddress;

/***
 *  【使用初始化器初始化多个处理器】
 * */
public class BootstrapWithInitializer
{
    /**
     * Listing 8.6 Bootstrapping and using ChannelInitializer
     */
    public void bootstrap() throws InterruptedException
    {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup())
                 .channel(NioServerSocketChannel.class)
                 .childHandler(new ChannelInitializerImpl());

        ChannelFuture bindFuture = serverBootstrap.bind(new InetSocketAddress(8080));
        bindFuture.addListener(new BootstrapDatagramChannel.BindFutureListener());
        bindFuture.sync();
    }

    /***
     *  【设置多个处理器的初始化器】
     * */
    final class ChannelInitializerImpl
            extends ChannelInitializer<Channel>
    {
        @Override
        protected void initChannel(Channel channel) throws Exception
        {
            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addLast(new HttpClientCodec());    //设置解码器
            pipeline.addLast(new HttpObjectAggregator(Integer.MAX_VALUE));//设置最大值处理器
        }
    }
}
