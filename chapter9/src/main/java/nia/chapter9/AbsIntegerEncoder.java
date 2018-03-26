package nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/***
 *  【绝对值编码器】
 * */
public class AbsIntegerEncoder
        extends MessageToMessageEncoder<ByteBuf>    //继承自消息转换编码器
{
    /***
     *  编码
     * */
    @Override
    protected void encode(
            ChannelHandlerContext channelHandlerContext,
            ByteBuf inBuf,
            List<Object> outList) throws Exception
    {
        while (inBuf.readableBytes() >= 4)//必须大于4个字节
        {
            //取绝对值
            int absInt = Math.abs(inBuf.readInt());
            outList.add(absInt);
        }
    }
}
