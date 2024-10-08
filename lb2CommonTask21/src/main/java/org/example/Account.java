package org.example;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Account{
    private static ServerSocket server;
    private static double balance = 1000;

    public static void startup()
    {
        try {
            server = new ServerSocket(3001);//Номер сокета
        } catch (Exception e) {
            System.out.println("Ошибка соединения+" + e);
        }
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
}
