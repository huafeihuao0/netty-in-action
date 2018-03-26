package nia.chapter7;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/***
 *  【任务调度】
 * */
public class ScheduleExamples
{
    private static final Channel CHANNEL_FROM_SOMEWHERE = new NioSocketChannel();

    /***
     *  JDK方式
     * */
    public static void schedule()
    {
        //实例化一个调度执行器服务
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
        Runnable taskRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("Now it is 60 seconds later");
            }
        };
        //调度执行
        ScheduledFuture<?> future = executor.schedule(taskRunnable, 60, TimeUnit.SECONDS);
        //关闭执行器
        executor.shutdown();
    }

    /***
     *  使用netty的事件循环执行调度任务
     * */
    public static void scheduleViaEventLoop()
    {
        Channel channel = CHANNEL_FROM_SOMEWHERE; // get reference from somewhere
        Runnable taskRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("60 seconds later");
            }
        };

        //使用事件循环启用调度任务
        ScheduledFuture<?> future = channel.eventLoop()
                                           .schedule(taskRunnable, 60, TimeUnit.SECONDS);
    }

    /***
     *  使用Netty的事件循环执行固定频率的调度任务
     * */
    public static void scheduleFixedViaEventLoop()
    {
        Channel ch = CHANNEL_FROM_SOMEWHERE; // get reference from somewhere
        Runnable taskRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("Run every 60 seconds");
            }
        };

        ScheduledFuture<?> future = ch.eventLoop()
                                      .scheduleAtFixedRate(taskRunnable, 60, 60, TimeUnit.SECONDS);
    }

    /***
     *  使用任务的未来来取消任务调度
     * */
    public static void cancelingTaskUsingScheduledFuture()
    {
        Channel ch = CHANNEL_FROM_SOMEWHERE; // get reference from somewhere
        Runnable taskRunnabl = new Runnable()
        {
            @Override
            public void run()
            {
                System.out.println("Run every 60 seconds");
            }
        };
        ScheduledFuture<?> future = ch.eventLoop()
                                      .scheduleAtFixedRate(taskRunnabl, 60, 60, TimeUnit.SECONDS);
        boolean mayInterruptIfRunning = false;
        //毒枭任务调度
        future.cancel(mayInterruptIfRunning);
    }
}
