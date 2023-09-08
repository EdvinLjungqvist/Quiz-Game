package me.edvin.quizgame.player;

public class Player {

    private final String name;
    private boolean host;
    private boolean answered;
    private int score;

    public Player(String name, boolean host, boolean answered, int score) {
        this.name = name;
        this.host = host;
        this.answered = answered;
        this.score = score;
    }

    public Player(String name) {
        this.name = name;
    }

    /**
     * Returns name of player
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns if player is host
     * @return host
     */
    public boolean isHost() {
        return host;
    }

    /**
     * Sets if player is host
     * @param host host
     */
    public void setHost(boolean host) {
        this.host = host;
    }

    /**
     * Returns if player has answered
     * @return answered
     */
    public boolean hasAnswered() {
        return answered;
    }

    /**
     * Sets if player has answered
     * @param answered answered
     */
    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    /**
     * Returns score of player
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns score of player
     * @param score score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Adds score to game
     */
    public void addScore() {
        score++;
    }
}
