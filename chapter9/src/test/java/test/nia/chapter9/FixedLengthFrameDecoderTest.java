package test.nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import nia.chapter9.FixedLengthFrameDecoder;
import org.junit.Test;
import test.nia.chapter9.utils.BufUtils;

import static org.junit.Assert.*;

/***
 *  【固定帧解码器测试】
 * */
public class FixedLengthFrameDecoderTest
{
    @Test
    public void testFramesDecoded()
    {
        ByteBuf intBuf = BufUtils.lengthedByteBuf(10);
        //复制整数缓冲
        ByteBuf inputBuf = intBuf.duplicate();
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        // 数据入站
        assertTrue(embeddedChannel.writeInbound(inputBuf.retain()));
        assertTrue(embeddedChannel.finish());

        //每次读取3个字节
        tryRead3BPertimes(embeddedChannel,inputBuf);
    }

    @Test
    public void testFramesDecoded2()
    {
        ByteBuf buf =BufUtils.lengthedByteBuf(10);
        ByteBuf inputBuf = buf.duplicate();

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecoder(3));

        //分段入站
        assertFalse(embeddedChannel.writeInbound(inputBuf.readBytes(2)));
        assertTrue(embeddedChannel.writeInbound(inputBuf.readBytes(7)));
        assertTrue(embeddedChannel.finish());

       //每次读取3个字节
        tryRead3BPertimes(embeddedChannel,inputBuf);
    }

    /***
     *  每次读取3个字节
     * */
    private void tryRead3BPertimes(EmbeddedChannel embeddedChannel,ByteBuf intBuf)
    {
        ByteBuf readBuf = (ByteBuf) embeddedChannel.readInbound();
        assertEquals(intBuf.readSlice(3), readBuf);
        readBuf.release();

        readBuf = (ByteBuf) embeddedChannel.readInbound();
        assertEquals(intBuf.readSlice(3), readBuf);
        readBuf.release();

        readBuf = (ByteBuf) embeddedChannel.readInbound();
        assertEquals(intBuf.readSlice(3), readBuf);
        readBuf.release();

        assertNull(embeddedChannel.readInbound());
        intBuf.release();
    }
}
