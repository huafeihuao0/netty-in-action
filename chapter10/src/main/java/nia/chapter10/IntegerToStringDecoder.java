package nia.chapter10;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/***
 *  【整数转字符串解码器】
 * */
public class IntegerToStringDecoder
        extends MessageToMessageDecoder<Integer>
{
    @Override
    public void decode(ChannelHandlerContext ctx,
                       Integer inInt,
                       List<Object> outList) throws Exception
    {
        outList.add(String.valueOf(inInt));//转换为字符串
    }
}

