package nia.chapter6;


import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/***
 *  【持有处理器上下文】
 * */
public class WriteHandler
        extends ChannelHandlerAdapter
{
    private ChannelHandlerContext ctx;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx)
    {
        this.ctx = ctx;
    }

    public void send(String msg)
    {
        ctx.writeAndFlush(msg);
    }
}
