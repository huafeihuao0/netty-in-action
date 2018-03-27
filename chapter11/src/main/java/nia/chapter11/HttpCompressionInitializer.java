package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;


/***
 *  【Http消息压缩】
 * */
public class HttpCompressionInitializer
        extends ChannelInitializer<Channel>
{
    private final boolean isClient;

    public HttpCompressionInitializer(boolean isClient)
    {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception
    {
        ChannelPipeline pipeline = ch.pipeline();
        if (isClient)
        {
            pipeline.addLast("codec", new HttpClientCodec());
            pipeline.addLast("decompressor", new HttpContentDecompressor());    //客户端使用解压缩
        } else
        {
            pipeline.addLast("codec", new HttpServerCodec());
            pipeline.addLast("compressor", new HttpContentCompressor());//服务端要锁
        }
    }
}
