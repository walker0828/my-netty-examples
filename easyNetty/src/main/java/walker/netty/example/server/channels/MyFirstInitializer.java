package walker.netty.example.server.channels;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import walker.netty.example.server.handlers.MyFirstHandler;

public class MyFirstInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel){
        System.out.println("链接报告IP:" + channel.localAddress().getHostString());
        System.out.println("链接报告Port:" + channel.localAddress().getPort());
        channel.pipeline().addLast(new MyFirstHandler());
    }
}
