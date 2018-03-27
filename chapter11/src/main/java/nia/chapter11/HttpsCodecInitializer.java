package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/***
 *  【https编解码器】
 * */
public class HttpsCodecInitializer
        extends ChannelInitializer<Channel>
{
    private final SslContext context;
    private final boolean isClient;

    public HttpsCodecInitializer(SslContext context, boolean isClient)
    {
        this.context = context;
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception
    {
        //SSL加密引擎
        SSLEngine sslEngine = context.newEngine(channel.alloc());

        ChannelPipeline pipeline = channel.pipeline();

        pipeline.addFirst("ssl", new SslHandler(sslEngine));//添加ssl加密处理器

        if (isClient)
        {
            pipeline.addLast("codec", new HttpClientCodec());   //http客户端编解码器
        } else
        {
            pipeline.addLast("codec", new HttpServerCodec());//http服务端编解码器
        }
    }
}
