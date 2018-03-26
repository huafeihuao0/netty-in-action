package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

/***
 *  【自定义的入栈资源释放】
 * */
@Sharable
public class DiscardHandler
        extends ChannelInboundHandlerAdapter
{
    /***
     *  通道可读回调
     * */
    @Override
    public void channelRead(ChannelHandlerContext handlerContext, Object msg)
    {
        ReferenceCountUtil.release(msg);
    }

}

