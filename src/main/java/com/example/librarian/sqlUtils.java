package com.example.librarian;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class sqlUtils {
    public static void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
            conn.close();
        } catch (SQLException e) {
            System.out.println(query);
            System.out.println(e.getMessage());
//            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        Connection con;
        try{
            con = DriverManager.getConnection("jdbc:sqlite:./src/books.db");
            return con;
        }
        catch (SQLException ex){
            System.out.println("Error"+ ex.getMessage());
            return null;
        }
    }

}
