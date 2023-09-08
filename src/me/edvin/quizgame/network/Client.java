package me.edvin.quizgame.network;

import me.edvin.quizgame.container.Container;
import me.edvin.quizgame.game.Game;
import me.edvin.quizgame.packet.Packet;
import me.edvin.quizgame.packet.PacketHandler;
import me.edvin.quizgame.packet.PacketListener;
import me.edvin.quizgame.packet.packets.*;
import me.edvin.quizgame.player.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements Runnable, PacketListener {

    private final PacketHandler handler = new PacketHandler(this);
    private final List<Container> containers = new ArrayList<>();
    private final List<String> messages = new ArrayList<>();
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private Thread thread;
    private Game game;
    private Player player;

    public Client() {}

    /**
     * Connects to a server
     * @param name player name
     * @param host host
     * @param port port
     */
    public void connect(String name, String host, int port) {
        try {
            new Server(port);
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            player = new Player(name);
            thread = new Thread(this);
            thread.start();

            send(new PlayerPacket(player));
        } catch (IOException e) {
            addMessage("Client: " + host + " is not available! Please try a different host.");
        }
    }

    /**
     * Disconnects from the server
     */
    public void disconnect() {
        try {
            socket.close();
            in.close();
            out.close();
            thread.interrupt();
            game = null;
            player = null;
            messages.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends packet to server
     * @param packet packet
     */
    public void send(Packet packet) {
        handler.encode(packet, out);
    }

    /**
     * Continuously reads packet from server
     */
    @Override
    public void run() {
        while (isConnected()) {
            try {
                handler.decode(in);
            } catch (IOException ignored) {}
        }
    }

    @Override
    public void onPacket(Packet packet) {
        if (packet instanceof CardPacket cardPacket) {
            game.setCard(cardPacket.getCard());
        } else if (packet instanceof DisconnectPacket disconnectPacket) {
            disconnect();
            addMessage("Server: " + disconnectPacket.getReason());
        } else if (packet instanceof MessagePacket messagePacket) {
            addMessage(messagePacket.getSender() + ": " + messagePacket.getMessage());
        } else if (packet instanceof PlayerPacket playerPacket) {
            int action = playerPacket.getAction();

            if (action == PlayerPacket.SET) {
                player = playerPacket.getPlayers().get(0);
            } else if (action == PlayerPacket.ADD) {
                game.addPlayers(playerPacket.getPlayers());
            }
        } else if (packet instanceof RoundPacket roundPacket) {
            game.setRound(roundPacket.getRound());
        } else if (packet instanceof StatePacket statePacket) {
            int state = statePacket.getState();

            if (state == StatePacket.CREATE) {
                game = new Game();
            } else if (state == StatePacket.REMOVE) {
                game = null;
            }
        }
        updateContainers();
    }

    /**
     * Adds container to client
     * @param container container
     */
    public void addContainer(Container container) {
        containers.add(container);
    }

    /**
     * Updates all containers
     */
    public void updateContainers() {
        for (Container container : containers) {
            container.update();
        }
    }

    /**
     * Returns if client is connected
     * @return connected
     */
    public boolean isConnected() {
        return socket != null && !socket.isClosed();
    }

    /**
     * Returns if client is playing
     * @return playing
     */
    public boolean isPlaying() {
        return game != null && player != null;
    }

    /**
     * Returns if client is host
     * @return host
     */
    public boolean isHost() {
        return player != null && player.isHost();
    }

    /**
     * Returns game
     * @return game
     */
    public Game getGame() {
        return game;
    }

    /**
     * Returns player
     * @return player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Returns chat messages
     * @return messages
     */
    public List<String> getMessages() {
        return messages;
    }

    /**
     * Adds message to chat
     * @param message message
     */
    public void addMessage(String message) {
        messages.add(message);
        updateContainers();
    }
}

