package com.cloudTop.starshare.networkapi.socketapi.SocketReqeust;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by yaowang on 2017/2/19.
 */

public class SocketAPIEncoder extends MessageToByteEncoder<SocketDataPacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, SocketDataPacket msg, ByteBuf out) throws Exception {
        ByteBufOutputStream bout = new ByteBufOutputStream(out);
        msg.writeSerializable(bout);
    }
}
