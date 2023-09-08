package me.edvin.quizgame.container;

import me.edvin.quizgame.container.containers.CardContainer;
import me.edvin.quizgame.container.containers.ChatContainer;
import me.edvin.quizgame.container.containers.GameContainer;
import me.edvin.quizgame.container.containers.PlayContainer;
import me.edvin.quizgame.network.Client;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    public MainFrame(Client client) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        getContentPane().setBackground(new Color(200, 225, 250));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weightx = 1;
        c.weighty = 0;
        c.insets = new Insets(5, 5, 5, 5);
        add(new CardContainer(client), c);
        c.gridy = 1;
        c.gridwidth = 1;
        add(new GameContainer(client), c);
        c.gridx = 1;
        add(new PlayContainer(client), c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.weighty = 1;
        add(new ChatContainer(client), c);

        pack();
        setVisible(true);
    }

}
