package serialize;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import packet.Command;
import packet.Packet;
import packet.request.LoginRequestPacket;
import packet.request.MessageRequestPacket;
import packet.response.LoginResponsePacket;
import packet.response.MessageResponsePacket;
import serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import static packet.Command.LOGIN_REQUEST;
import static packet.Command.LOGIN_RESPONSE;

/**
 * ����ͨ����Ϣ����ͽ���
 */
public class PacketCodec {

    public static final PacketCodec INSTANCE = new PacketCodec();

    public static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(Command.MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(Command.MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }

    public ByteBuf encode(ByteBuf byteBuf, Packet packet){
        //1.���л�java����
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //2.ʵ�ʵı������
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public ByteBuf encode(Packet packet){
        //1.����ByteBuf����
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        //2.���л�java����
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3.ʵ�ʵı������
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf){
        //��������Э�鲿�֣��õ�Ҫ���������
        byteBuf.skipBytes(4);

        byteBuf.skipBytes(1);

        byte serialAlgorithm = byteBuf.readByte();

        byte command = byteBuf.readByte();

        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];

        byteBuf.readBytes(bytes);

        Class<? extends Packet> Type = getRequestType(command);
        Serializer serializer = getSerializer(serialAlgorithm);

        if(Type != null && serializer != null){
            return serializer.deserialize(Type, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {

        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {

        return packetTypeMap.get(command);
    }

}
