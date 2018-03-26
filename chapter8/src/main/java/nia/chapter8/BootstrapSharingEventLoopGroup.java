package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/***
 *  【客/服共享事件循环组】
 * */
public class BootstrapSharingEventLoopGroup
{
    /**
     * Listing 8.5 Bootstrapping a server
     */
    public void bootstrap()
    {
        //外层的服务端引导
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup(), new NioEventLoopGroup()) //设置2个事件循环组，分别管理服务端和客户端套接字通道
                       .channel(NioServerSocketChannel.class) //设置服务端套接字通道
                       .childHandler(new SharedEvLoopClientHandler());//设置子通道处理器，实现共享客户端事件循环组

        ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new BootstrapDatagramChannel.BindFutureListener());
    }

    /***
     *  【共享事件循环客户端】
     * */
    public class SharedEvLoopClientHandler
            extends SimpleChannelInboundHandler<ByteBuf>
    {
        ChannelFuture connectFuture;

        @Override
        public void channelActive(ChannelHandlerContext ctx)
        throws Exception
        {
            //链接数据库的客户端启动引导
            Bootstrap dbClientBootstrap = new Bootstrap();
            dbClientBootstrap.channel(NioSocketChannel.class)
                     .handler(new BootstrapServer.SimplePrintHandler());
            //【设置共享的事件循环】
            dbClientBootstrap.group(ctx.channel()
                               .eventLoop());
            connectFuture = dbClientBootstrap.connect(
                    new InetSocketAddress("www.manning.com", 80));
        }

        @Override
        protected void channelRead0(
                ChannelHandlerContext channelHandlerContext,
                ByteBuf byteBuf) throws Exception
        {
            if (connectFuture.isDone())
            {
                // do something with the data
            }
        }
    }
}
