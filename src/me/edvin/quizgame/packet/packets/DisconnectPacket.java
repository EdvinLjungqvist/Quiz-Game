package me.edvin.quizgame.packet.packets;

import me.edvin.quizgame.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class DisconnectPacket extends Packet {

    private String reason;

    public DisconnectPacket(String reason) {
        this.reason = reason;
    }

    public DisconnectPacket() {}

    @Override
    public void encode(DataOutputStream out) throws IOException {
        out.writeUTF(reason);
    }

    @Override
    public void decode(DataInputStream in) throws IOException {
        reason = in.readUTF();
    }

    /**
     * Returns reason from packet
     * @return reason
     */
    public String getReason() {
        return reason;
    }
}
