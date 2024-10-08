package org.example;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class serv extends Frame {
    private ServerSocket server;
    private double balance = 1000;

    public serv() {
        try {
            server = new ServerSocket(3001);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return;
        }
        setTitle("Server");
        setSize(400, 400);
        setLocationRelativeTo(null);
        JLabel loadingLabel = new JLabel("Баланс счета= "+balance, SwingConstants.CENTER);
        add(loadingLabel, BorderLayout.CENTER);
        repaint();
    }
    public boolean handleEvent(Event evt)//Используется обработчик //событий ранних версий Java
    {
        if (evt.id==Event.WINDOW_DESTROY)//Закрыть приложение
        {System.exit(0);}
        return super.handleEvent(evt);
    }
    public boolean mouseDown(Event evt,int x,int y)//Обработчик //события от мыши
    {
        System.out.println("запуск клиента");
        new Thread(()->{
            Frame frame = new Balance(balance);
            frame.setSize(400, 200);
            frame.setLayout(new GridLayout(3, 2));
        }).start();//Запуск потока клиента
        return(true);
    }
    public void listenClients(){
        while (true) {
            System.out.println("Waiting for clietn");
            try (Socket client = server.accept(); //ожидание соединения с клиентом
                 InputStreamReader isr = new InputStreamReader(client.getInputStream());
                 BufferedReader br = new BufferedReader(isr);
                 OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
                 BufferedWriter bw = new BufferedWriter(osw)) {

                String method = br.readLine();
                if (method.equals("changeBalance")) {
                    double changeAmount = Double.parseDouble(br.readLine());
                    balance += changeAmount;
                    changeLabelContent();
                    System.out.println("Changing balance");
                    bw.write(String.valueOf(balance));
                } else if (method.equals("giveBalance")) {
                    System.out.println("Giving balance");
                    bw.write(String.valueOf(balance));
                }
                bw.newLine();
                bw.flush();
            } catch (Exception e) {
                System.out.println("Ошибка  " + e);
            }
        }
    }

    private void changeLabelContent() {
        SwingUtilities.invokeLater(() -> {
            removeAll();
            JLabel loadingLabel = new JLabel("Баланс счета= "+balance, SwingConstants.CENTER);
            add(loadingLabel, BorderLayout.CENTER);
            revalidate();
            repaint();
        });
    }

    public static void main(String args[])
    {
        serv account = new serv();
        SwingUtilities.invokeLater(()-> {
                account.setVisible(true);
        });
        account.listenClients();
    }
}