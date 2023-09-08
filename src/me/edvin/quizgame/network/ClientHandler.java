package me.edvin.quizgame.network;

import me.edvin.quizgame.packet.Packet;
import me.edvin.quizgame.packet.PacketHandler;
import me.edvin.quizgame.packet.PacketListener;
import me.edvin.quizgame.packet.packets.AlternativePacket;
import me.edvin.quizgame.packet.packets.MessagePacket;
import me.edvin.quizgame.packet.packets.PlayerPacket;
import me.edvin.quizgame.packet.packets.StatePacket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable, PacketListener {

    private final PacketHandler handler = new PacketHandler(this);
    private final Thread thread = new Thread(this);
    private final Server server;
    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    public ClientHandler(Server server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        thread.start();
    }

    /**
     * Closes connection to client
     */
    private void close() {
        try {
            server.unregister(this);
            socket.close();
            in.close();
            out.close();
            thread.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a packet
     * @param packet packet
     */
    public void send(Packet packet) {
        handler.encode(packet, out);
    }

    /**
     * Continuously reads packet from client
     */
    @Override
    public void run() {
        while (!socket.isClosed()) {
            try {
                handler.decode(in);
            } catch (IOException e) {
                close();
            }
        }
    }

    @Override
    public void onPacket(Packet packet) {
        if (packet instanceof AlternativePacket alternativePacket) {
            server.answer(this, alternativePacket.getAlternative());
        } else if (packet instanceof MessagePacket messagePacket) {
            server.broadcast(messagePacket, this);
        }
        else if (packet instanceof PlayerPacket playerPacket) {
            if (playerPacket.getAction() == PlayerPacket.SET) {
                server.register(this, playerPacket.getPlayers().get(0));
            }
        } else if (packet instanceof StatePacket statePacket) {
            int state = statePacket.getState();

            if (state == StatePacket.START) {
                server.startGame();
            } else if (state == StatePacket.END) {
                server.endGame();
            } else if (state == StatePacket.NEXT) {
                server.nextCard();
            }
        }
    }
}
