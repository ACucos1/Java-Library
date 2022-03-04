package com.example.librarian;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;




public class LibrarianApplication extends Application {
    private static Stage primaryStage;

    public static Stage getPrimaryStage() {return primaryStage;}

    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader librarianDashboardView = new FXMLLoader(LibrarianApplication.class.getResource("librarian-view.fxml"));
        FXMLLoader librarianDashboardView = new FXMLLoader(LibrarianApplication.class.getResource("welcomePage.fxml"));


        primaryStage = stage;
        Scene scene = new Scene(librarianDashboardView.load());
        stage.setTitle("Library");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}