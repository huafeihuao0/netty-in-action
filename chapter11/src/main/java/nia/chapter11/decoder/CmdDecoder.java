package nia.chapter11.decoder;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;
import nia.chapter11.model.Cmd;

/***
 *  【基于行分隔符的命令消息帧解码器】
 * */
public  final class CmdDecoder
        extends LineBasedFrameDecoder   //继承基于行分隔符的帧解码器
{
    private static final byte SPACE = (byte) ' ';
    public CmdDecoder(int maxLength)
    {
        super(maxLength);
    }

    /***
     *  解码返回命令消息
     * */
    @Override
    protected Object decode(ChannelHandlerContext handlerContext, ByteBuf buf) throws Exception
    {
        //调用父类完成行分隔符的帧解码
        ByteBuf frame = (ByteBuf) super.decode(handlerContext, buf);
        if (frame == null)
        {
            return null;
        }

        //获取第一个空格符索引
        int indexOf1stSpace = frame.indexOf(frame.readerIndex(), frame.writerIndex(), SPACE);
        ByteBuf cmdName = frame.slice(frame.readerIndex(), indexOf1stSpace);
        ByteBuf cmdArgs=frame.slice(indexOf1stSpace + 1, frame.writerIndex());
        return new Cmd(cmdName, cmdArgs);
    }
}
