package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/***
 *  【SSL数据加解密处理器】
 * */
public class SslChannelInitializer
        extends ChannelInitializer<Channel>
{
    private final SslContext sslContext;
    private final boolean startTls;

    public SslChannelInitializer(SslContext sslContext, boolean startTls)
    {
        this.sslContext = sslContext;
        this.startTls = startTls;
    }

    @Override
    protected void initChannel(Channel channel) throws Exception
    {
        //实例化ssl加解密引擎
        SSLEngine sslEngine = sslContext.newEngine(channel.alloc());
        channel.pipeline()
               .addFirst("ssl", new SslHandler(sslEngine, startTls));//添加加解密处理器
    }
}
