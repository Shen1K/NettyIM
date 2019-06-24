package packet.request;

import packet.Packet;

import static packet.Command.LOGIN_REQUEST;

public class LoginRequestPacket extends Packet {
    private String userId;

    //�û�����

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String name;

    //�û�����
    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Byte getCommand() {
        return LOGIN_REQUEST;
    }
}
