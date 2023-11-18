package com.algo.ref.ex.netty.client;

import com.algo.ref.ex.netty.server.modal.RequestData;
import com.algo.ref.ex.netty.server.modal.ResponseData;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        super.channelActive(ctx);
        RequestData msg=new RequestData();
        msg.setIntValue(123);
        msg.setStringValue("all work and no play make jack a dull boy");
        ChannelFuture future=ctx.writeAndFlush(msg);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//        super.channelRead(ctx, msg);
        System.out.println((ResponseData )msg);
        ctx.close();
    }
}
