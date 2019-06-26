package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import util.Util;

public class AuthHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!Util.hasLogin(ctx.channel())){
            ctx.channel().close();
        }else {
            //如果用户已经注册，从pipeline中删除这个Handler
            ctx.pipeline().remove(this);
            //用于传递到后面的ChannelHandler
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(Util.hasLogin(ctx.channel())){
            System.out.println("当前连接登录验证完毕，无需再次验证, AuthHandler 被移除");
        }else {
            System.out.println("无登录验证，强制关闭连接!");
        }
    }
}
