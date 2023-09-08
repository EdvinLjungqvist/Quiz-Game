package me.edvin.quizgame.container.containers;

import me.edvin.quizgame.container.Container;
import me.edvin.quizgame.game.Game;
import me.edvin.quizgame.network.Client;
import me.edvin.quizgame.packet.packets.StatePacket;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GameContainer extends Container {

    private final JLabel roundLabel = new JLabel("Round: ?");
    private final JLabel scoreLabel = new JLabel("Score: ?");
    private final JComboBox<String> playerBox = new JComboBox<>();
    private final JButton startButton = new JButton("Start");
    private final JButton endButton = new JButton("End");
    private final JButton nextButton = new JButton("Next");

    public GameContainer(Client client) {
        super(client, "Game");

        roundLabel.setBorder(new EmptyBorder(5, 5, 5, 5));
        playerBox.setBackground(Color.WHITE);
        playerBox.setFocusable(false);
        startButton.setBackground(new Color(150, 255, 150));
        startButton.setFocusable(false);
        endButton.setBackground(new Color(255, 150, 150));
        endButton.setFocusable(false);
        nextButton.setBackground(new Color(150, 150, 255));
        nextButton.setFocusable(false);

        c.fill = GridBagConstraints.BOTH;
        c.ipady = 15;
        c.weightx = 1;
        c.insets = new Insets(2, 2, 2, 2);
        add(roundLabel, c);
        c.gridx = 1;
        add(scoreLabel, c);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        add(playerBox, c);
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipadx = 50;
        add(startButton, c);
        c.gridx = 1;
        add(endButton, c);
        c.gridx = 2;
        add(nextButton, c);

        startButton.addActionListener(event -> {
            client.send(new StatePacket(StatePacket.START));
        });
        endButton.addActionListener(event -> {
            client.send(new StatePacket(StatePacket.END));
        });
        nextButton.addActionListener(event -> {
            client.send(new StatePacket(StatePacket.NEXT));
        });

        update();
    }

    @Override
    public void update() {
        boolean playing = client.isPlaying();
        boolean host = client.isHost();

        playerBox.removeAllItems();
        if (playing) {
            Game game = client.getGame();

            roundLabel.setText("Round: " + game.getRound());
            scoreLabel.setText("Score: " + client.getPlayer().getScore());
            game.getPlayers().forEach(p -> playerBox.addItem((p.getName().equals(client.getPlayer().getName()) ? "You" : p.getName()) + " (Score: " + p.getScore() + ", " + (p.hasAnswered() ? "Answered" : "Answering") + ")"));
        } else {
            roundLabel.setText("Round: ?");
            scoreLabel.setText("Score: ?");
        }
        startButton.setEnabled(host && !playing);
        endButton.setEnabled(host && playing);
        nextButton.setEnabled(host && playing);
    }
}
