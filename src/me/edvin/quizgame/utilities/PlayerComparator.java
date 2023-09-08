package me.edvin.quizgame.utilities;

import me.edvin.quizgame.player.Player;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {
    /**
     * Compares scores between 2 players
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return difference
     */
    @Override
    public int compare(Player o1, Player o2) {
        return o2.getScore() - o1.getScore();
    }
}
