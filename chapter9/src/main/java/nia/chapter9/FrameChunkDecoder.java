package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/***
 *  【帧数据块解码器】
 * */
public class FrameChunkDecoder
        extends ByteToMessageDecoder
{
    private final int maxFrameSize;

    public FrameChunkDecoder(int maxFrameSize)
    {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext handlerContext,
                          ByteBuf inBuf,
                          List<Object> outList) throws Exception
    {
        int readableBytes = inBuf.readableBytes();
        if (readableBytes > maxFrameSize)
        {
            // discard the bytes
            inBuf.clear();
            throw new TooLongFrameException();  //超出大小异常
        }
        ByteBuf buf = inBuf.readBytes(readableBytes);
        outList.add(buf);
    }
}
