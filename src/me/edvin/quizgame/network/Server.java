package me.edvin.quizgame.network;

import me.edvin.quizgame.card.Alternative;
import me.edvin.quizgame.card.Card;
import me.edvin.quizgame.data.CardStorage;
import me.edvin.quizgame.game.Game;
import me.edvin.quizgame.packet.Packet;
import me.edvin.quizgame.packet.packets.*;
import me.edvin.quizgame.player.Player;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server implements Runnable {

    private final Map<ClientHandler, Player> players = new HashMap<>();
    private final CardStorage storage = new CardStorage();
    private final Thread thread = new Thread(this);
    private ServerSocket socket;
    private Game game;

    public Server(int port) {
        try {
            socket = new ServerSocket(port);
            thread.start();
        } catch (IOException ignored) {}
    }

    /**
     * Stops the server
     */
    public void stop() {
        try {
            socket.close();
            thread.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Registers a player
     * @param client client
     * @param player player
     */
    public void register(ClientHandler client, Player player) {
        if (!containsName(player.getName())) {
            if (players.isEmpty()) {
                player.setHost(true);
                client.send(new PlayerPacket(player));
            }
            players.put(client, player);

            client.send(new MessagePacket("Server", "Welcome to Quiz Game, " + player.getName() + "!"));
            broadcast(new MessagePacket("Server", player.getName() + " has joined the server!"), client);
        } else {
            client.send(new DisconnectPacket(player.getName() + " is already taken! Please enter a different name."));
        }
    }

    /**
     * Unregisters a player
     * @param client client
     */
    public void unregister(ClientHandler client) {
        Player player = players.remove(client);

        if (player != null) {
            if (player.isHost()) {
                broadcast(new DisconnectPacket("You left the server since the host left!"), null);
                stop();
            } else {
                broadcast(new MessagePacket("Server", player.getName() + " has left the server!"), null);
            }
        }
    }

    /**
     * Starts game
     */
    public void startGame() {
        game = new Game();
        game.setCard(storage.getCards().get(0));
        game.addPlayers(new ArrayList<>(players.values()));
        broadcast(new StatePacket(StatePacket.CREATE), null);
        broadcast(new CardPacket(game.getCard()), null);
        broadcast(new PlayerPacket(game.getPlayers()), null);
    }

    /**
     * Ends game
     */
    public void endGame() {
        broadcast(new MessagePacket("Server", game.getWinner().getName() + " has won!"), null);
        broadcast(new StatePacket(StatePacket.REMOVE), null);
        game = null;
    }

    /**
     * Changes card
     */
    public void nextCard() {
        List<Card> cards = storage.getCards();
        int index = cards.indexOf(game.getCard()) + 1;

        for (Map.Entry<ClientHandler, Player> entry : players.entrySet()) {
            if (game.getPlayers().contains(entry.getValue())) {
                entry.getValue().setAnswered(false);
                entry.getKey().send(new PlayerPacket(entry.getValue()));
            }
        }
        game.setCard(cards.get(index < cards.size() ? index : 0));
        game.getPlayers().forEach(player -> player.setAnswered(false));
        game.addRound();
        broadcast(new PlayerPacket(game.getPlayers()), null);
        broadcast(new CardPacket(game.getCard()), null);
        broadcast(new RoundPacket(game.getRound()), null);
    }

    /**
     * Answers an alternative
     * @param client client
     * @param alternative alternative
     */
    public void answer(ClientHandler client, Alternative alternative) {
        Player player = players.get(client);

        if (alternative.isCorrect()) {
            player.addScore();
            client.send(new MessagePacket("Server", "You were correct!"));
            broadcast(new MessagePacket("Server", player.getName() + " was correct!"), client);
        } else {
            client.send(new MessagePacket("Server", "You were incorrect!"));
        }
        player.setAnswered(true);
        client.send(new PlayerPacket(player));
        broadcast(new PlayerPacket(game.getPlayers()), null);
    }

    /**
     * Sends packet to all clients except the sender
     * @param packet packet
     * @param sender sender
     */
    public void broadcast(Packet packet, ClientHandler sender) {
        for (ClientHandler client : players.keySet()) {
            if (sender == null || sender != client) {
                client.send(packet);
            }
        }
    }

    /**
     * Returns if a player has the same name
     * @param name name
     * @return contains
     */
    private boolean containsName(String name) {
        for (Player player : players.values()) {
            if (player.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns if server is listening
     * @return listening
     */
    private boolean isListening() {
        return socket != null && !socket.isClosed();
    }

    /**
     * Continuously listens for connections
     */
    @Override
    public void run() {
        while (isListening()) {
            try {
                new ClientHandler(this, socket.accept());
            } catch (IOException ignored) {}
        }
    }
}
