package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

import java.net.InetSocketAddress;

/***
 *  【优雅关闭】
 * */
public class GracefulShutdown
{
    public static void main(String args[])
    {
        GracefulShutdown client = new GracefulShutdown();
        client.bootstrap();
    }

    /**
     * Listing 8.9 Graceful shutdown
     */
    public void bootstrap()
    {
        EventLoopGroup nioEvLoopGrp = new NioEventLoopGroup();
        Bootstrap cliBootstrap = new Bootstrap();
        cliBootstrap.group(nioEvLoopGrp)
                    .channel(NioSocketChannel.class)
                    .handler(new BootstrapServer.SimplePrintHandler());

        cliBootstrap.connect(new InetSocketAddress("www.manning.com", 80))
                    .syncUninterruptibly();//同步阻塞

        //优雅关闭事件循环组
        Future<?> future = nioEvLoopGrp.shutdownGracefully();
        // block until the group has shutdown
        future.syncUninterruptibly();
    }
}
