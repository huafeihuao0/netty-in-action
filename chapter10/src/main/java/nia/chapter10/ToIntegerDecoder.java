package nia.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/***
 *  【字节转整数解码器】
 * */
public class ToIntegerDecoder
        extends ByteToMessageDecoder
{
    @Override
    public void decode(ChannelHandlerContext ctx,
                       ByteBuf inBuf,
                       List<Object> outList) throws Exception
    {
        if (inBuf.readableBytes() >= 4)//int占4个字节
        {
            outList.add(inBuf.readInt());
        }
    }
}

