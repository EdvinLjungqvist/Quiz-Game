package me.edvin.quizgame.game;

import me.edvin.quizgame.card.Card;
import me.edvin.quizgame.player.Player;
import me.edvin.quizgame.utilities.PlayerComparator;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private final PlayerComparator comparator = new PlayerComparator();
    private final List<Player> players = new ArrayList<>();
    private Card card;
    private int round = 1;

    public Game() {}

    /**
     * Returns players of game
     * @return game
     */
    public List<Player> getPlayers() {
        return players;
    }

    /**
     * Adds or replaces players to game
     * @param players players
     */
    public void addPlayers(List<Player> players) {
        this.players.clear();
        this.players.addAll(players);
        this.players.sort(comparator);
    }

    /**
     * Returns the winner
     * @return winner
     */
    public Player getWinner() {
        return players.stream()
                .min(comparator)
                .orElse(null);
    }

    /**
     * Returns card of game
     * @return card
     */
    public Card getCard() {
        return card;
    }

    /**
     * Sets card of game
     * @param card card
     */
    public void setCard(Card card) {
        this.card = card;
    }

    /**
     * Returns round of game
     * @return round
     */
    public int getRound() {
        return round;
    }

    /**
     * Sets round of game
     * @param round round
     */
    public void setRound(int round) {
        this.round = round;
    }

    /**
     * Adds round to game
     */
    public void addRound() {
        round++;
    }
}
