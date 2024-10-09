package org.example;

import javax.jws.soap.SOAPBinding;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/db5";
    private static final String PASSWORD ="root";
    private static final String USER = "root";
    private static final String SELECT_RU = "SELECT russian FROM dictionary WHERE english = LOWER(?)";
    private static final String SELECT_EN = "SELECT english FROM dictionary WHERE russian = LOWER(?)";

    public static String translateRussianWord(String word){
        word = word.toLowerCase();
        System.out.println("finding: "+word);
        try(Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement stmtRu = conn.prepareStatement(SELECT_EN)){
            stmtRu.setString(1,word);
            ResultSet rs = stmtRu.executeQuery();
            if(rs.next()) {
                return rs.getString("english");
            }
            else{
                return "";
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "Ошибка с бд";
        }
    }

    public static String translateEnglishWord(String word){
        word = word.toLowerCase();
        System.out.println("finding: "+word);
        try(Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement stmtRu = conn.prepareStatement(SELECT_RU)){
            stmtRu.setString(1,word);
            ResultSet rs = stmtRu.executeQuery();
            if(rs.next()) {
                return rs.getString("russian");
            }
            else{
                return "";
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
            return "Ошибка с бд";
        }
    }

    /*public static void main(String[] args){
        System.out.println(DB.getTranslatedWord("yEs"));
    }*/
}
