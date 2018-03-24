package nia.chapter2.echoclient.handlers;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/***
 *  【客户端响应处理器】
 * */
@Sharable
public class EchoClientHandler
        extends SimpleChannelInboundHandler<ByteBuf>    //继承简单通道入站处理器
{
    /***
     *  处理异常回调
     * */
    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause)
    {
        cause.printStackTrace();
        channelHandlerContext.close();  //关闭通道
    }

    /***
     *  通道活跃的时候回调
     * */
    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext)
    {
        //发送给服务端小弟
        channelHandlerContext.writeAndFlush(Unpooled.copiedBuffer("Netty rocks!",
                CharsetUtil.UTF_8));
    }

    /***
     *  0级别读的时候回调
     * */
    @Override
    public void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf msgBuf)
    {
        System.out.println(
                "Client received: " + msgBuf.toString(CharsetUtil.UTF_8));
    }

}
