package me.edvin.quizgame.card;

import java.util.List;

public class Card {

    private final String question;
    private final List<Alternative> alternatives;
    private final CardType type;

    public Card(String question, List<Alternative> alternatives, CardType type) {
        this.question = question;
        this.alternatives = alternatives;
        this.type = type;
    }

    /**
     * Returns question of card
     * @return question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Returns alternatives of card
     * @return alternatives
     */
    public List<Alternative> getAlternatives() {
        return alternatives;
    }

    /**
     * Returns type of card
     * @return type
     */
    public CardType getType() {
        return type;
    }
}
