package me.edvin.quizgame.container;

import me.edvin.quizgame.network.Client;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;

public abstract class Container extends JPanel {

    protected final GridBagConstraints c = new GridBagConstraints();
    protected final Client client;

    public Container(Client client, String title) {
        this.client = client;
        setBorder(new TitledBorder(new LineBorder(Color.WHITE, 3, true), title));
        setBackground(new Color(205, 230, 255));
        setLayout(new GridBagLayout());
        client.addContainer(this);
    }

    /**
     * Updates the container
     */
    public abstract void update();
}
