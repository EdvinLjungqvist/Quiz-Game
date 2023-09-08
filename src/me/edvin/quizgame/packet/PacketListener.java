package me.edvin.quizgame.packet;

public interface PacketListener {

    /**
     * Handles incoming packets
     * @param packet packet
     */
    void onPacket(Packet packet);
}
