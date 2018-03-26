package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/***
 *  【使用简单入站处理器实现默认的资源释放功能】
 * */
@Sharable
public class SimpleDiscardHandler
        extends SimpleChannelInboundHandler<Object>
{
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg)
    {
        // No need to do anything special
    }
}
