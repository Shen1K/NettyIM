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

    //当服务端产生连接时，执行这个操作

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(new Date() + "：客户端开始登录...");

        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodec.INSTANCE.decode(requestByteBuf);

        if(packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());

            if(valid(loginRequestPacket)){
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + "：登录成功");
            }else {
                loginResponsePacket.setSuccess(false);
                loginResponsePacket.setReason("密码校验失败");
                System.out.println(new Date() + "：登录失败");
            }

            //登录响应
            ByteBuf responseByteBuf = PacketCodec.INSTANCE.encode(loginResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    //用于确认用户的登录信息
    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
