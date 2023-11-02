package me.edvin.quizgame.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Packet {

    /**
     * Sends data to an output stream
     * @param out output stream
     * @throws IOException if I/O error occurs
     */
    public abstract void encode(DataOutputStream out) throws IOException;

    /**
     * Reads data from an input stream
     * @param in input stream
     * @throws IOException if I/O error occurs
     */
    public abstract void decode(DataInputStream in) throws IOException;
}
