package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;
import nia.chapter11.utils.DefSimpleInboundHandler;

import java.io.Serializable;

/***
 *  【Jboss序列化编解码器】
 * */
public class MarshallingInitializer
        extends ChannelInitializer<Channel>
{
    private final MarshallerProvider marshallerProvider;
    private final UnmarshallerProvider unmarshallerProvider;

    public MarshallingInitializer(
            UnmarshallerProvider unmarshallerProvider,
            MarshallerProvider marshallerProvider)
    {
        this.marshallerProvider = marshallerProvider;
        this.unmarshallerProvider = unmarshallerProvider;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception
    {
        channel.pipeline()
               //添加序列化解码器
               .addLast(new MarshallingDecoder(unmarshallerProvider))
               //添加序列化编码器
               .addLast(new MarshallingEncoder(marshallerProvider))
               .addLast(new DefSimpleInboundHandler<Serializable>());
    }

}
