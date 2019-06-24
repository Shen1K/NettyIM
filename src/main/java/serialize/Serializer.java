package serialize;

import serialize.impl.JSONSerializer;

public interface Serializer {
    Serializer DEFAULT = new JSONSerializer();

    /**
     * ���л��㷨
     * @return
     */
    byte getSerializerAlogrithm();

    /**
     * java����ת��Ϊ������
     * @param object
     * @return
     */
    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);
}
