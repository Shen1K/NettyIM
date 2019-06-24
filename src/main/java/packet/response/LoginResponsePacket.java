package packet.response;

import packet.Packet;

import static packet.Command.LOGIN_RESPONSE;

public class LoginResponsePacket extends Packet {
    //�����Ƿ�ɹ�
    private boolean success;

    //ʧ������
    private String reason;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public Byte getCommand() {
        return LOGIN_RESPONSE;
    }
}
