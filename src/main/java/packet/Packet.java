package packet;

//���ڶ���ͨ��Э����Ϣ
public abstract class Packet{
    /*
    Э��汾
     */
    private Byte version = 1;

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }

    /**
     * ָ��
     * @return
     */
    public abstract Byte getCommand();
}
