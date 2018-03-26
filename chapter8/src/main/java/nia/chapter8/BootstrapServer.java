package nia.chapter8;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/***
 *  【引导启动服务器】
 * */
public class BootstrapServer
{
    /**
     * Listing 8.4 Bootstrapping a server
     */
    public void bootstrap()
    {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(new NioEventLoopGroup())
                 .channel(NioServerSocketChannel.class)//设置NIO版本的服务端套接字通道
                 .childHandler(new SimplePrintHandler());

        ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(8080));
        future.addListener(new BootstrapDatagramChannel.BindFutureListener());
    }


    /***
     *  【简单打印处理器】
     * */
    public static class SimplePrintHandler
            extends SimpleChannelInboundHandler<ByteBuf>
    {
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext,
                                    ByteBuf byteBuf) throws Exception
        {
            System.out.println("Received data");
        }
    }
}
