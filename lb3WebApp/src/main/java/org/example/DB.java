package org.example;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
    public DB() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }
    private static final String URL = "jdbc:mysql://localhost:3306/db5";
    private static final String PASSWORD ="root";
    private static final String USER = "root";
    private static final String SELECT = "SELECT * FROM dictionary WHERE LOWER(russian) = LOWER(?) OR english = LOWER(?)";

    public String getTranslatedWord(String word){
        word = word.toLowerCase();
        try(Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement stmt = conn.prepareStatement(SELECT)){
            stmt.setString(1,word);
            stmt.setString(2,word);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            String russian = rs.getString("russian").toLowerCase();
            String english = rs.getString("english").toLowerCase();
            if(russian.equals(word)){
                return english;
            }
            else return russian;
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "Ошибка с бд";
        }
    }

    public static void main(String[] args){
        System.out.println(DB.getTranslatedWord("yEs"));
    }
}
