package nia.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/***
 *  【使用RepayingDecoder实现简便的解码器】
 * */
public class ToIntegerDecoder2
        extends ReplayingDecoder<Void>
{
    @Override
    public void decode(ChannelHandlerContext handlerContext,
                       ByteBuf inBuf,
                       List<Object> outList) throws Exception
    {
        outList.add(inBuf.readInt());
    }
}

