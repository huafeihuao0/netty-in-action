package nia.chapter10;

import io.netty.channel.CombinedChannelDuplexHandler;

/***
 *  【组合编解码器】
 * */
public class CombinedByteCharCodec
        extends CombinedChannelDuplexHandler<ByteToCharDecoder, CharToByteEncoder>
{
    public CombinedByteCharCodec()
    {
        super(new ByteToCharDecoder(), new CharToByteEncoder());
    }
}
