package me.edvin.quizgame.data;

import com.google.gson.*;
import me.edvin.quizgame.card.Alternative;
import me.edvin.quizgame.card.Card;
import me.edvin.quizgame.card.CardType;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CardStorage {

    private final List<Card> cards = new ArrayList<>();
    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    public CardStorage() {
        load();
    }

    /**
     * Loads cards from the cards.json file
     */
    private void load() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("cards.json");
        InputStreamReader reader = new InputStreamReader(inputStream);
        JsonArray cardArray = gson.fromJson(reader, JsonArray.class);

        for (JsonElement cardElement : cardArray) {
            JsonObject cardObject = cardElement.getAsJsonObject();
            String question = cardObject.get("question").getAsString();
            JsonArray alternativeArray = cardObject.get("alternatives").getAsJsonArray();
            List<Alternative> alternatives = new ArrayList<>();

            for (JsonElement alternativeElement : alternativeArray) {
                JsonObject alternativeObject = alternativeElement.getAsJsonObject();
                String text = alternativeObject.get("text").getAsString();
                boolean correct = alternativeObject.get("correct").getAsBoolean();
                Alternative alternative = new Alternative(text, correct);

                alternatives.add(alternative);
            }
            CardType type = CardType.valueOf(cardObject.get("type").getAsString());

            cards.add(new Card(question, alternatives, type));
        }
    }

    /**
     * Saves cards to the cards.json file (not used in this project)
     */
    public void save() {
        JsonArray cardArray = new JsonArray();

        for (Card card : cards) {
            JsonObject cardObject = new JsonObject();
            cardObject.addProperty("question", card.getQuestion());
            JsonArray alternativeArray = new JsonArray();

            for (Alternative alternative : card.getAlternatives()) {
                JsonObject alternativeObject = new JsonObject();
                alternativeObject.addProperty("text", alternative.toString());
                alternativeObject.addProperty("correct", alternative.isCorrect());
                alternativeArray.add(alternativeObject);
            }
            cardObject.add("alternatives", alternativeArray);
            cardObject.addProperty("type", card.getType().name());
            cardArray.add(cardObject);
        }

        try (FileWriter fileWriter = new FileWriter("src/resources/cards.json")) {
            gson.toJson(cardArray, fileWriter);
        } catch (IOException ignored) {}
    }

    /**
     * Returns cards
     * @return cards
     */
    public List<Card> getCards() {
        return cards;
    }
}
