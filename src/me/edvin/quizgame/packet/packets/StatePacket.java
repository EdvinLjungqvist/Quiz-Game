package me.edvin.quizgame.packet.packets;

import me.edvin.quizgame.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class StatePacket extends Packet {

    public static int CREATE = 0, REMOVE = 1, START = 2, END = 3, NEXT = 4;
    private int state;

    public StatePacket(int state) {
        this.state = state;
    }

    public StatePacket() {}

    @Override
    public void encode(DataOutputStream out) throws IOException {
        out.writeInt(state);
    }

    @Override
    public void decode(DataInputStream in) throws IOException {
        state = in.readInt();
    }

    /**
     * Returns state from packet
     * @return state
     */
    public int getState() {
        return state;
    }
}
