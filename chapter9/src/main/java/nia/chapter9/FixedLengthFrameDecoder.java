package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/***
 *  【固定帧解码器】
 * */
public class FixedLengthFrameDecoder
        extends ByteToMessageDecoder //继承自字节转消息解码器
{
    //目标大小
    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength)
    {
        if (frameLength <= 0)
        {
            throw new IllegalArgumentException(
                    "frameLength must be a positive integer: " + frameLength);
        }
        this.frameLength = frameLength;
    }

    /***
     *  解码
     * */
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf inBuf, List<Object> outList) throws Exception
    {
        while (inBuf.readableBytes() >= frameLength)    //只有当帧大小满足时候才读
        {
            ByteBuf buf = inBuf.readBytes(frameLength);
            outList.add(buf);
        }
    }
}
