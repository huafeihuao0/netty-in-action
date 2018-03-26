package test.nia.chapter9.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class BufUtils
{
    /***
     *  生成指定大小的字节缓冲
     * */
    public static ByteBuf lengthedByteBuf(int len)
    {
        ByteBuf buf = Unpooled.buffer();
        for (int i = 0; i < len; i++)
        {
            buf.writeInt(i);
        }
        return buf;
    }
}
