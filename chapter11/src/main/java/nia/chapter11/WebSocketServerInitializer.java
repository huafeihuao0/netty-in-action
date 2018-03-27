package nia.chapter11;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import nia.chapter11.utils.DefSimpleInboundHandler;

/***
 *  【websocket编解码器】
 * */
public class WebSocketServerInitializer
        extends ChannelInitializer<Channel>
{
    @Override
    protected void initChannel(Channel ch) throws Exception
    {
        ch.pipeline()
          .addLast(
                  new HttpServerCodec(),    //http服务端编解码器
                  new HttpObjectAggregator(65536),//http消息聚合器
                  new WebSocketServerProtocolHandler("/websocket"),//对指定路由进行升级
                  new DefSimpleInboundHandler<TextWebSocketFrame>(),//文本帧处理器
                  new DefSimpleInboundHandler<BinaryWebSocketFrame>(),//二进制帧处理器
                  new DefSimpleInboundHandler<ContinuationWebSocketFrame>());//持续帧处理器
    }
}
