package nia.chapter4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/***
 *  【JDK实现的非阻塞IO】
 * */
public class PlainNioServer
{
    public void serve(int port) throws IOException
    {
        //打开服务端套接字通道
        ServerSocketChannel serverSocketCh = ServerSocketChannel.open();
        serverSocketCh.configureBlocking(false);    //设置非阻塞
        //绑定端口和地址
        bindIPAndPort(port, serverSocketCh);
        //开启选择器
        Selector selector = Selector.open();
        //将服务套接字通道注册到选择器中，并监听链接事件
        serverSocketCh.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("Hi!\r\n".getBytes());
        for (; ; )
        {
            try
            {
                //IO复用
                selector.select();
            } catch (IOException ex)
            {
                ex.printStackTrace();
                //handle exception
                break;
            }
            //监听到的事件集
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext())
            {
                SelectionKey key = iterator.next();
                iterator.remove();
                try
                {
                    //处理客户端链接事件
                    handleAcceptEv(selector, msg, key);
                    //可写事件
                    handleWriteEv(key);
                } catch (IOException ex)
                {
                    key.cancel();
                    try
                    {
                        key.channel()
                           .close();
                    } catch (IOException cex)
                    {
                        // ignore on close
                    }
                }
            }
        }
    }

    /***
     *  处理客户端链接事件
     * */
    private void handleAcceptEv(Selector selector, ByteBuffer msg, SelectionKey key) throws IOException
    {
        if (key.isAcceptable()) //链接事件
        {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            SocketChannel clientSocketChannel = serverSocketChannel.accept();   //开始接受链接
            clientSocketChannel.configureBlocking(false);
            //注册读写事件
            clientSocketChannel.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ, msg.duplicate());
            System.out.println("Accepted connection from " + clientSocketChannel);
        }
    }

    /***
     *  处理写事件
     * */
    private void handleWriteEv(SelectionKey key) throws IOException
    {
        if (key.isWritable())
        {
            //客户端可写
            SocketChannel clientSocketChannel = (SocketChannel) key.channel();
            //获取写缓存
            ByteBuffer buffer = (ByteBuffer) key.attachment();
            //循环写
            while (buffer.hasRemaining())
            {
                if (clientSocketChannel.write(buffer) == 0)
                {
                    break;
                }
            }
            clientSocketChannel.close();
        }
    }

    /***
     *  绑定端口和地址
     * */
    private void bindIPAndPort(int port, ServerSocketChannel serverSocketCh) throws IOException
    {
        ServerSocket serverSocket = serverSocketCh.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        serverSocket.bind(address); //绑定地址和端口
    }
}

