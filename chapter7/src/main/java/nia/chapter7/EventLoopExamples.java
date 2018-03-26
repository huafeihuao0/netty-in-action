package nia.chapter7;

import java.util.Collections;
import java.util.List;

/***
 *  使用Netty的事件循环调度队列任务
 * */
public class EventLoopExamples
{
    /**
     * Listing 7.1 Executing tasks in an event loop
     */
    public static void executeTaskInEventLoop()
    {
        boolean terminated = true;
        //...
        while (!terminated)
        {
            List<Runnable> readyEvents = blockUntilEventsReady();
            for (Runnable ev : readyEvents)
            {
                ev.run();
            }
        }
    }

    private static final List<Runnable> blockUntilEventsReady()
    {
        return Collections.<Runnable>singletonList(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    Thread.sleep(1000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
