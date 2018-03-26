package nia.chapter6;

import io.netty.channel.*;

/***
 *  【出站异常处理】
 * */
public class OutboundExceptionHandler
        extends ChannelOutboundHandlerAdapter
{
    @Override
    public void write(ChannelHandlerContext handlerContext, Object msg, ChannelPromise promise)
    {
        //向承诺中添加监听器
        promise.addListener(new ChannelFutureListener()
        {
            @Override
            public void operationComplete(ChannelFuture future)
            {
                if (!future.isSuccess())    //失败
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
