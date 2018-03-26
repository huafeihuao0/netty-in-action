package nia.chapter6;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelPipeline;

import static io.netty.channel.DummyChannelPipeline.DUMMY_INSTANCE;

/***
 *  【修改处理器管道】
 * */
public class ModifyChannelPipeline
{
    private static final ChannelPipeline CHANNEL_PIPELINE_FROM_SOMEWHERE = DUMMY_INSTANCE;

    /**
     * Listing 6.5 Modify the ChannelPipeline
     */
    public static void modifyPipeline()
    {
        ChannelPipeline pipeline = CHANNEL_PIPELINE_FROM_SOMEWHERE; // get reference to pipeline;
        FirstHandler firstHandler = new FirstHandler();

        //添加处理器到不同位置
        pipeline.addLast("handler1", firstHandler);
        pipeline.addFirst("handler2", new SecondHandler());
        pipeline.addLast("handler3", new ThirdHandler());

        //移除处理器
        pipeline.remove("handler3");
        pipeline.remove(firstHandler);

        //替换处理器
        pipeline.replace("handler2", "handler4", new FourthHandler());
    }

    private static final class FirstHandler
            extends ChannelHandlerAdapter
    {

    }

    private static final class SecondHandler
            extends ChannelHandlerAdapter
    {

    }

    private static final class ThirdHandler
            extends ChannelHandlerAdapter
    {

    }

    private static final class FourthHandler
            extends ChannelHandlerAdapter
    {

    }
}
