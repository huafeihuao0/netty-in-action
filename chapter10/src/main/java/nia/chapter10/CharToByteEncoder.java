package nia.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/***
 *  【字符编码器】
 * */
public class CharToByteEncoder
        extends MessageToByteEncoder<Character>
{
    @Override
    public void encode(ChannelHandlerContext handlerContext,
                       Character inMsgChs,
                       ByteBuf outBuf) throws Exception
    {
        outBuf.writeChar(inMsgChs); //转换成字节流
    }
}

