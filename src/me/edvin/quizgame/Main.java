package me.edvin.quizgame;

import me.edvin.quizgame.container.MainFrame;
import me.edvin.quizgame.network.Client;

public class Main {

    /**
     * Starts main thread
     * @param args args
     */
    public static void main(String[] args) {
        new MainFrame(new Client());
    }
}
