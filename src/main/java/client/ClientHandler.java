package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.Packet;
import packet.request.LoginRequestPacket;
import packet.response.LoginResponsePacket;
import packet.response.MessageResponsePacket;
import serialize.PacketCodec;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

    //��client��server��������ʱ�������������
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": �ͻ��˿�ʼ��¼");

        //������¼����
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setName("sk");
        loginRequestPacket.setPassword("pwd");

        //����
        ByteBuf buf = PacketCodec.INSTANCE.encode(loginRequestPacket);

        ctx.channel().writeAndFlush(buf);
    }

    //�����ܵ�server���͵�response
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("���ܵ�response");
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if(loginResponsePacket.isSuccess())
                System.out.println(new Date() + "���ͻ��˵�¼�ɹ�");
            else
                System.out.println(new Date() + ": �ͻ��˵�¼ʧ�ܣ�ԭ��" + loginResponsePacket.getReason());
        }else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + "���յ��ͻ�����Ϣ��"+messageResponsePacket.getMessage());
        }
    }
}
