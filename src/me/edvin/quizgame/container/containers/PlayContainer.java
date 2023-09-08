package me.edvin.quizgame.container.containers;

import me.edvin.quizgame.container.Container;
import me.edvin.quizgame.network.Client;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class PlayContainer extends Container {

    private final JTextField nameField = new JTextField("Guest");
    private final JTextField hostField = new JTextField("localhost");
    private final JTextField portField = new JTextField("2000");
    private final JButton playButton = new JButton("Play");
    private final JButton leaveButton = new JButton("Leave");

    public PlayContainer(Client client) {
        super(client, "Play");

        CompoundBorder border = new CompoundBorder(new LineBorder(new Color(150, 150, 150), 1), new EmptyBorder(4, 4, 4, 4));
        nameField.setBorder(border);
        hostField.setBorder(border);
        portField.setBorder(border);
        playButton.setBackground(new Color(150, 255, 150));
        playButton.setFocusable(false);
        leaveButton.setBackground(new Color(255, 150, 150));
        leaveButton.setFocusable(false);

        c.fill = GridBagConstraints.BOTH;
        c.ipady = 15;
        c.insets = new Insets(2, 2, 2, 2);
        add(new JLabel("Name:", SwingConstants.RIGHT), c);
        c.gridy = 1;
        add(new JLabel("Host & Port:", SwingConstants.RIGHT), c);
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 2;
        c.weightx = 1;
        add(nameField, c);
        c.gridy = 1;
        c.gridwidth = 1;
        add(hostField, c);
        c.gridx = 2;
        add(portField, c);
        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.ipadx = 50;
        add(playButton, c);
        c.gridx = 2;
        add(leaveButton, c);

        playButton.addActionListener(event -> {
            try {
                String name = nameField.getText();
                String host = hostField.getText();
                int port = Integer.parseInt(portField.getText());

                client.connect(name, host, port);
            } catch (NumberFormatException e) {
                client.addMessage("Client: Invalid port! Please enter a different port.");
            }
        });
        leaveButton.addActionListener(event -> {
            client.disconnect();
            client.updateContainers();
        });

        update();
    }

    @Override
    public void update() {
        boolean connected = client.isConnected();

        nameField.setEnabled(!connected);
        hostField.setEnabled(!connected);
        portField.setEnabled(!connected);
        playButton.setEnabled(!connected);
        leaveButton.setEnabled(connected);
    }
}
