package nia.chapter11.utils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class DefSimpleInboundHandler<T> extends SimpleChannelInboundHandler<T>
{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, T msg) throws Exception
    {

    }
}
