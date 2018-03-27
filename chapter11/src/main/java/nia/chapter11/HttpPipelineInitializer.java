package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/***
 *  【http协议编解码器】
 * */
public class HttpPipelineInitializer
        extends ChannelInitializer<Channel>
{
    private final boolean client;

    public HttpPipelineInitializer(boolean client)
    {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        if (client)
        {
            pipeline.addLast("decoder", new HttpResponseDecoder()); //响应解码器
            pipeline.addLast("encoder", new HttpRequestEncoder());  //请求编码器
        } else
        {
            pipeline.addLast("decoder", new HttpRequestDecoder());  //请求解码器
            pipeline.addLast("encoder", new HttpResponseEncoder()); //响应编码器
        }
    }
}
