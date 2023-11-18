package com.algo.ref.ex.netty.client;

import com.algo.ref.ex.netty.server.modal.RequestData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;

public class RequestDataEncoder extends MessageToByteEncoder<RequestData>{
    private final Charset charset= Charset.forName("UTF-8");
    @Override
    protected void encode(ChannelHandlerContext ctx, RequestData msg, ByteBuf out) throws Exception {
        out.writeInt(msg.getIntValue());
        out.writeInt(msg.getStringValue().length());
        out.writeCharSequence(msg.getStringValue(), charset);
    }
    // implements EventExecutorGroup, ChannelHandler, EventExecutorGroup
}
