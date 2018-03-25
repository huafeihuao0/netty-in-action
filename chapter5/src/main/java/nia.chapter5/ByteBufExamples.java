package nia.chapter5;

import io.netty.buffer.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ByteProcessor;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.Random;

import static io.netty.channel.DummyChannelHandlerContext.DUMMY_INSTANCE;

/***
 *  【BB示例】
 * */
public class ByteBufExamples
{
    private final static Random random = new Random();
    private static final ByteBuf BYTE_BUF_FROM_SOMEWHERE = Unpooled.buffer(1024);
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static final ChannelHandlerContext CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE = DUMMY_INSTANCE;

    /**
     * 使用堆内支持数组
     */
    public static void heapBuffer()
    {
        ByteBuf heapBuf = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        if (heapBuf.hasArray()) //是否是包含支持数组
        {
            byte[] arrOnHeap = heapBuf.array(); //获取支持数组
            int offset = heapBuf.arrayOffset() + heapBuf.readerIndex();
            int readableLen = heapBuf.readableBytes();   //获取可读字节长度
            handleArray(arrOnHeap, offset, readableLen);
        }
    }

    /***
     *  直接内存
     * */
    public static void directBuffer()
    {
        ByteBuf directBuf = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        if (!directBuf.hasArray())  //不包含支持数组，表示使用直接内存
        {
            int readableLen = directBuf.readableBytes();
            byte[] array = new byte[readableLen];
            directBuf.getBytes(directBuf.readerIndex(), array); //复制
            handleArray(array, 0, readableLen);
        }
    }

    /***
     *  使用ByteBuffer实现复合缓冲区
     * */
    public static void byteBufferComposite(ByteBuffer headerBuffer, ByteBuffer bodyBuffer)
    {
        // Use an array to hold the message parts
        ByteBuffer[] combinedBuffers = new ByteBuffer[]{headerBuffer, bodyBuffer};

        // Create a new ByteBuffer and use copy to merge the header and body
        ByteBuffer messageBuffer = ByteBuffer.allocate(headerBuffer.remaining() + bodyBuffer.remaining());
        messageBuffer.put(headerBuffer);
        messageBuffer.put(bodyBuffer);
        messageBuffer.flip();
    }

    private static void handleArray(byte[] array, int offset, int len) {}

    /**
     *使用复合缓冲区组合
     */
    public static void byteBufComposite()
    {
        CompositeByteBuf compMsgByteBuf = Unpooled.compositeBuffer();
        ByteBuf headerBuf = BYTE_BUF_FROM_SOMEWHERE; // can be backing or direct
        ByteBuf bodyBuf = BYTE_BUF_FROM_SOMEWHERE;   // can be backing or direct
        compMsgByteBuf.addComponents(headerBuf, bodyBuf);   //添加要管理的子缓冲
        compMsgByteBuf.removeComponent(0); //移除首个缓冲
        for (ByteBuf buf : compMsgByteBuf)
        {
            System.out.println(buf.toString());
        }
    }

    /***
     *  读取复合缓冲区数组内容
     * */
    public static void byteBufCompositeArray()
    {
        CompositeByteBuf compBuf = Unpooled.compositeBuffer();

        int readableLen = compBuf.readableBytes();
        byte[] destArr = new byte[readableLen];
        compBuf.getBytes(compBuf.readerIndex(), destArr);//浅拷贝

        handleArray(destArr, 0, destArr.length);
    }

    /***
     *  所有获取指定位置内容
     * */
    public static void byteBufRelativeAccess()
    {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        for (int i = 0; i < buffer.capacity(); i++)
        {
            byte b = buffer.getByte(i); //获取i处的字节
            System.out.println((char) b);
        }
    }

    /***
     *  便利所有数据
     * */
    public static void readAllData()
    {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        while (buffer.isReadable()) //是否可读
        {
            System.out.println(buffer.readByte());  //读一个字节
        }
    }

    /***
     *  写数据
     * */
    public static void write()
    {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        while (buffer.writableBytes() >= 4) //可写长度>4
        {
            buffer.writeInt(random.nextInt());
        }
    }

    /***
     *  字节处理器
     * */
    public static void byteProcessor()
    {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        int index = buffer.forEachByte(ByteProcessor.FIND_CR);  //寻找 \r
    }


    public static void byteBufProcessor()
    {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        int index = buffer.forEachByte(ByteBufProcessor.FIND_CR);
    }

    /***
     *  缓冲切片，浅拷贝
     * */
    public static void byteBufSlice()
    {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf sliced = buf.slice(0, 15);  //浅拷贝
        System.out.println(sliced.toString(utf8));
        buf.setByte(0, (byte) 'J');
        assert buf.getByte(0) == sliced.getByte(0);
    }

    /***
     *  深拷贝
     * */
    public static void byteBufCopy()
    {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        ByteBuf copy = buf.copy(0, 15); //神拷贝
        System.out.println(copy.toString(utf8));
        buf.setByte(0, (byte) 'J');
        assert buf.getByte(0) != copy.getByte(0);
    }

    /**
     * get/set
     */
    public static void byteBufSetGet()
    {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf oriBuf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);

        System.out.println((char) oriBuf.getByte(0));

        //获取读写索引
        int readerIndex = oriBuf.readerIndex();
        int writerIndex = oriBuf.writerIndex();

        oriBuf.setByte(0, (byte) 'B');
        System.out.println((char) oriBuf.getByte(0));
        assert readerIndex == oriBuf.readerIndex();
        assert writerIndex == oriBuf.writerIndex();
    }

    /**
     * read/write
     */
    public static void byteBufWriteRead()
    {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8);
        System.out.println((char) buf.readByte());
        int readerIndex = buf.readerIndex();
        int writerIndex = buf.writerIndex();
        buf.writeByte((byte) '?');
        assert readerIndex == buf.readerIndex();
        assert writerIndex != buf.writerIndex();
    }



    /**
     *  获取缓冲分配器
     */
    public static void obtainingByteBufAllocatorReference()
    {
        Channel channel = CHANNEL_FROM_SOMEWHERE; //get reference form somewhere
        ByteBufAllocator allocator = channel.alloc();   //从通道中获取

        ChannelHandlerContext ctx = CHANNEL_HANDLER_CONTEXT_FROM_SOMEWHERE; //get reference form somewhere
        ByteBufAllocator allocator2 = ctx.alloc();  //从处理器上下文中获取
    }

    /**
     * 引用计数
     */
    public static void referenceCounting()
    {
        Channel channel = CHANNEL_FROM_SOMEWHERE; //get reference form somewhere
        ByteBufAllocator allocator = channel.alloc();
        ByteBuf buf = allocator.directBuffer();
        assert buf.refCnt() == 1;
    }

    /**
     *释放引用
     */
    public static void releaseReferenceCountedObject()
    {
        ByteBuf buffer = BYTE_BUF_FROM_SOMEWHERE; //get reference form somewhere
        boolean released = buffer.release();    //释放引用
        //...
    }


}
