package org.example;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Balance extends Frame {
    BufferedWriter bw;
    BufferedReader br;

    public static void main(String[] args) {
        Frame frame = new Balance();
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(3, 2));
    }

    public Balance(){
        Label balanceLabel = new Label("Баланс: 0");
        balanceLabel.setAlignment(Label.RIGHT);

        // Кнопка для показа баланса
        Button showBalanceButton = new Button("Показать баланс");
        showBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Socket server = new Socket("127.0.0.1", 3001);
                    bw = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
                    br = new BufferedReader(new InputStreamReader(server.getInputStream()));

                    bw.write("giveBalance");
                    bw.newLine();
                    bw.flush();
                    double balance = Double.parseDouble(br.readLine());
                    balanceLabel.setText("Баланс: " + balance);

                    bw.close();
                    br.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        // Поле для ввода изменения баланса
        TextField changeField = new TextField();

        // Кнопка для изменения баланса
        Button changeBalanceButton = new Button("Изменить баланс");
        changeBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Double.parseDouble(changeField.getText());
                    Socket server = new Socket("127.0.0.1", 3001);
                    bw = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
                    br = new BufferedReader(new InputStreamReader(server.getInputStream()));

                    bw.write("changeBalance");
                    bw.newLine();
                    bw.flush();
                    bw.write(changeField.getText());
                    bw.newLine();
                    bw.flush();
                    double balance = Double.parseDouble(br.readLine());
                    balanceLabel.setText("Баланс: " + balance);

                    bw.close();
                    br.close();
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        add(balanceLabel);
        add(showBalanceButton);
        add(changeField);
        add(changeBalanceButton);

        // Закрытие окна
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                dispose(); // Закрытие окна
            }
        });

        setVisible(true);
    }
}
