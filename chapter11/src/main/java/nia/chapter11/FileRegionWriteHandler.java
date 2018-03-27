package nia.chapter11;

import io.netty.channel.*;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.File;
import java.io.FileInputStream;


/***
 *  【文件域入站理器】
 * */
public class FileRegionWriteHandler
        extends ChannelInboundHandlerAdapter
{
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static final File FILE_FROM_SOMEWHERE = new File("");

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception
    {
        File file = FILE_FROM_SOMEWHERE; //get reference from somewhere
        Channel channel = CHANNEL_FROM_SOMEWHERE; //get reference from somewhere

        FileRegion fileRegion = new DefaultFileRegion(new FileInputStream(file).getChannel(),//文件读取通道
                0, file.length());

        //将文件域写入到远端
        channel.writeAndFlush(fileRegion);
    }
}
