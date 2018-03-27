package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.io.File;
import java.io.FileInputStream;

/***
 *  【大文件块处理器】
 * */
public class ChunkedWriteHandlerInitializer
        extends ChannelInitializer<Channel>
{
    private final File file;
    private final SslContext sslCtx;

    public ChunkedWriteHandlerInitializer(File file, SslContext sslCtx)
    {
        this.file = file;
        this.sslCtx = sslCtx;
    }

    /***
     *  初始化通道
     * */
    @Override
    protected void initChannel(Channel newChannel) throws Exception
    {
        newChannel.pipeline()//获取处理器管道
                  .addLast(new SslHandler(sslCtx.newEngine(newChannel.alloc())))//设置ssl加密
                  .addLast(new ChunkedWriteHandler())    //【大文件写入处理器】
                  .addLast(new WriteStreamHandler()); //写流
    }

    public final class WriteStreamHandler
            extends ChannelInboundHandlerAdapter
    {

        @Override
        public void channelActive(ChannelHandlerContext handlerContext) throws Exception
        {
            super.channelActive(handlerContext);
            //使用块化的流写入到远程
            handlerContext.writeAndFlush(new ChunkedStream(new FileInputStream(file)));
        }
    }
}
