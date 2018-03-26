package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 *  【线程安全的处理器】
 * */
@Sharable
public class SharableHandler
        extends ChannelInboundHandlerAdapter
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
        System.out.println("channel read message " + msg);
        ctx.fireChannelRead(msg);
    }
}

