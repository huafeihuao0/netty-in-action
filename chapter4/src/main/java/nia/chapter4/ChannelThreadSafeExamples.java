package nia.chapter4;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/***
 *  【通道线程安全性示例】
 * */
public class ChannelThreadSafeExamples
{
    //通道单例
    private static final Channel CHANNEL_SINGLETON = new NioSocketChannel();

    /***
     *  单线程操作
     * */
    public static void writingToChannel()
    {
        Channel channel = CHANNEL_SINGLETON; // Get the channel reference from somewhere
        ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);

        ChannelFuture channelFuture = channel.writeAndFlush(buf);
        channelFuture.addListener(new MyCheckFutureListener());
    }

    /***
     *  多线程操作
     * */
    public static void writingToChannelFromManyThreads()
    {
        final Channel channel = CHANNEL_SINGLETON; // Get the channel reference from somewhere
        final ByteBuf buf = Unpooled.copiedBuffer("your data", CharsetUtil.UTF_8);
        //写入客户端通道的执行体
        Runnable writerRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                channel.write(buf.duplicate());
            }
        };

        //多次运行
        Executor executor = Executors.newCachedThreadPool();
        executor.execute(writerRunnable);
        executor.execute(writerRunnable);
    }

    /***
     *  自定义的检查回调未来事件是否成功的回监听器
     * */
    private static class MyCheckFutureListener
            implements ChannelFutureListener//通道事件监听器
    {
        /***
         * 操作完成回调
         * */
        @Override
        public void operationComplete(ChannelFuture future)
        {
            if (future.isSuccess()) //操作成功
            {
                System.out.println("Write successful");
            } else  //操作失败
            {
                System.err.println("Write error");
                future.cause()
                      .printStackTrace();
            }
        }
    }
}
