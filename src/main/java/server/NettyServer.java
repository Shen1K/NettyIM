package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {

    //设置服务器暴露的端口
    static final int PORT = 9000;
    public static void main(String[] args){

        //服务端启动器
        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        //实现reactor模型的主从线程组
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();

        //设置服务端参数
        serverBootstrap.group(boss, work)
                //设置channe的类型，Nio非阻塞io
                .channel(NioServerSocketChannel.class)
                //Option是针对服务端Channel的参数设置，ServerSocketChannel
                .option(ChannelOption.SO_BACKLOG, 1024)

                //childOption是针对每个连接服务端的channel， SocketChannel
                //一般只有server端有
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                    }
                });

        serverBootstrap.bind(PORT);
    }

    //绑定端口时，显示是否绑定成功
    private static void bind(final ServerBootstrap serverBootstrap, final int port){
        serverBootstrap.bind(port).addListener(future->{
            if(future.isSuccess())
                System.out.println("端口[" + port+"]绑定成功");
            else
                System.out.println("端口绑定失败");
        });
    }



}
