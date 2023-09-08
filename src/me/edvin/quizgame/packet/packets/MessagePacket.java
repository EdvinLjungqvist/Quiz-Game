package me.edvin.quizgame.packet.packets;

import me.edvin.quizgame.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class MessagePacket extends Packet {

    private String sender;
    private String message;

    public MessagePacket(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    public MessagePacket() {}

    @Override
    public void encode(DataOutputStream out) throws IOException {
        out.writeUTF(sender);
        out.writeUTF(message);
    }

    @Override
    public void decode(DataInputStream in) throws IOException {
        sender = in.readUTF();
        message = in.readUTF();
    }

    /**
     * Returns sender from packet
     * @return sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * Returns message from packet
     * @return message
     */
    public String getMessage() {
        return message;
    }
}
