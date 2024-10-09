package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class getTranslationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            String russianWord = req.getParameter("txt");
            String englishWord = req.getParameter("trans");

            String translatedEnWord = null;
            String translatedRuWord = null;
            if (russianWord != null && russianWord.isEmpty() && englishWord != null && !englishWord.isEmpty()) {
                System.out.println("translating en word " + englishWord.length());
                translatedRuWord = DB.translateEnglishWord(englishWord);
            } else if (englishWord != null && englishWord.isEmpty() && russianWord != null & !russianWord.isEmpty()) {
                System.out.println("translating ru word " + russianWord.length());
                translatedEnWord = DB.translateRussianWord(russianWord);
            }

            System.out.println("nothing to translate");

            req.setAttribute("translatedEnWord", translatedEnWord);
            req.setAttribute("translatedRuWord", translatedRuWord);
            req.getRequestDispatcher("index.jsp").forward(req, resp);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

}
