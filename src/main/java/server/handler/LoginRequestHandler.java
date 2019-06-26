package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import packet.request.LoginRequestPacket;
import packet.response.LoginResponsePacket;
import util.Util;

import java.util.Date;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        System.out.println(new Date() + ": ÊÕµ½¿Í»§¶ËµÇÂ¼ÇëÇó¡­¡­");
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(msg.getVersion());

        if(valid(msg)){
            Util.markAsLogin(ctx.channel());
            loginResponsePacket.setSuccess(true);
            System.out.println(new Date() + "£ºµÇÂ¼³É¹¦£¡");
        }else {
            loginResponsePacket.setReason("µÇÂ¼Ê§°Ü");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + "£ºµÇÂ¼Ê§°Ü");
        }
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
