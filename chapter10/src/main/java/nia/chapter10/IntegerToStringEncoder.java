package nia.chapter10;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;


/***
 *  【整数转字符串编码器】
 * */
public class IntegerToStringEncoder
        extends MessageToMessageEncoder<Integer>
{
    @Override
    public void encode(ChannelHandlerContext ctx,
                       Integer inInt,
                       List<Object> outList) throws Exception
    {
        outList.add(String.valueOf(inInt));
    }
}

