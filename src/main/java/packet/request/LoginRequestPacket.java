package packet.request;

import packet.Packet;

import static packet.Command.LOGIN_REQUEST;

public class LoginRequestPacket extends Packet {
    private String userId;

    //用户姓名

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String name;

    //用户密码
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
