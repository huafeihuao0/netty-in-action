package nia.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/***
 *  【空闲连接处理器】
 * */
public class IdleStateHandlerInitializer
        extends ChannelInitializer<Channel>
{
    @Override
    protected void initChannel(Channel channel) throws Exception
    {
        channel.pipeline()
               .addLast(new IdleStateHandler(0, 0, 60, TimeUnit.SECONDS))//空闲连接处理器
               .addLast(new HeartbeatHandler());    //心跳处理器
    }

    /***
     *  【心跳处理器】
     * */
    public static final class HeartbeatHandler
            extends ChannelInboundHandlerAdapter
    {
        //心跳序列
        private static final ByteBuf HEARTBEAT_SEQUENCE =
                Unpooled.unreleasableBuffer(Unpooled.copiedBuffer(
                        "HEARTBEAT", CharsetUtil.ISO_8859_1));

        /***
         *  触发某种事件的时候回调
         * */
        @Override
        public void userEventTriggered(ChannelHandlerContext handlerContext,
                                       Object ev) throws Exception
        {
            if (ev instanceof IdleStateEvent)   //空闲状态事件
            {
                //发送测试心跳
                handlerContext.writeAndFlush(HEARTBEAT_SEQUENCE.duplicate())
                              .addListener(ChannelFutureListener.CLOSE_ON_FAILURE);//若心跳失败，直接关闭连接
            } else
            {
                super.userEventTriggered(handlerContext, ev);
            }
        }
    }
}
