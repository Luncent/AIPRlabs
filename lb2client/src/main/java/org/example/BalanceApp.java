package org.example;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class BalanceApp extends Frame{
    String ipAddress = null;
    int port=0;
    BufferedWriter bw;
    BufferedReader br;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("введите ip адрес (формата 127.0.0.1) :");
        String ipAddress = scanner.nextLine();
        System.out.println("введите port (3001) :");
        int port = scanner.nextInt();
        Frame frame = new BalanceApp(ipAddress,port);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(3, 2));
    }

    public BalanceApp(String ip, int port){
        this.ipAddress=ip;
        this.port = port;
        Label balanceLabel = new Label("Баланс: 0");
        balanceLabel.setAlignment(Label.RIGHT);

        // Кнопка для показа баланса
        Button showBalanceButton = new Button("Показать баланс");
        showBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Socket server = new Socket(ipAddress, port);
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
                    Socket server = new Socket(ipAddress, port);
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