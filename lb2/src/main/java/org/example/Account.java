package org.example;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Account{
    private static ServerSocket server;
    private static double balance = 1000;

    public static void main(String[] args)
    {
        try {
            server = new ServerSocket(3001);//Номер сокета
        } catch (Exception e) {
            System.out.println("Ошибка соединения+" + e);
        }
        while (true) {
            try (Socket client = server.accept(); //ожидание соединения с клиентом
                 InputStreamReader isr = new InputStreamReader(client.getInputStream());
                 BufferedReader br = new BufferedReader(isr);
                 OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
                 BufferedWriter bw = new BufferedWriter(osw)) {

                System.out.println("Client connected");
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
