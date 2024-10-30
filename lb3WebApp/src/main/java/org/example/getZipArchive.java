package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class getZipArchive extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String presentationPath = getServletContext().getRealPath("/WEB-INF/presentation.pptx");
        String mp3Path = getServletContext().getRealPath("/WEB-INF/sample4.mp3");
        resp.setContentType("application/zip");

        try(ZipOutputStream zos = new ZipOutputStream(resp.getOutputStream())){
            addFileToArchive(zos, presentationPath);
            addFileToArchive(zos, mp3Path);
            zos.flush();
        }
    }


    private void addFileToArchive(ZipOutputStream zos, String filePath){
        File file = new File(filePath);
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file))) {
            byte[] buffer = new byte[1024];
            ZipEntry zipFile = new ZipEntry(file.getName());
            int length;
            zos.putNextEntry(zipFile);
            while((length = bis.read(buffer))>0){
                zos.write(buffer,0,length);
            }
            zos.closeEntry();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
