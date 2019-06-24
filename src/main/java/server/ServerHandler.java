package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import packet.Packet;
import packet.request.LoginRequestPacket;
import packet.response.LoginResponsePacket;
import serialize.PacketCodec;

import java.util.Date;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    //������˲�������ʱ��ִ���������

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date() + "���ͻ��˿�ʼ��¼...");

        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(requestByteBuf);

        if(packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());

            if(valid(loginRequestPacket)){
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + "����¼�ɹ�");
            }else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("����У��ʧ��");
                System.out.println(new Date() + "����¼ʧ��");
            }

            //��¼��Ӧ
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    //����ȷ���û��ĵ�¼��Ϣ
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
