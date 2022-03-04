package com.example.librarian;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomePage {


//    @FXML
//    Button nextPage;


    @FXML
    protected void nextPage () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarianApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750,500); // scene
        Stage stage = LibrarianApplication.getPrimaryStage();
        stage.setTitle("Library Management System (Admin Detail)");
        stage.setScene(scene);
        stage.show();
        //switch to new FXML
    }

    @FXML
    protected void nextPageStudent () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarianApplication.class.getResource("studentSearch.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750,500); // scene
        Stage stage = LibrarianApplication.getPrimaryStage();
        stage.setTitle("Library Management System (Admin Detail)");
        stage.setScene(scene);
        stage.show();
        //switch to new FXML
    }


}
