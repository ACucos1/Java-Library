package com.example.librarian;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.io.*;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class Report{

    private int counter = 0;

    Array[] arrayList = { };

    @FXML
    Label reportText, registeredLabel, nameLabel;


    @FXML
    private void createButton() throws IOException {
        Connection conn = sqlUtils.getConnection();
        String query = "SELECT * FROM librarians;";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            File f = new File("Librarians.txt");
            PrintWriter pw = new PrintWriter(f);

            while(rs.next()){
                pw.println(rs.getInt("librarianID") + "\t" + rs.getString("firstname") + "\t" +  rs.getString("lastname")
                        + "\t" +  rs.getString("libName") + "\t" +  rs.getString("date") + "\t" +  rs.getInt("phoneNumber")
                        + "\t" +  rs.getString("address") + "\t" +  rs.getString("emailAddress"));
                counter++;
            }
            pw.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        registeredLabel.setText(String.valueOf(counter));

    }
}




