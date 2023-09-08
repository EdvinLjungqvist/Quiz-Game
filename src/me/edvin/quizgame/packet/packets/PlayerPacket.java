package me.edvin.quizgame.packet.packets;

import me.edvin.quizgame.packet.Packet;
import me.edvin.quizgame.player.Player;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerPacket extends Packet {

    public static int SET = 0, ADD = 1;
    private int action;
    private List<Player> players;

    public PlayerPacket(int action, List<Player> players) {
        this.action = action;
        this.players = players;
    }

    public PlayerPacket(Player player) {
        this(SET, List.of(player));
    }

    public PlayerPacket(List<Player> players) {
        this(ADD, players);
    }

    public PlayerPacket() {}

    @Override
    public void encode(DataOutputStream out) throws IOException {
        out.writeInt(action);
        out.writeInt(players.size());

        for (Player player : players) {
            out.writeUTF(player.getName());
            out.writeBoolean(player.isHost());
            out.writeBoolean(player.hasAnswered());
            out.writeInt(player.getScore());
        }
    }

    @Override
    public void decode(DataInputStream in) throws IOException {
        action = in.readInt();
        players = new ArrayList<>();
        int amount = in.readInt();

        for (int i = 0; i < amount; i++) {
            players.add(new Player(in.readUTF(), in.readBoolean(), in.readBoolean(), in.readInt()));
        }
    }

    /**
     * Returns action from packet
     * @return action
     */
    public int getAction() {
        return action;
    }

    /**
     * Returns players from packet
     * @return players
     */
    public List<Player> getPlayers() {
        return players;
    }
}
