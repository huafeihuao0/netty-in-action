package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/***
 *  【Http消息聚合】
 * */
public class HttpAggregatorInitializer
        extends ChannelInitializer<Channel>
{
    private final boolean isClient;

    public HttpAggregatorInitializer(boolean isClient)
    {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        if (isClient)
        {
            pipeline.addLast("codec", new HttpClientCodec());   //http客户端编解码器
        } else
        {
            pipeline.addLast("codec", new HttpServerCodec());   //http服务端编解码器
        }

        //【使用http消息聚合器】
        pipeline.addLast("aggregator", new HttpObjectAggregator(512 * 1024));
    }
}
