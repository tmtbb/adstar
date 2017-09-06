package com.cloudTop.starshare.networkapi.socketapi.SocketReqeust;


import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.PointerByReference;

import java.io.IOException;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by yaowang on 2017/2/19.
 * Change by YaoLei on 2017-8-31 17:31:36
 * 数据包内容加密对象
 */

public class SocketAPIEncoder extends MessageToByteEncoder<SocketDataPacket> {
    @Override
    protected void encode(ChannelHandlerContext ctx, SocketDataPacket msg, ByteBuf out) throws Exception {
        if (msg.getIsZipEncrypt() != 0) {
            //获取SocketDataPacket的具体数据包信息
            ByteBuf buffer_encode = Unpooled.buffer(msg.getPacketLength());
            ByteBufOutputStream bout = new ByteBufOutputStream(buffer_encode);
            msg.writeSerializable(bout);

            try {
                //对数据包信息进行加密处理，然后发送给服务端
                byte[] bytes_encryption = socketDataPacketPacketStream(buffer_encode.array());
                //如果数据加密失败，则明文发送，则加密发送，
                if(bytes_encryption == null){
                    msg.writeSerializable(bout);
                }else{
                    out.writeBytes(bytes_encryption);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            //不做加密处理，直接发送给服务端
            ByteBufOutputStream bout = new ByteBufOutputStream(out);
            msg.writeSerializable(bout);

//            //获取SocketDataPacket的具体数据包信息
//            ByteBuf buffer_encode = Unpooled.buffer(msg.getPacketLength());
//            ByteBufOutputStream bout = new ByteBufOutputStream(buffer_encode);
//            msg.writeSerializable(bout);
//
//            try {
//                //对数据包信息进行加密处理，然后解密处理，然后发送给服务端
//                byte[] bytes_encryption = socketDataPacketPacketStream(buffer_encode.array());
//                byte[] bytes_decode = socketDataUnpackStream(bytes_encryption);
//                out.writeBytes(bytes_decode);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

        }

    }


    /**
     * Native的加密方法
     * @param bytes 字节数组对象
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public byte[] socketDataPacketPacketStream(byte[] bytes) throws IOException, ClassNotFoundException {
        int length = bytes.length;
        Memory pointer = new Memory(length);
        pointer.write(0, bytes, 0, length);
        PointerByReference out_stream = new PointerByReference(Pointer.NULL);
        ;
        IntByReference out_stream_length = new IntByReference(0);
//        System.out.println("当前操作码："+operateCode);
        int flag = Clibrary.INSTANTCE.PacketStream(pointer, length, out_stream, out_stream_length);
        ;
        if (flag == 0) {
            return null;
        }
        int length_result = out_stream_length.getValue();
        byte[] bytes_result = out_stream.getValue().getByteArray(0, length_result);
        return bytes_result;
    }

}
