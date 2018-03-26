package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.oio.OioDatagramChannel;

import java.net.InetSocketAddress;

/***
 *  【引导UDP客户端】
 * */
public class BootstrapDatagramChannel
{
    /**
     * Listing 8.8 Using Bootstrap with DatagramChannel
     */
    public void bootstrap()
    {
        Bootstrap cliBootstrap = new Bootstrap();
        cliBootstrap.group(new OioEventLoopGroup()) //设置OIO事件循环组
                    .channel(OioDatagramChannel.class)//设置OIO版本的数据报通道
                    .handler(new MySimpleHandler());

        ChannelFuture bindFuture = cliBootstrap.bind(new InetSocketAddress(0));
        bindFuture.addListener(new BindFutureListener());
    }

    /***
     *  【自定义的简单处理器】
     * */
    private class MySimpleHandler
            extends SimpleChannelInboundHandler<DatagramPacket>//传入数据报包
    {
        @Override
        public void channelRead0(ChannelHandlerContext ctx,
                                 DatagramPacket msg) throws Exception
        {
            // Do something with the packet
        }
    }

    /***
     *  【绑定未来监听器】
     * */
    public static class BindFutureListener implements ChannelFutureListener
    {
        @Override
        public void operationComplete (ChannelFuture future) throws Exception
        {
            if (future.isSuccess())
            {
                System.out.println("Channel bound");
            } else
            {
                System.err.println("Bind attempt failed");
                future.cause()
                      .printStackTrace();
            }
        }
    }
}
