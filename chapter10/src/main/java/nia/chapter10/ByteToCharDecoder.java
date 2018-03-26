package nia.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/***
 *  字符解码器
 * */
public class ByteToCharDecoder
        extends ByteToMessageDecoder
{
    @Override
    public void decode(ChannelHandlerContext handlerContext,
                       ByteBuf inBuf,
                       List<Object> outList) throws Exception
    {
        if (inBuf.readableBytes() >= 2) //可读字节必须不小于2
        {
            outList.add(inBuf.readChar());//读取一个字符
        }
    }
}

