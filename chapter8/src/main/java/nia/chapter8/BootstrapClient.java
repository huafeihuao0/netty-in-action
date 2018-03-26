package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/***
 *  【引导启动客户端】
 * */
public class BootstrapClient
{
    /***
     *  引导启动客户端
     * */
    public void bootstrap()
    {
        //nio版的事件循环组
        EventLoopGroup nioEvLoopGrp = new NioEventLoopGroup();
        //实例化客户端引导
        Bootstrap cliBootstrap = new Bootstrap();
        cliBootstrap.group(nioEvLoopGrp)    //设置事件循环组
                    .channel(NioSocketChannel.class) //设置nio版本的客户端套接字通道
                    .handler(new SimplePrintChannelHandler());

        //连接到远程服务器，返回未来
        InetSocketAddress remoteAddr = new InetSocketAddress("www.manning.com", 80);
        ChannelFuture future = cliBootstrap.connect(remoteAddr);
        //设置异步未来监听器
        future.addListener(new ConnectFutureListener());
    }

    /***
     *  简单打印处理器
     * */
    public class SimplePrintChannelHandler
            extends SimpleChannelInboundHandler<ByteBuf>    //设置单处理器
    {
        /***
         *  有可读消息时候回调
         * */
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception
        {
            System.out.println("Received data");
        }
    }

    /***
     *  【链接未来监听器】
     * */
    private class ConnectFutureListener
            implements ChannelFutureListener
    {
        /***
         *  操作完成回调
         * */
        @Override
        public void operationComplete(ChannelFuture connectFuture) throws Exception
        {
            if (connectFuture.isSuccess())  //链接成功
            {
                System.out.println("Connection established");
            } else  //链接失败
            {
                System.err.println("Connection attempt failed");
                connectFuture.cause()
                             .printStackTrace();
            }
        }
    }

    public static void main(String args[])
    {
        BootstrapClient client = new BootstrapClient();
        client.bootstrap();
    }
}
