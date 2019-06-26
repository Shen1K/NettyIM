package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import packet.request.LoginRequestPacket;
import packet.response.LoginResponsePacket;
import util.Util;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
        loginRequestPacket.setName("sk");
        loginRequestPacket.setUserId(UUID.randomUUID().toString());
        loginRequestPacket.setPassword("pwd");

        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if(msg.isSuccess()){
            System.out.println(new Date() + ": �ͻ��˵�¼�ɹ�");
        }else {
            System.out.println(new Date() + ": �ͻ��˵�¼ʧ�ܣ�ԭ��" + msg.getReason());
        }
    }
}
