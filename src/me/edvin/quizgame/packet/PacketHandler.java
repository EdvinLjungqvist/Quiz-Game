package me.edvin.quizgame.packet;

import me.edvin.quizgame.packet.packets.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class PacketHandler {

    private final Map<Integer, Supplier<? extends Packet>> suppliers = new HashMap<>();
    private final Map<Class<? extends Packet>, Integer> ids = new HashMap<>();
    private final PacketListener listener;

    public PacketHandler(PacketListener listener) {
        this.listener = listener;

        register(AlternativePacket.class, AlternativePacket::new);
        register(CardPacket.class, CardPacket::new);
        register(DisconnectPacket.class, DisconnectPacket::new);
        register(MessagePacket.class, MessagePacket::new);
        register(PlayerPacket.class, PlayerPacket::new);
        register(RoundPacket.class, RoundPacket::new);
        register(StatePacket.class, StatePacket::new);
    }

    /**
     * Identifies id of packet and encodes it
     * @param packet packet
     * @param out output stream
     */
    public void encode(Packet packet, DataOutputStream out) {
        try {
            Integer id = ids.get(packet.getClass());

            if (id != null) {
                out.writeInt(id);
                packet.encode(out);
            }
        } catch (IOException ignored) {}
    }

    /**
     * Identifies id of packet and decodes it
     * @param in input stream
     * @throws IOException if I/O error occurs
     */
    public void decode(DataInputStream in) throws IOException {
        int id = in.readInt();
        Supplier<? extends Packet> supplier = suppliers.get(id);

        if (supplier != null) {
            Packet packet = supplier.get();

            if (packet != null) {
                packet.decode(in);
                listener.onPacket(packet);
            }
        }
    }

    /**
     * Registers a packet
     * @param clazz class
     * @param supplier supplier
     * @param <T> packet
     */
    private <T extends Packet> void register(Class<T> clazz, Supplier<T> supplier) {
        int id = suppliers.size();
        suppliers.put(id, supplier);
        ids.put(clazz, id);
    }
}