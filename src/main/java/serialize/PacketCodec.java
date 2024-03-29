package serialize;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import packet.Packet;
import packet.request.LoginRequestPacket;
import packet.response.LoginResponsePacket;
import serialize.impl.JSONSerializer;

import java.util.HashMap;
import java.util.Map;

import static packet.Command.LOGIN_REQUEST;
import static packet.Command.LOGIN_RESPONSE;

/**
 * 用于通信信息编码和解码
 */
public class PacketCodec {

    public static final PacketCodec INSTANCE = new PacketCodec();

    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Byte, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlogrithm(), serializer);
    }

    public ByteBuf encode(Packet packet){
        //1.创建ByteBuf对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();

        //2.序列化java对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3.实际的编码过程
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlogrithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
    }

    public Packet decode(ByteBuf byteBuf){
        //跳过无用协议部分，得到要传输的数据
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
