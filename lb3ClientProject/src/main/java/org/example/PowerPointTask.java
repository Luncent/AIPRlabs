package org.example;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class PowerPointTask {
    private static final String ADDRESS = "http://localhost:8080/lb3WebApp-1.0/getPowerPoint";
    public static void main(String[] args) throws IOException, InterruptedException {
        getPowerPointFile();
        openFile();
    }

    public static void openFile() throws IOException, InterruptedException {
        File file = new File("presentation.pptx");
        if(file.exists()){
            System.out.println("exists");
        }

        String command = "C:\\Program Files\\Microsoft Office\\root\\Office16\\POWERPNT.EXE";
        ProcessBuilder builder = new ProcessBuilder(command,file.getAbsolutePath());
        Process process = builder.start();
        if(process.waitFor()==0){
            System.out.println("complited");
        }
        else{
            System.out.println("smthnig went wrong");
        }
    }

    public static void getPowerPointFile() throws MalformedURLException, UnsupportedEncodingException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpGet httpGet = new HttpGet(ADDRESS);
            HttpResponse response = httpClient.execute(httpGet);

            String answer = EntityUtils.toString(response.getEntity());
            byte[] filesBytes = new BASE64Decoder().decodeBuffer(answer);
            FileOutputStream fos = new FileOutputStream("presentation.pptx");
            try {
                fos.write(filesBytes);
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
