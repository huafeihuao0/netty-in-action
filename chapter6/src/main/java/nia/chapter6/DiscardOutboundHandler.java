package nia.chapter6;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.ReferenceCountUtil;

/***
 *  【出站资源释放】
 * */
@Sharable
public class DiscardOutboundHandler
        extends ChannelOutboundHandlerAdapter   //继承自出站处理器适配器
{
    /***
     *  写回调
     * */
    @Override
    public void write(ChannelHandlerContext handlerContext, Object msg, ChannelPromise channelPromise)
    {
        //释放资源
        ReferenceCountUtil.release(msg);
        //设置成功承诺
        channelPromise.setSuccess();
    }
}

