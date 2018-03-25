package nia.chapter4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

/***
 *  【Netty版本的异步IO服务器】
 * */
public class NettyNioServer
{
    public void server(int port) throws Exception
    {
        //nio版本的事件循环组
        NioEventLoopGroup nioEvLoopGrp = new NioEventLoopGroup();
        try
        {
            //实例化服务端io启动器
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(nioEvLoopGrp)    //添加nio版事件循环组
                    .channel(NioServerSocketChannel.class)  //添加nio版本的服务端套接字通道
                    .localAddress(new InetSocketAddress(port))  //绑定
                    .childHandler(new MyChannelIniter());   //设置子通道初始化器

            ChannelFuture channelFuture = serverBootstrap
                    .bind()//绑定
                    .sync()
                    .channel()  //获取通道
                    .closeFuture()  //关闭事件监听
                    .sync();
        } finally
        {
            nioEvLoopGrp.shutdownGracefully()
                        .sync();
        }
    }

    /***
     *  【自定义的通道初始化器】
     * */
    private class MyChannelIniter
            extends ChannelInitializer<SocketChannel>
    {
        @Override
        public void initChannel(SocketChannel ch) throws Exception
        {
            ch.pipeline()
              .addLast(new ChannelInboundHandlerAdapter()
              {
                  /***
                   *  通道链接成功后回调
                   * */
                  @Override
                  public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception
                  {
                      ByteBuf hiBuf = Unpooled.copiedBuffer("Hi!\r\n", Charset.forName("UTF-8"));
                      final ByteBuf buf = Unpooled.unreleasableBuffer(hiBuf);

                      channelHandlerContext.writeAndFlush(buf.duplicate())
                         .addListener(ChannelFutureListener.CLOSE);
                  }
              });
        }
    }
}

