package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {

    //���÷�������¶�Ķ˿�
    static final int PORT = 9000;
    public static void main(String[] args){

        //�����������
        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        //ʵ��reactorģ�͵������߳���
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();

        //���÷���˲���
        serverBootstrap.group(boss, work)
                //����channe�����ͣ�Nio������io
                .channel(NioServerSocketChannel.class)
                //Option����Է����Channel�Ĳ������ã�ServerSocketChannel
                .option(ChannelOption.SO_BACKLOG, 1024)

                //childOption�����ÿ�����ӷ���˵�channel�� SocketChannel
                //һ��ֻ��server����
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {

                    }
                });

        serverBootstrap.bind(PORT);
    }

    //�󶨶˿�ʱ����ʾ�Ƿ�󶨳ɹ�
    private static void bind(final ServerBootstrap serverBootstrap, final int port){
        serverBootstrap.bind(port).addListener(future->{
            if(future.isSuccess())
                System.out.println("�˿�[" + port+"]�󶨳ɹ�");
            else
                System.out.println("�˿ڰ�ʧ��");
        });
    }



}
