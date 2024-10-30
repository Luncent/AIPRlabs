package org.example;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PowerPointTask {
    private static final String ADDRESS = "http://localhost:8080/lb3WebApp-1.0/getZipArchive";
    private static final String ZIP_PATH = "archive.zip";

    public static void main(String[] args) throws IOException, InterruptedException {
        getZipArchive();
        List<String> fileNames = unzip();
        for(String fileName : fileNames){
            if(fileName.contains(".pptx")){
                new Thread(()-> {
                    try {
                        openPresentation(fileName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
            }
            else if(fileName.contains(".mp3")){
               new Thread(()->playMP3(fileName)).start();
            }
        }

    }

    private static void playMP3(String filePath){
        try (FileInputStream fis = new FileInputStream(filePath)) {
            Player player = new Player(fis);
            player.play();
        } catch (JavaLayerException | IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> unzip(){
        List<String> fileNames = new ArrayList<>();
        File dir = new File(Paths.get("").toAbsolutePath().toString());
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(ZIP_PATH))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File newFile = new File(dir, zipEntry.getName());
                fileNames.add(zipEntry.getName());
                try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile))) {
                    byte[] buffer = new byte[4096]; // Увеличенный размер буфера
                    int length;
                    while ((length = zis.read(buffer)) > 0) {
                        bos.write(buffer, 0, length); // Записываем только прочитанные байты
                    }
                }
                zis.closeEntry();
            }
            return fileNames;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void openPresentation(String fileName) throws IOException, InterruptedException {
        File file = new File(fileName);

        String command = "C:\\Program Files\\Microsoft Office\\root\\Office16\\POWERPNT.EXE";
        ProcessBuilder builder = new ProcessBuilder(command,file.getAbsolutePath());
        Process process = builder.start();
        if(process.waitFor()==0){
            System.out.println(" reading presentation completed");
        }
        else{
            System.out.println("smthnig went wrong with presentation");
        }
    }

    public static void getZipArchive() throws MalformedURLException, UnsupportedEncodingException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(ADDRESS);
            HttpResponse response = httpClient.execute(httpGet);

            byte[] bytes = EntityUtils.toByteArray(response.getEntity());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(ZIP_PATH));
            try {
                bos.write(bytes);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
