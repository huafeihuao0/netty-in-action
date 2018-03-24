package nia.chapter2.echoserver;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import nia.chapter2.echoserver.handlers.ServerEchoHandler;

import java.net.InetSocketAddress;

/***
 *  【响应服务器】
 * */
public class EchoServer
{
    private final int port;

    public EchoServer(int port)
    {
        this.port = port;
    }

    public void start() throws Exception
    {
        //nio事件循环组
        EventLoopGroup nioEventLoopGroup = new NioEventLoopGroup();
        try
        {
            //服务启动器，开启全局的io事件控制
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            //配置
            serverBootstrap.group(nioEventLoopGroup)    //设置事件循环组
                           .channel(NioServerSocketChannel.class) //设置处理的通道类型，为服务器版套接字通道
                           .localAddress(new InetSocketAddress(port)) //绑定本地地址和端口
                           .childHandler(new ChannelInitializer<SocketChannel>()  //设置子处理器
                           {
                               @Override
                               public void initChannel(SocketChannel socketChannel) throws Exception
                               {
                                   socketChannel.pipeline()   //绑定通道的处理器链
                                                .addLast(new ServerEchoHandler());   //添加自定义的响应处理器
                               }
                           });

            //获取通道未来事件回调
            ChannelFuture channelFuture = serverBootstrap.bind()//绑定端口
                                                         .sync(); //阻塞

            //打印通道本地地址
            final String TIP = " started and listening for connections on ";
            System.out.println(EchoServer.class.getName() + TIP + channelFuture.channel()
                                                                               .localAddress());
            //同步关闭通道
            channelFuture.channel()
                         .closeFuture()
                         .sync();
        } finally
        {
            //强制关闭事件循环组
            nioEventLoopGroup.shutdownGracefully()
                             .sync();
        }
    }

    public static void main(String[] args) throws Exception
    {
//        if (args.length != 1)
//        {
//            System.err.println("Usage: " + EchoServer.class.getSimpleName() +
//                    " <port>"
//            );
//            return;
//        }
//        int port = Integer.parseInt(args[0]);
        new EchoServer(9999).start();
    }
}
