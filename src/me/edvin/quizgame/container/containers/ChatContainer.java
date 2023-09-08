package me.edvin.quizgame.container.containers;

import me.edvin.quizgame.container.Container;
import me.edvin.quizgame.network.Client;
import me.edvin.quizgame.packet.packets.MessagePacket;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ChatContainer extends Container {

    private final JList<String> chatList = new JList<>();
    private final JTextField chatField = new JTextField();
    private final JButton sendButton = new JButton("Send message");

    public ChatContainer(Client client) {
        super(client, "Chat");

        sendButton.setBackground(new Color(150, 150, 255));
        sendButton.setFocusable(false);

        JScrollPane scroll = new JScrollPane(chatList);
        CompoundBorder border = new CompoundBorder(new LineBorder(new Color(150, 150, 150), 1), new EmptyBorder(4, 4, 4, 4));
        chatList.setBorder(border);
        chatField.setBorder(border);

        c.fill = GridBagConstraints.BOTH;
        c.gridwidth = 2;
        c.weightx = 1;
        c.weighty = 1;
        c.ipady = 100;
        add(scroll, c);
        c.gridy = 1;
        c.gridwidth = 1;
        c.weighty = 0;
        c.ipady = 15;
        add(chatField, c);
        c.gridx = 1;
        c.weightx = 0;
        add(sendButton, c);

        chatField.addActionListener(event -> sendMessage());
        sendButton.addActionListener(event -> sendMessage());

        update();
    }

    @Override
    public void update() {
        chatList.clearSelection();
        chatList.setListData(client.getMessages().toArray(new String[0]));
        sendButton.setEnabled(client.isConnected());
    }

    /**
     * Sends message
     */
    private void sendMessage() {
        String message = chatField.getText();

        if (client.isConnected() && message.length() > 0 && message.length() < 100) {
            client.addMessage("You: " + message);
            client.send(new MessagePacket(client.getPlayer().getName(), message));
            chatField.setText("");
            update();
        }
        int index = chatList.getModel().getSize() - 1;

        if (index >= 0) {
            chatList.ensureIndexIsVisible(index);
        }
    }
}
