package nia.chapter4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/***
 *  【Netty版阻塞式IO】
 * */
public class NettyOioServer
{
    /***
     *  服务
     * */
    public void server(int port) throws Exception
    {
        //事件循环组
        EventLoopGroup eventLoopGroup = new OioEventLoopGroup();
        try
        {
            //实例化服务端启动器
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(eventLoopGroup)  //添加事件循环组
                    .channel(OioServerSocketChannel.class) //阻塞式通道
                    .localAddress(new InetSocketAddress(port)) //绑定地址
                    .childHandler(new MyChannelIniter());    //通道初始化器

            //Future回调操作
            ChannelFuture f = serverBootstrap
                    .bind() //绑定
                    .sync()//阻塞式同步，直到发生新的事件
                    .channel()  //获取内部通道
                    .closeFuture()  //关闭
                    .sync();
        } finally
        {
            //同步关闭事件循环组
            eventLoopGroup.shutdownGracefully()
                          .sync();
        }
    }

    /***
     *  【自定义的通道处理器初始化器】
     * */
    private class MyChannelIniter
            extends ChannelInitializer<SocketChannel>  //子通道处理器初始化器
    {
        /***
         *  初始化通道的时候回调
         * */
        @Override
        public void initChannel(SocketChannel socketChannel) throws Exception
        {
            socketChannel.pipeline()
              .addLast(new ChannelInboundHandlerAdapter()   //添加最后一个入栈通道处理器
              {
                  @Override
                  public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception
                  {
                      //准备写缓冲
                      ByteBuf hiBuf = Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8"));
                      final ByteBuf writeBuf = Unpooled.unreleasableBuffer(hiBuf);
                      channelHandlerContext.writeAndFlush(writeBuf.duplicate())
                         .addListener(ChannelFutureListener.CLOSE);
                  }
              });
        }
    }
}

