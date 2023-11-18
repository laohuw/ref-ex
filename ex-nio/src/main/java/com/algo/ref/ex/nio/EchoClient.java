package com.algo.ref.ex.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;

public class EchoClient implements Runnable{
    public static void main(String[] args) throws IOException {
        EchoClient echoClient=new EchoClient();
        echoClient.run();
    }
    public void run() {
        ByteBuffer buffer=ByteBuffer.allocate(256);
        ByteBuffer outBuffer=ByteBuffer.allocate(256);
        int n=0;
        try {
            Selector selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("localhost",8080));
            int ops =  SelectionKey.OP_CONNECT | SelectionKey.OP_READ | SelectionKey.OP_WRITE;
            socketChannel.register(selector, ops);

            while(true) {
                if(n==2) break;
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = (Iterator<SelectionKey>) selectionKeys.iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    SocketChannel channel = (SocketChannel)key.channel();
                    if (key.isReadable()) {
                        System.out.println("read data:");
                        int len=channel.read(buffer);
                        if(len >0) {
                            buffer.flip();
                            System.out.println(new String(buffer.array()));
                            buffer.clear();
                            outBuffer.put("Done".getBytes(StandardCharsets.UTF_8));
                            channel.write(outBuffer);
                            outBuffer.clear();
                        }
                    } else if (key.isAcceptable()) {
                        System.out.println("accepted connection");
                    } else if (key.isConnectable()) {
                        System.out.println("connectable");
                        try{
                            channel.finishConnect();
                            System.out.println("connected " + channel.getLocalAddress() + " <> " + channel.getRemoteAddress());
                        } catch (Exception e) {
                            e.printStackTrace();
                            channel.close();
                        }
                    } else if (key.isWritable()) {
                        if(n>0)
                            continue;
                        System.out.println("write data success\n");
                        outBuffer.put(("Message " + n++ + " for testing nio echo server\n").getBytes(StandardCharsets.UTF_8));
                        channel.write(outBuffer);
                        outBuffer.clear();
                    }
                    iter.remove();
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
