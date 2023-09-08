package me.edvin.quizgame.card;

public class Alternative {

    private final String text;
    private final boolean correct;

    public Alternative(String text, boolean correct) {
        this.text = text;
        this.correct = correct;
    }

    /**
     * Returns text of alternative
     * @return text
     */
    @Override
    public String toString() {
        return text;
    }

    /**
     * Returns if alternative is correct
     * @return correct
     */
    public boolean isCorrect() {
        return correct;
    }
}
