package org.example;

import java.awt.*;

public class Main {
    public static void main(String[] args) {
        new Thread(()->{
            Frame frame = new Balance();
            frame.setSize(400, 200);
            frame.setLayout(new GridLayout(3, 2));
        }).start();
        new Thread(()->{
            Frame frame = new Balance();
            frame.setSize(400, 200);
            frame.setLayout(new GridLayout(3, 2));
        }).start();
        Account.startup();
    }
}

