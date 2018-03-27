package nia.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;
import nia.chapter11.utils.DefSimpleInboundHandler;

/***
 *  【基于行分隔符的帧解码器】
 * */
public class LineBasedHandlerInitializer
        extends ChannelInitializer<Channel>
{
    @Override
    protected void initChannel(Channel channel) throws Exception
    {
        channel.pipeline()
               //添加基于长度的帧解码器
               .addLast(new LineBasedFrameDecoder(64 * 1024))
               .addLast(new DefSimpleInboundHandler<ByteBuf>());
    }
}
