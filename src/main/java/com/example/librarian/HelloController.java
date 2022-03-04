package com.example.librarian;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloController {

    @FXML
    Label labelLogin;
    @FXML
    Button loginButton;
    @FXML
    TextField usernameInput;
    @FXML
    PasswordField passwordInput;
    @FXML
    RadioButton adminButton;
    @FXML
    RadioButton libraryButton;



    @FXML
    protected void onLoginButtonClick() throws IOException {
        verifyUser();
    }

    private void verifyUser() throws IOException {
        if (usernameInput.getText().isEmpty() && passwordInput.getText().isEmpty()){
            labelLogin.setText("No fields are filled, Please try again!");
            //correct username, empty pass
        }
        else if(adminButton.isSelected()){
            Connection conn = sqlUtils.getConnection();
            String query = "SELECT username, password FROM admins WHERE username = '" + usernameInput.getText() + "' AND password = '" + passwordInput.getText() + "';";
            Statement st;
            ResultSet rs;

            try{
                st = conn.createStatement();
                rs = st.executeQuery(query);

                if(rs.next()){
                    AdminController.changeScene();
                }
                else{
                    labelLogin.setText("Username or Password Incorrect");
                }
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if(libraryButton.isSelected()){
            Connection conn = sqlUtils.getConnection();
            String query = "SELECT username, password FROM librarianLogins WHERE username = '" + usernameInput.getText() + "' AND password = '" + passwordInput.getText() + "';";
            Statement st;
            ResultSet rs;

            try{
                st = conn.createStatement();
                rs = st.executeQuery(query);

                if(rs.next()){
                    LibrarianController.changeScene();
                }
                else{
                    labelLogin.setText("Username or Password Incorrect");
                }
                conn.close();
            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onBackButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarianApplication.class.getResource("welcomePage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750,500); // scene
        Stage stage = LibrarianApplication.getPrimaryStage();
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();

    }

}