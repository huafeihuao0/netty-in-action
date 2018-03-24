package nia.chapter2.echoclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import nia.chapter2.echoclient.handlers.EchoClientHandler;

import java.net.InetSocketAddress;

/***
 *  【响应客户端】
 * */
public class EchoClient
{
    private final String host;
    private final int port;

    public EchoClient(String host, int port)
    {
        this.host = host;
        this.port = port;
    }

    /***
     *  启动客户端
     * */
    public void start() throws Exception
    {
        //io事件循环组
        EventLoopGroup nioEvGrp = new NioEventLoopGroup();
        try
        {
            //客户端启动器，开启全局的io事件循环
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(nioEvGrp)//添加事件循环组
             .channel(NioSocketChannel.class)   //通道类型为普通的链接套接字通道
             .remoteAddress(new InetSocketAddress(host, port))  //链接远程服务器
             .handler(new ChannelInitializer<SocketChannel>()   //添加处理器
             {
                 @Override
                 public void initChannel(SocketChannel socketChannel) throws Exception
                 {
                     socketChannel.pipeline()
                       .addLast(new EchoClientHandler());   //添加自定义的处理器
                 }
             });

            //链接，并阻塞式监听事件
            ChannelFuture channelFuture = bootstrap.connect()
                               .sync();
            //关闭通道
            channelFuture.channel()
             .closeFuture()
             .sync();
        } finally
        {
            //强制关闭事件循环
            nioEvGrp.shutdownGracefully()
                 .sync();
        }
    }

    public static void main(String[] args) throws Exception
    {
//        if (args.length != 2)
//        {
//            System.err.println("Usage: " + EchoClient.class.getSimpleName() +
//                    " <host> <port>"
//            );
//            return;
//        }

//        final String host = args[0];
//        final int port = Integer.parseInt(args[1]);
        new EchoClient("localhost", 9999).start();
    }
}

