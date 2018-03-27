package nia.chapter11;

import com.google.protobuf.MessageLite;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import nia.chapter11.utils.DefSimpleInboundHandler;

/***
 *  【支持google数据格式protobuf的编解码器】
 * */
public class ProtoBufInitializer
        extends ChannelInitializer<Channel>
{
    private final MessageLite messageLite;

    public ProtoBufInitializer(MessageLite messageLite)
    {
        this.messageLite = messageLite;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception
    {
        channel.pipeline()
               .addLast(new ProtobufVarint32FrameDecoder()) //添加protobuf帧解码器
               .addLast(new ProtobufEncoder())//添加protobuf编码器
               .addLast(new ProtobufDecoder(messageLite))//添加protobuf帧解码器
               .addLast(new DefSimpleInboundHandler<Object>());
    }

}
