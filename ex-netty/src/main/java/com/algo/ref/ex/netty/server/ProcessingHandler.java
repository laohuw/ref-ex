package com.algo.ref.ex.netty.server;

import com.algo.ref.ex.netty.server.modal.RequestData;
import com.algo.ref.ex.netty.server.modal.ResponseData;
import io.netty.channel.*;

public class ProcessingHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        RequestData requestData=(RequestData) msg;
        ResponseData responseData=new ResponseData();
        responseData.setIntValue(requestData.getIntValue()*2);
        ChannelFuture future=ctx.writeAndFlush(responseData);
        future.addListener(ChannelFutureListener.CLOSE);
        System.out.println(requestData);
    }
}
