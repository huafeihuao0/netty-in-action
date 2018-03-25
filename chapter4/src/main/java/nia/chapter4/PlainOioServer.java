package nia.chapter4;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

/***
 *  【jdk中的阻塞IO】
 * */
public class PlainOioServer
{
    public void serve(int port) throws IOException
    {
        //服务端套接字监听指定端口
        final ServerSocket serverSocket = new ServerSocket(port);

        try
        {
            for (; ; )  //循环阻塞
            {
                //阻塞式接受每一个客户端套接字的链接
                final Socket clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from " + clientSocket);

                //启动客户端套接字操作线程
                startChilSocketProcessorTh(clientSocket);
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /***
     *  启动客户端套接字操作线程
     * */
    private void startChilSocketProcessorTh(final Socket clientSocket)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                OutputStream out;
                try
                {
                    //向客户端写数据
                    out = clientSocket.getOutputStream();
                    out.write("Hi!\r\n".getBytes(Charset.forName("UTF-8")));
                    out.flush();
                    clientSocket.close();
                } catch (IOException e)
                {
                    e.printStackTrace();
                } finally
                {
                    try
                    {
                        clientSocket.close();
                    } catch (IOException ex)
                    {
                        // ignore on close
                    }
                }
            }
        }).start();
    }
}
