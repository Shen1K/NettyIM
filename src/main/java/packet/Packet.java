package packet;

//用于定义通信协议信息
public abstract class Packet{
    /*
    协议版本
     */
    private Byte version = 1;

    public Byte getVersion() {
        return version;
    }

    public void setVersion(Byte version) {
        this.version = version;
    }

    /**
     * 指令
     * @return
     */
    public abstract Byte getCommand();
}
