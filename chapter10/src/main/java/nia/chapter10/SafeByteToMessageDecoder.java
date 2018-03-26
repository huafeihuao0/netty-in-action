package nia.chapter10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/***
 *  【安全版字节转消息解码器】
 * */
public class SafeByteToMessageDecoder
        extends ByteToMessageDecoder
{
    private static final int MAX_FRAME_SIZE = 1024;

    @Override
    public void decode(ChannelHandlerContext ctx,
                       ByteBuf inBuf,
                       List<Object> outList) throws Exception
    {
        int readable = inBuf.readableBytes();
        if (readable > MAX_FRAME_SIZE)  //如果可读字节大于最大帧大小
        {
            inBuf.skipBytes(readable);
            throw new TooLongFrameException("Frame too big!");  //抛出帧过大异常
        }
    }
}
