package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class getImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filePath = getServletContext().getRealPath("/WEB-INF/waves.jpg");
        File img = new File(filePath);
        resp.setContentType("image/jpeg");
        resp.setContentLength((int)img.length());
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(img));
            BufferedOutputStream bos = new BufferedOutputStream(resp.getOutputStream())){
            byte[] bytes = new byte[4096];
            int length;
            while((length=bis.read(bytes))>0){
                bos.write(bytes,0,length);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
