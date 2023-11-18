package com.algo.ref.ex.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import java.util.Iterator;
import java.util.Set;

public class EchoServer implements Runnable{
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("Nio socket server starts...");
        ByteBuffer buffer = ByteBuffer.allocate(256);
        try {
            Selector selector = SelectorProvider.provider().openSelector();;//Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.socket().setReuseAddress(true);
            serverChannel.socket().bind(new InetSocketAddress("localhost",8080));
            SelectionKey acceptKey =serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            acceptKey.interestOps(SelectionKey.OP_ACCEPT);

            while (true) {
                selector.select();
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if(!key.isValid())
                        continue;
                    if (key.isAcceptable()) {
                        ServerSocketChannel acceptor= (ServerSocketChannel) key.channel();
                        SocketChannel clientChannel = acceptor.accept();
                        clientChannel.configureBlocking(false);
                        clientChannel.register(selector, SelectionKey.OP_READ| SelectionKey.OP_WRITE);
                    }
                    if (key.isReadable()) {
                        echoBack(buffer, key);
                    }
                    iter.remove();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void echoBack(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel clientChannel= (SocketChannel) key.channel();
        clientChannel.read(buffer);
        if(new String(buffer.array()).trim().equals("Done")){
            clientChannel.close();
            System.out.println("Client channel closed!");
        }else{
            buffer.flip();
            clientChannel.write(buffer);
            buffer.clear();
        }
    }

    public static void main(String[] args) throws IOException {
        EchoServer serverChannel=new EchoServer(8080);
        serverChannel.run();
    }
}
