package nia.chapter11;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.LineBasedFrameDecoder;
import nia.chapter11.decoder.CmdDecoder;
import nia.chapter11.model.Cmd;
import nia.chapter11.utils.DefSimpleInboundHandler;

/***
 *  【命令式数据处理器】
 * */
public class CmdHandlerInitializer
        extends ChannelInitializer<Channel>
{

    @Override
    protected void initChannel(Channel channel) throws Exception
    {
        channel.pipeline()
               .addLast(new CmdDecoder(64 * 1024))
               .addLast(new DefSimpleInboundHandler<Cmd>());
    }



}
