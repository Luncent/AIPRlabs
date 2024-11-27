package org.example;

import javax.print.attribute.standard.DateTimeAtCreation;
import java.io.*;

public class tests {
    public static void main(String[] args){
        long timeStart = System.currentTimeMillis();
        String path = "archive.zip";
        File file = new File(path);
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] bytes = new byte[1024];
            int length;
            while((length=bis.read(bytes))>0){

            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } ;
        long timeEnd = System.currentTimeMillis();
    }
}
