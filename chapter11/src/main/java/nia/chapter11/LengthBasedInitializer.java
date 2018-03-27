package nia.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import nia.chapter11.utils.DefSimpleInboundHandler;

/***
 *  【基于长度的协议处理器】
 * */
public class LengthBasedInitializer
        extends ChannelInitializer<Channel>
{
    @Override
    protected void initChannel(Channel ch) throws Exception
    {
        ch.pipeline()
          //基于长度的帧解码器
          .addLast(new LengthFieldBasedFrameDecoder(64 * 1024, 0, 8))
          .addLast(new DefSimpleInboundHandler<ByteBuf>());
    }
}
