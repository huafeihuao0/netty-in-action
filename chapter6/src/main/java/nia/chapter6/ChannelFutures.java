package nia.chapter6;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.socket.nio.NioSocketChannel;


/***
 *  【将未来监听器添加到通道未来】
 * */
public class ChannelFutures
{
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();
    private static final ByteBuf SOME_MSG_FROM_SOMEWHERE = Unpooled.buffer(1024);


    /***
     *  将未来监听器添加到通道未来
     * */
    public static void addingChannelFutureListener()
    {
        Channel channel = CHANNEL_FROM_SOMEWHERE; // get reference to pipeline;
        ByteBuf msgBuf = SOME_MSG_FROM_SOMEWHERE; // get reference to pipeline;

        //通道写入，返回通道未来
        ChannelFuture channelFuture = channel.write(msgBuf);
        channelFuture.addListener(new ChannelFutureListener()  //添加通道未来监听器
        {
            /***
             *  写操作完成回调
             * */
            @Override
            public void operationComplete(ChannelFuture future)
            {
                if (!future.isSuccess())//失败
                {
                    future.cause()
                          .printStackTrace();//打印异常栈

                    future.channel()
                          .close();//关闭通道
                }
            }
        });
    }
}
