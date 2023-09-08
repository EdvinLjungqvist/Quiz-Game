package me.edvin.quizgame.packet.packets;

import me.edvin.quizgame.card.Alternative;
import me.edvin.quizgame.card.Card;
import me.edvin.quizgame.card.CardType;
import me.edvin.quizgame.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardPacket extends Packet {

    private Card card;

    public CardPacket(Card card) {
        this.card = card;
    }

    public CardPacket() {}

    @Override
    public void encode(DataOutputStream out) throws IOException {
        out.writeUTF(card.getQuestion());
        out.writeInt(card.getAlternatives().size());

        for (Alternative alternative : card.getAlternatives()) {
            out.writeUTF(alternative.toString());
            out.writeBoolean(alternative.isCorrect());
        }
        out.writeUTF(card.getType().name());
    }

    @Override
    public void decode(DataInputStream in) throws IOException {
        String question = in.readUTF();
        int amount = in.readInt();
        List<Alternative> alternatives = new ArrayList<>();

        for (int i = 0; i < amount; i++) {
            alternatives.add(new Alternative(in.readUTF(), in.readBoolean()));
        }
        CardType type = CardType.valueOf(in.readUTF());

        card = new Card(question, alternatives, type);
    }

    /**
     * Returns card from packet
     * @return card
     */
    public Card getCard() {
        return card;
    }
}
