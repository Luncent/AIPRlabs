package org.example;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.ArrayList;

public class serv extends Frame {
    public boolean handleEvent(Event evt)//Используется обработчик //событий ранних версий Java
    {
        if (evt.id==Event.WINDOW_DESTROY)//Закрыть приложение
        {System.exit(0);}
        return super.handleEvent(evt);
    }
    public boolean mouseDown(Event evt,int x,int y)//Обработчик //события от мыши
    {
        new clientThread().start();//Запуск потока клиента
        return(true);
    }
    public static void main(String args[])
    {
        serv f=new serv();
        f.resize(400,400);
        f.show();
        new Account().start();
    }
}

class clientThread extends Thread{
    BufferedReader br;
    Socket s=null;
    public clientThread() {
        try {
            s = new Socket("127.0.0.1", 3001);
            InputStreamReader isr = new InputStreamReader(s.getInputStream());
            br = new BufferedReader(isr);
        } catch (Exception e) {
            System.out.println("Ошибка:  " + e);
        }
    }

    public void run() {
        while (true) {
            try {
                sleep(100);
            } catch (Exception er) {
                System.out.println("Ошибка  " + er);
            }
            try {
                StringBuilder sb = new StringBuilder();
                String str;
                while((str = br.readLine())!=null){
                    sb.append(str);
                }
                System.out.println("Клиент принял сообщение: "+sb.toString());
                break;
            } catch (Exception e) {
                System.out.println("ERRORR+" + e);
            }
        }
    }
}

class Account extends Thread{
    List<Socket> clients= new ArrayList<>();
    ServerSocket server;
    String amountstring;
    static  int amount=200;
    public void run() {
        try {
            server = new ServerSocket(3001);//Номер сокета
        } catch (Exception e) {
            System.out.println("Ошибка соединения+" + e);
        }
        while (true) {
            try (Socket client = server.accept(); //ожидание соединения с клиентом
                 OutputStreamWriter osw = new OutputStreamWriter(client.getOutputStream());
                 BufferedWriter bw = new BufferedWriter(osw)) {

                int amountcur=((int)(Math.random()*1000)); //отрицательный вклад   снятие части денег со счета
                //отрицательный вклад   снятие части денег со счета
                if (Math.random() > 0.5)
                    amount -= amountcur;
                else
                    amount += amountcur;
                Integer x = new Integer(amount);
                amountstring = x.toString();
                bw.write("Account:" + amountstring +" port:"+client.getPort());
                //передача строки клиенту
                bw.flush();
            } catch (Exception e) {
                System.out.println("Ошибка  " + e);
            }
        }
    }
}
