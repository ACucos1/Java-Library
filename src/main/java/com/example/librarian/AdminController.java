package com.example.librarian;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminController implements Initializable{
    /**
     *
     * <TableColumn fx:id="tcLibName" prefWidth="75.0" text="libName" />
     * <TableColumn fx:id="tcDate" prefWidth="75.0" text="date" />
     * <TableColumn fx:id="tcPhoneNum" prefWidth="75.0" text="phoneNumber" />
     * <TableColumn fx:id="tcAddress" prefWidth="75.0" text="address" />
     * <TableColumn fx:id="tcEmail" prefWidth="75.0" text="emailAddress" />
     */
    @FXML
    private TableColumn<Librarian, String> tcEmail;
    @FXML
    private TableColumn<Librarian, String> tcAddress;
    @FXML
    private TableColumn<Librarian, Integer> tcPhoneNum;
    @FXML
    private TableColumn<Librarian, String> tcDate;
    @FXML
    private TableColumn<Librarian, String> tcLibName;
    @FXML
    private TableColumn<Librarian, String> tcLname;
    @FXML
    private TableColumn<Librarian, String> tcFname;
    @FXML
    private TableColumn<Librarian, Integer> tcLibID;
    @FXML
    private TextField firstName, lastName, libName, date, phoneNumber, address, emailAddress;
    @FXML
    private Label firstNameLabel, lastNameLabel, libraryLabel ,phoneLabel, addressLabel, emailLabel;
    @FXML
    private TableView<Librarian> listView;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        showLibrarians();
//        try {
////            tableData();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        //listView.getItems().addAll(data);
    }


    @FXML
    protected void add() throws IOException {
        String query = "INSERT INTO librarians(firstname, lastname, libName, date, phoneNumber, address, emailAddress) VALUES('"+ firstName.getText() + "', '"
                        + lastName.getText() + "', '" + libName.getText() + "', '" + date.getText() + "', " + phoneNumber.getText() + ", '" + address.getText() + "', '" + emailAddress.getText() + "');";
        sqlUtils.executeQuery(query);
        showLibrarians();
    }



    @FXML
    protected void delete() throws IOException
    {
        String query = "DELETE FROM librarians WHERE firstname = '" + firstName.getText() +  "' AND lastname = '" + lastName.getText() + "' AND emailAddress = '" + emailAddress.getText() + "';";
        sqlUtils.executeQuery(query);
        showLibrarians();
    }

    public static ArrayList<String> listFilesForFolder(final File folder) throws IOException {
        ArrayList<String> arrayList = new ArrayList<>();
        for (final File fileEntry : Objects.requireNonNull(folder.listFiles())) {
            String read = Files.readAllLines(Paths.get(fileEntry.getPath())).get(0);
            arrayList.add(read.strip());
        }
        return arrayList;
    }

    public void tableData() throws IOException {
//        Path p = Paths.get("src/main/librarianData");
//        final File folder = new File(String.valueOf(p));
//        ArrayList<String> al = listFilesForFolder(folder);
//        listView.getItems().clear();
//        for(String detail: al) {
//            listView.getItems().add(detail);
//        }
//        listView.refresh();
    }

    public ObservableList<Librarian> getTableData() {
        ObservableList<Librarian> librarianList = FXCollections.observableArrayList();
        Connection conn = sqlUtils.getConnection();
        String query = "SELECT * FROM librarians";

        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Librarian librarian;
            while(rs.next()){
                librarian = new Librarian(rs.getInt("librarianID"), rs.getString("firstname"), rs.getString("lastname"),
                        rs.getString("libName"), rs.getString("date"), rs.getInt("phoneNumber"), rs.getString("address"), rs.getString("emailAddress"));
                librarianList.add(librarian);
            }
            conn.close();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return librarianList;
    }

    public void showLibrarians(){
        ObservableList<Librarian> librarianList = getTableData();

        tcLibID.setCellValueFactory(new PropertyValueFactory<Librarian, Integer>("librarianID"));
        tcFname.setCellValueFactory(new PropertyValueFactory<Librarian, String>("firstName"));
        tcLname.setCellValueFactory(new PropertyValueFactory<Librarian, String>("lastName"));
        tcLibName.setCellValueFactory(new PropertyValueFactory<Librarian, String>("libName"));
        tcDate.setCellValueFactory(new PropertyValueFactory<Librarian, String>("date"));
        tcPhoneNum.setCellValueFactory(new PropertyValueFactory<Librarian, Integer>("phoneNumber"));
        tcAddress.setCellValueFactory(new PropertyValueFactory<Librarian, String >("address"));
        tcEmail.setCellValueFactory(new PropertyValueFactory<Librarian, String >("email"));

        listView.setItems(librarianList);

    }

    @FXML
    protected void detail () {

        if(listView.getSelectionModel().getSelectedItem() != null){
            Librarian librarian = listView.getSelectionModel().getSelectedItem();

            //TextFields
            firstName.setText("" + librarian.getFirstName());
            lastName.setText(librarian.getLastName());
            libName.setText(librarian.getLibName());
            date.setText(librarian.getDate());
            phoneNumber.setText("" + librarian.getPhoneNumber());
            address.setText("" + librarian.getAddress());
            emailAddress.setText("" + librarian.getEmail());

            //Labels
            firstNameLabel.setText("First Name: " + librarian.getFirstName());
            lastNameLabel.setText("Last Name: " + librarian.getLastName());
            libraryLabel.setText("Library: " + librarian.getLibName());
            phoneLabel.setText("Phone #: " + librarian.getPhoneNumber());
            addressLabel.setText("Address: " + librarian.getAddress());
            emailLabel.setText("Email: " + librarian.getEmail());
        }
    }

    @FXML
    private void onBackButtonClick() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarianApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750,500); // scene
        Stage stage = LibrarianApplication.getPrimaryStage();
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void generateReport() throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(LibrarianApplication.class.getResource("report.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750,500); // scene
        Stage stage = LibrarianApplication.getPrimaryStage();
        stage.setTitle("Library Management System (Generate Report)");
        stage.setScene(scene);
        stage.show();

    }


    public static void changeScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarianApplication.class.getResource("admin.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750,500); // scene

        Stage stage = LibrarianApplication.getPrimaryStage();
        stage.setTitle("Librarian Management System");
        stage.setScene(scene);
        stage.show();
    }


}

