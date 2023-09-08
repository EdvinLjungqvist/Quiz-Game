package me.edvin.quizgame.packet.packets;

import me.edvin.quizgame.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class RoundPacket extends Packet {

    private int round;

    public RoundPacket(int round) {
        this.round = round;
    }

    public RoundPacket() {}

    @Override
    public void encode(DataOutputStream out) throws IOException {
        out.writeInt(round);
    }

    @Override
    public void decode(DataInputStream in) throws IOException {
        round = in.readInt();
    }

    /**
     * Returns round from packet
     * @return round
     */
    public int getRound() {
        return round;
    }
}
