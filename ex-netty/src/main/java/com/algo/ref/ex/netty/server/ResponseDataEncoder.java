package com.algo.ref.ex.netty.server;

import com.algo.ref.ex.netty.server.modal.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ResponseDataEncoder extends MessageToByteEncoder<ResponseData> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseData msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getIntValue());
    }
}
