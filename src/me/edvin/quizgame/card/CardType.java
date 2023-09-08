package me.edvin.quizgame.card;

public enum CardType {

    MATH("Math"),
    SCIENCE("Science"),
    HISTORY("History"),
    LITERATURE("Literature"),
    GEOGRAPHY("Geography"),
    LANGUAGE("Language"),
    ART("Art"),
    SPORTS("Sports"),
    MUSIC("Music");

    private final String name;

    CardType(String name) {
        this.name = name;
    }

    /**
     * Returns name of type
     * @return name
     */
    public String getName() {
        return name;
    }
}
