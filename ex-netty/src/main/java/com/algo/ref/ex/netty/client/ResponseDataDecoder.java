package com.algo.ref.ex.netty.client;

import com.algo.ref.ex.netty.server.modal.ResponseData;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

public class ResponseDataDecoder extends ReplayingDecoder<ResponseData> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ResponseData data=new ResponseData();
        int intValue = in.readInt();
        data.setIntValue(intValue);
        out.add(data);
    }
}
