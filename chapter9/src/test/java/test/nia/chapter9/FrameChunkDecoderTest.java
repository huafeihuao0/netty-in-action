package test.nia.chapter9;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.TooLongFrameException;
import nia.chapter9.FrameChunkDecoder;
import org.junit.Assert;
import org.junit.Test;
import test.nia.chapter9.utils.BufUtils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/***
 *  【处理器异常测试】
 * */
public class FrameChunkDecoderTest
{
    @Test
    public void testFramesDecoded()
    {
        ByteBuf intBuf = BufUtils.lengthedByteBuf(40);
        ByteBuf inputBuf = intBuf.duplicate();

        EmbeddedChannel channel = new EmbeddedChannel(new FrameChunkDecoder(3));

        assertTrue(channel.writeInbound(inputBuf.readBytes(2)));
        //【try-catch】捕捉处理器异常
        try
        {
            channel.writeInbound(inputBuf.readBytes(4));
            Assert.fail();
        } catch (TooLongFrameException e)
        {
            // expected exception
        }
        assertTrue(channel.writeInbound(inputBuf.readBytes(3)));
        assertTrue(channel.finish());

        // Read frames
        ByteBuf read = (ByteBuf) channel.readInbound();
        assertEquals(intBuf.readSlice(2), read);
        read.release();

        read = (ByteBuf) channel.readInbound();
        assertEquals(intBuf.skipBytes(4)
                        .readSlice(3), read);
        read.release();
        intBuf.release();
    }
}
