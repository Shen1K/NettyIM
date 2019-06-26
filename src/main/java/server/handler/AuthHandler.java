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
            //����û��Ѿ�ע�ᣬ��pipeline��ɾ�����Handler
            ctx.pipeline().remove(this);
            //���ڴ��ݵ������ChannelHandler
            super.channelRead(ctx, msg);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        if(Util.hasLogin(ctx.channel())){
            System.out.println("��ǰ���ӵ�¼��֤��ϣ������ٴ���֤, AuthHandler ���Ƴ�");
        }else {
            System.out.println("�޵�¼��֤��ǿ�ƹر�����!");
        }
    }
}
