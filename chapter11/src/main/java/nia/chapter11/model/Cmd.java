package nia.chapter11.model;

import io.netty.buffer.ByteBuf;

/***
 *  【命令消息格式】
 * */
public final class Cmd
{
    private final ByteBuf name;
    private final ByteBuf args;

    public Cmd(ByteBuf name, ByteBuf args)
    {
        this.name = name;
        this.args = args;
    }

    public ByteBuf name()
    {
        return name;
    }

    public ByteBuf args()
    {
        return args;
    }
}
