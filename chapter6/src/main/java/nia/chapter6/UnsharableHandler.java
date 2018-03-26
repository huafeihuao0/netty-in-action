package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 *  【非线程安全的处理器】
 * */
@Sharable
public class UnsharableHandler
        extends ChannelInboundHandlerAdapter
{
    private int count;  //线程不安全的状态

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
        count++;
        System.out.println("inboundBufferUpdated(...) called the "
                + count + " time");
        ctx.fireChannelRead(msg);
    }
}

