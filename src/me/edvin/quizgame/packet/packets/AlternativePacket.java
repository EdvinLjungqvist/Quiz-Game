package me.edvin.quizgame.packet.packets;

import me.edvin.quizgame.card.Alternative;
import me.edvin.quizgame.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AlternativePacket extends Packet {

    private Alternative alternative;

    public AlternativePacket(Alternative alternative) {
        this.alternative = alternative;
    }

    public AlternativePacket() {}

    @Override
    public void encode(DataOutputStream out) throws IOException {
        out.writeUTF(alternative.toString());
        out.writeBoolean(alternative.isCorrect());
    }

    @Override
    public void decode(DataInputStream in) throws IOException {
        alternative = new Alternative(in.readUTF(), in.readBoolean());
    }

    /**
     * Returns alternative from packet
     * @return alternative
     */
    public Alternative getAlternative() {
        return alternative;
    }
}
