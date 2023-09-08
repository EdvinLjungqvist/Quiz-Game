package me.edvin.quizgame.container.containers;

import me.edvin.quizgame.card.Alternative;
import me.edvin.quizgame.card.Card;
import me.edvin.quizgame.container.Container;
import me.edvin.quizgame.game.Game;
import me.edvin.quizgame.network.Client;
import me.edvin.quizgame.packet.packets.AlternativePacket;

import javax.swing.*;
import java.awt.*;

public class CardContainer extends Container {

    private final JLabel typeLabel = new JLabel("Type: ?");
    private final JLabel questionLabel = new JLabel("Question: ?", SwingConstants.CENTER);
    private final JComboBox<Alternative> alternativeBox = new JComboBox<>();
    private final JButton answerButton = new JButton("Answer");

    public CardContainer(Client client) {
        super(client, "Card");

        alternativeBox.setBackground(Color.WHITE);
        alternativeBox.setFocusable(false);
        answerButton.setBackground(new Color(150, 255, 150));
        answerButton.setFocusable(false);

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weightx = 1;
        c.ipady = 15;
        c.insets = new Insets(0, 5, 0, 0);
        add(typeLabel, c);
        c.gridy = 1;
        c.insets = new Insets(25, 0, 0, 0);
        add(questionLabel, c);
        c.gridy = 2;
        c.gridwidth = 1;
        add(alternativeBox, c);
        c.gridx = 1;
        c.weightx = 0;
        add(answerButton, c);

        answerButton.addActionListener(event -> {
            client.send(new AlternativePacket((Alternative) alternativeBox.getSelectedItem()));
        });

        update();
    }

    @Override
    public void update() {
        Game game = client.getGame();
        boolean playing = client.isPlaying();

        if (playing) {
            Card card = game.getCard();

            if (card != null) {
                typeLabel.setText("Type: " + card.getType().getName());
                questionLabel.setText("Question: " + card.getQuestion());
                alternativeBox.setModel(new DefaultComboBoxModel<>(card.getAlternatives().toArray(new Alternative[0])));
            }
        } else {
            typeLabel.setText("Type: ?");
            questionLabel.setText("Question: ?");
            alternativeBox.setModel(new DefaultComboBoxModel<>());
        }
        answerButton.setEnabled(playing && !client.getPlayer().hasAnswered());
    }
}
