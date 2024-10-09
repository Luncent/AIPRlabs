package org.example;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.http.HttpResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Main {
    private static final String address = "http://localhost:8080/lb3WebApp-1.0/MyServlet?txt=%s&trans=%s";
    public static void main(String[] args) {
        JFrame frame = new JFrame("Переводчик");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 150);
        frame.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Поле для ввода русского слова
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(new JLabel("Русское слово:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        JTextField russianField = new JTextField(15);
        frame.add(russianField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        JButton translateToEnglishButton = new JButton("Перевести на английский");
        frame.add(translateToEnglishButton, gbc);

        // Поле для ввода английского слова
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(new JLabel("Английское слово:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JTextField englishField = new JTextField(15);
        frame.add(englishField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        JButton translateToRussianButton = new JButton("Перевести на русский");
        frame.add(translateToRussianButton, gbc);

        translateToEnglishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String russianWord = russianField.getText();
                    String translatedWord = translateToEnglish(russianWord);
                    englishField.setText(translatedWord);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                    englishField.setText(ex.getMessage());
                }
            }
        });

        translateToRussianButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String englishWord = englishField.getText();
                    String translatedWord = translateToRussian(englishWord);
                    russianField.setText(translatedWord);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                    russianField.setText(ex.getMessage());
                }
            }
        });

        frame.setVisible(true);
    }

    public static String translateToRussian(String englishWord) throws MalformedURLException, UnsupportedEncodingException {
        String encodedRussianWord = URLEncoder.encode(englishWord, StandardCharsets.UTF_8.toString());
        String url = String.format(address,"",encodedRussianWord);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);

            String answer = EntityUtils.toString(response.getEntity());
            System.out.println(answer);

            //search
            String findStr = "name=\"txt\" value=\"";
            int length = findStr.length();
            int startPosition = answer.indexOf(findStr)+length;
            String shortened = answer.substring(startPosition);
            int endPosition = shortened.indexOf('\"');
            String translation = shortened.substring(0,endPosition);
            System.out.println(translation);
            return translation;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "Error";
        }
    }

    public static String translateToEnglish(String russianWord) throws MalformedURLException, UnsupportedEncodingException {
        String encodedRussianWord = URLEncoder.encode(russianWord, StandardCharsets.UTF_8.toString());
        String url = String.format(address,encodedRussianWord,"");
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpClient.execute(httpGet);

            String answer = EntityUtils.toString(response.getEntity());
            System.out.println(answer);
            //search
            String findStr = "\"trans\" value=\"";
            int length = findStr.length();
            int startPosition = answer.indexOf(findStr)+length;
            String shortened = answer.substring(startPosition);
            int endPosition = shortened.indexOf('\"');
            String translation = shortened.substring(0,endPosition);
            System.out.println(translation);
            return translation;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "Error";
        }
    }
/*public void m() {
    BufferedReader bf = null;
    try {
        URLConnection conn = url.openConnection();
        bf = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String str;
        while ((str = bf.readLine()) != null) {
            System.out.println(str);
            sb.append(str);
        }
        String answer = sb.toString();
        String findStr = "\"trans\" value=\"";
        int length = findStr.length();
        int startPosition = answer.indexOf(findStr) + length;
        String shortened = answer.substring(startPosition);
        int endPosition = shortened.indexOf('\"');
        String translation = shortened.substring(0, endPosition);
        System.out.println(translation);
        return translation;
    } catch (Exception ex) {
        ex.printStackTrace();
        return "Error";
    } finally {
        try {
            if (bf != null) {
                bf.close();
            }
        } catch (Exception e) {

        }
    }
}*/
}
