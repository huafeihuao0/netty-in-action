package nia.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/***
 *  【短整型转字节编码器】
 * */
public class ShortToByteEncoder
        extends MessageToByteEncoder<Short>
{
    @Override
    public void encode(ChannelHandlerContext ctx,
                       Short inShort,
                       ByteBuf outBuf) throws Exception
    {
        outBuf.writeShort(inShort);//写入short
    }
}
