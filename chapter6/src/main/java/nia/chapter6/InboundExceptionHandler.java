package nia.chapter6;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/***
 *  【入栈异常处理】
 * */
public class InboundExceptionHandler
        extends ChannelInboundHandlerAdapter
{
    @Override
    public void exceptionCaught(ChannelHandlerContext handlerContext, Throwable cause)
    {
        cause.printStackTrace();//打印异常栈
        handlerContext.close();//关闭处理器上下文
    }
}
