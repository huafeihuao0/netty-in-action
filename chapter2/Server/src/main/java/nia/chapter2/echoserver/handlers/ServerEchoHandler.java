package nia.chapter2.echoserver.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/***
 *  【服务端响应处理器】
 * */
@Sharable
public class ServerEchoHandler
        extends ChannelInboundHandlerAdapter
{
    /***
     *  通道异常的时候回调
     * */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause)
    {
        cause.printStackTrace();
        channelHandlerContext.close();  //关闭通道
    }

    /***
     *  通道读取时候回调，会被回调多次
     * */
    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg)
    {
        ByteBuf msgBuf = (ByteBuf) msg;
        System.out.println("Server received: " + msgBuf.toString(CharsetUtil.UTF_8));
        //将读取的消息写回给客户端
        channelHandlerContext.write(msgBuf);
    }

    /***
     *  读取完成的时候回调
     * */
    @Override
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception
    {
        channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER)//刷新写缓存
           .addListener(ChannelFutureListener.CLOSE); //完成后关闭通道
    }
}
