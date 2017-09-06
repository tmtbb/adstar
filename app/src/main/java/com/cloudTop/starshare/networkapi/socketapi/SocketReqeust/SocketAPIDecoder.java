package com.cloudTop.starshare.networkapi.socketapi.SocketReqeust;

import android.app.Application;

import com.cloudTop.starshare.app.AppApplication;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import java.nio.ByteOrder;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

/**
 * Created by yaowang on 2017/2/19.
 * Change by YaoLei on 2017-8-31 17:31:36
 * 数据包内容解密对象
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
        int frameLength = buf.getUnsignedShort(offset);
        return frameLength;
    }

    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {

        ByteBuf buffer_decode = in;
        //获取从服务器返回的字节流的第三位，如果这个标记为不为零，则需要加密，否则不需要加密
        int encyFlag = in.getByte(2);
        if (encyFlag != 0) {
            byte[] bytes_decode = socketDataUnpackStream(in.array());
            if (bytes_decode == null) {
                //解密失败
                buffer_decode = in;
                AppApplication.checkKey();
            } else {
                //解密成功
                buffer_decode = Unpooled.buffer(bytes_decode.length);
                buffer_decode.writeBytes(bytes_decode);
            }
        } else {
            buffer_decode = in;
        }

        int length = buffer_decode.readableBytes();

        if (length < 2) {
            return null;
        }
        int readerIndex = buffer_decode.readerIndex();
        int frameLength = getFrameLength(buffer_decode, readerIndex);
        if (frameLength < 0) {
            buffer_decode.skipBytes(2);
            throw new CorruptedFrameException(
                    "negative pre-adjustment length field: " + frameLength);
        }

        if (buffer_decode.readableBytes() < frameLength) {
            return null;
        }
        readerIndex = buffer_decode.readerIndex();

        ByteBuf byteBuf = buffer_decode.order(ByteOrder.LITTLE_ENDIAN);

        SocketDataPacket socketDataPacket = null;

        if (frameLength - 26 == byteBuf.getUnsignedShort(readerIndex + 8)) {
            socketDataPacket = new SocketDataPacket();
            ByteBufInputStream inputStream = new ByteBufInputStream(byteBuf);
            socketDataPacket.readSerializable(inputStream);
        }
        buffer_decode = null;
        in.readerIndex(readerIndex + frameLength);
        return socketDataPacket;
    }

    /**
     * Native的解密函数
     *
     * @param bytes 字节数组对象
     * @return
     */
    public byte[] socketDataUnpackStream(byte[] bytes) {

        int length = bytes.length;
        Memory pointer = new Memory(length);
        pointer.write(0, bytes, 0, length);

        PointerByReference out_stream = new PointerByReference(Pointer.NULL);
        ;
        IntByReference out_stream_length = new IntByReference(0);

        int flag = Clibrary.INSTANTCE.UnpackStream(pointer, length, out_stream, out_stream_length);
        ;
        if (flag == 0) {
            return null;
        }
        int length_result = out_stream_length.getValue();
        byte[] bytes_result = out_stream.getValue().getByteArray(0, length_result);
        return bytes_result;
    }

    protected ByteBuf extractFrame(ChannelHandlerContext ctx, ByteBuf buffer, int index, int length) {
        ByteBuf frame = ctx.alloc().buffer(length);
        frame.writeBytes(buffer, index, length);
        return frame;
    }
}
