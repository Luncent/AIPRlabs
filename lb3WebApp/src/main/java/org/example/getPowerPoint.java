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

import static java.util.Base64.getEncoder;

public class getPowerPoint extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rootPath = getServletContext().getRealPath("/WEB-INF/presentation.pptx");

        Path path = Paths.get(rootPath);
        byte[] bytes = Files.readAllBytes(path);
        String base64bytes = Base64.getEncoder().encodeToString(bytes);

        resp.setContentType("text/plain");
        resp.setContentLength(base64bytes.length());
        try(Writer out = resp.getWriter()){
            out.write(base64bytes);
            out.flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
