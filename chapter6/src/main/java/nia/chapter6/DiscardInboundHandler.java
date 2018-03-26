package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/***
 *  【消耗和释放入栈信息】
 * */
@Sharable
public class DiscardInboundHandler
        extends ChannelInboundHandlerAdapter    //继承自入栈处理器适配器
{
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
    {
        ReferenceCountUtil.release(msg);
    }
}
