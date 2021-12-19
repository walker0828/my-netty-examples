package com.example.discard;

import com.example.config.NioDemoConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


public class NioDiscardServer {
    public static void startServer() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(NioDemoConfig.SOCKET_SERVER_PORT));

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(selector.select() > 0) {
            Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();

            while(selectionKeys.hasNext()) {
                SelectionKey selectionKey = selectionKeys.next();

                if(selectionKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ);
                }else if (selectionKey.isReadable()) {
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int length = 0;
                    while((length = socketChannel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();

                        byteBuffer.clear();
                    }
                }
                selectionKeys.remove();
            }

            serverSocketChannel.close();
        }
    }

    public static void main(String[] args) throws IOException {
        startServer();
    }
}
