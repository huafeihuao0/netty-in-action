package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.oio.OioSocketChannel;

import java.net.InetSocketAddress;

/***
 *  【不兼容的启动客户端】
 * */
public class InvalidBootstrapClient
{
    /**
     * Listing 8.3 Incompatible Channel and EventLoopGroup
     */
    public void bootstrap()
    {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup())    //NIO版本的事件循环组
                 .channel(OioSocketChannel.class)   //OIO版本的套接字通道，与上述不兼容
                 .handler(new BootstrapServer.SimplePrintHandler());
        ChannelFuture future = bootstrap.connect(
                new InetSocketAddress("www.manning.com", 80));
        future.syncUninterruptibly();
    }

    public static void main(String args[])
    {
        InvalidBootstrapClient client = new InvalidBootstrapClient();
        client.bootstrap();
    }
}
