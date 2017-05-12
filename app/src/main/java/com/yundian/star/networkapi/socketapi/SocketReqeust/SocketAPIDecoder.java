package com.yundian.star.networkapi.socketapi.SocketReqeust;

import java.nio.ByteOrder;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

/**
 * Created by yaowang on 2017/2/19.
 */

public class SocketAPIDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        Object decoded = decode(ctx, in);
        if (decoded != null) {
            out.add(decoded);
        }
    }


    protected int getFrameLength(ByteBuf buf, int offset) {
        buf = buf.order(ByteOrder.LITTLE_ENDIAN);
        int frameLength =  buf.getUnsignedShort(offset);
        return frameLength;
    }

    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.readableBytes() < 2) {
            return null;
        }
        int readerIndex = in.readerIndex() ;
        int frameLength = getFrameLength(in, readerIndex);
        if (frameLength < 0) {
            in.skipBytes(2);
            throw new CorruptedFrameException(
                    "negative pre-adjustment length field: " + frameLength);
        }

        if (in.readableBytes() < frameLength) {
            return null;
        }
        readerIndex = in.readerIndex();

        ByteBuf byteBuf = in.order(ByteOrder.LITTLE_ENDIAN);
        SocketDataPacket socketDataPacket = null;
        if( frameLength - 26 == byteBuf.getUnsignedShort(readerIndex + 8) )
        {
            socketDataPacket = new SocketDataPacket();
            ByteBufInputStream inputStream = new ByteBufInputStream(byteBuf);
            socketDataPacket.readSerializable(inputStream);
        }
        in.readerIndex(readerIndex + frameLength);

        return socketDataPacket;
    }


    protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
        ByteBuf frame = ctx.alloc().buffer(length);
        frame.writeBytes(buffer, index, length);
        return frame;
    }
}
