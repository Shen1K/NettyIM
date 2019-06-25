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

    //当client与server建立连接时，触发这个操作
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登录");

        //创建登录对象
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setName("sk");
        loginRequestPacket.setPassword("pwd");

        //编码
        ByteBuf buf = PacketCodec.INSTANCE.encode(loginRequestPacket);

        ctx.channel().writeAndFlush(buf);
    }

    //当接受到server发送的response
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("接受到response");
        ByteBuf byteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(byteBuf);

        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;

            if(loginResponsePacket.isSuccess())
                System.out.println(new Date() + "：客户端登录成功");
            else
                System.out.println(new Date() + ": 客户端登录失败，原因" + loginResponsePacket.getReason());
        }else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println(new Date() + "：收到客户端消息："+messageResponsePacket.getMessage());
        }
    }
}
