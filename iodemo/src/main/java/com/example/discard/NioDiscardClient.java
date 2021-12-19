package com.example.discard;

import com.example.config.NioDemoConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioDiscardClient {
    private static void startClient() throws IOException {
        InetSocketAddress address = new InetSocketAddress(NioDemoConfig.SOCKET_SERVER_PORT);
        SocketChannel socketChannel = SocketChannel.open(address);
        socketChannel.configureBlocking(false);
        while(!socketChannel.finishConnect()) {
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("hello world".getBytes());
        byteBuffer.flip();

        socketChannel.write(byteBuffer);
        socketChannel.shutdownOutput();
        socketChannel.close();
    }


    public static void main(String[] args) throws IOException {
        startClient();
    }
}
