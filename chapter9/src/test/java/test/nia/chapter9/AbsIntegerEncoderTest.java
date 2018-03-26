package test.nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import nia.chapter9.AbsIntegerEncoder;
import org.junit.Test;
import test.nia.chapter9.utils.BufUtils;

import static org.junit.Assert.*;

/***
 *  【绝对值转换器出站检测】
 * */
public class AbsIntegerEncoderTest
{
    @Test
    public void testEncoded()
    {
        ByteBuf buf = BufUtils.lengthedByteBuf(10);

        //实例化包含绝对值编码器的嵌入式通道
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new AbsIntegerEncoder());

        //数据出站
        assertTrue(embeddedChannel.writeOutbound(buf)); //判断是否成功
        assertTrue(embeddedChannel.finish());//判断是否完成

        //读取
        for (int i = 1; i < 10; i++)
        {
            assertEquals(i, embeddedChannel.readOutbound());
        }
        assertNull(embeddedChannel.readOutbound());
    }
}
