package me.edvin.quizgame.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Packet {

    /**
     * Sends data to a output stream
     * @param out output stream
     * @throws IOException if I/O error occurs
     */
    public abstract void encode(DataOutputStream out) throws IOException;

    /**
     * Reads data from a input stream
     * @param in input stream
     * @throws IOException if I/O error occurs
     */
    public abstract void decode(DataInputStream in) throws IOException;
}
