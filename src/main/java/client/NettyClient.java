package client;


import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NettyClient {

    static final int PORT = 9000;
    static final String HOST = "127.0.0.1";

    //���Ӵ���
    static final int MAX_RETRY = 5;

    public static void main(String[] args){
        //�ͻ���������
        Bootstrap bootstrap = new Bootstrap();

        //�ͻ��˹���EventLoopGroup
        NioEventLoopGroup worker = new NioEventLoopGroup();

        bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new ClientHandler());
                    }
                });


        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if(future.isSuccess())
                System.out.println("���ӳɹ�");
            else if(retry ==0)
                System.out.println("����ʧ��");
            else{
                int order = MAX_RETRY-retry+1;
                int dely = 1 << order;
                System.out.println(new Date() + "����ʧ�ܣ� ��"+ order+"������");

                //��ʱִ��
                bootstrap.config().group().schedule(()->connect(bootstrap, host, port, retry-1), dely,
                        TimeUnit.SECONDS);
            }
        });
    }
}
