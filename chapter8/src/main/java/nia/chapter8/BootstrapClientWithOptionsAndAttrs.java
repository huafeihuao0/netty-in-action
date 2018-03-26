package nia.chapter8;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/***
 *  【设置通道的选项和属性】
 * */
public class BootstrapClientWithOptionsAndAttrs
{
    public void bootstrap()
    {
        //实例化id属性键对象
        final AttributeKey<Integer> idAttrKey = AttributeKey.newInstance("ID");

        Bootstrap cliBootstrap = new Bootstrap();
        cliBootstrap.group(new NioEventLoopGroup())
                 .channel(NioSocketChannel.class)
                 .handler(new MyChannelHandler(idAttrKey));
        //设置选项
        cliBootstrap.option(ChannelOption.SO_KEEPALIVE, true)   //保持长连接
                 .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);   //设置链接超时时间
        //设置属性
        cliBootstrap.attr(idAttrKey, 123456);
        InetSocketAddress remoteAddr = new InetSocketAddress("www.manning.com", 80);
        ChannelFuture future = cliBootstrap.connect(remoteAddr);
        //同步，且不容许打断
        future.syncUninterruptibly();
    }

    /***
     *  【自定义的处理器】
     * */
    public class MyChannelHandler extends SimpleChannelInboundHandler<ByteBuf>
    {
        private AttributeKey<Integer> idAttrKey;

        public MyChannelHandler(AttributeKey<Integer> idAttrKey)
        {
            this.idAttrKey = idAttrKey;
        }

        /***
         *  通道注册时候回调
         * */
        @Override
        public void channelRegistered(ChannelHandlerContext handlerContext)
                             throws Exception
        {
            //获取通道的属性值
            Integer idValue = handlerContext.channel()
                                 .attr(idAttrKey)
                                 .get();
        }

        /***
         *  通道可读的时候回调
         * */
        @Override
        protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception
        {
            System.out.println("Received data");
        }
    }
}
