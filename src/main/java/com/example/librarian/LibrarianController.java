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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class LibrarianController implements Initializable {
    @FXML
    private Label lblCount;
    @FXML
    private Button btnDownload;
    @FXML
    private Label booksError;
    @FXML
    private Label lblRentalsError;
    @FXML
    private TableView<Rentals> tvRentals;
    @FXML
    private TableColumn<Rentals, Integer> tcStudentID;
    @FXML
    private TableColumn<Rentals, Integer> tcRentISBN;
    @FXML
    private TextField tfStudentID;
    @FXML
    private TextField tfRentISBN;
    @FXML
    private Button btnRent;
    @FXML
    private Button btnReturn;
    @FXML
    private TableColumn<Books, String> tcType;
    @FXML
    private TextField tfType;
    @FXML
    private TableColumn<Books, Integer> tcCopies;
    @FXML
    private TextField tfCopies;
    @FXML
    private TextField tfISBN;
    @FXML
    private TextField tfTitle;
    @FXML
    private TextField tfAuthor;
    @FXML
    private TextField tfYear;
    @FXML
    private TextField tfPages;
    @FXML
    private TableView<Books> tvBooks;
    @FXML
    private TableColumn<Books, Integer> tcISBN;
    @FXML
    private TableColumn<Books, String> tcTitle;
    @FXML
    private TableColumn<Books, String> tcAuthor;
    @FXML
    private TableColumn<Books, Integer> tcYear;
    @FXML
    private TableColumn<Books, Integer> tcPages;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;


    @Override
    public void initialize(URL url, ResourceBundle rb){
        showBooks();
        showRentals();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {

        if(event.getSource() == btnAdd){
            System.out.println("Add btn Clicked!");
            addBook();
        }
        else if(event.getSource() == btnUpdate){
            System.out.println("Update btn Clicked!");
            updateBook();
        }
        else if(event.getSource() == btnDelete){
            System.out.println("Delete btn Clicked!");
            deleteBook();
        }
        else if(event.getSource() == btnRent){
            System.out.println("Rent btn Clicked!");
            addRental();
        }
        else if(event.getSource() == btnReturn){
            System.out.println("Return btn Clicked!");
            returnRental();
        }
        else if(event.getSource() == btnDownload){
            System.out.println("Download btn Clicked!");
            downloadCatalog();
        }

    }

    @FXML
    private void handleMouseAction(MouseEvent event) {
        if(tvBooks.getSelectionModel().getSelectedItem() != null){
            Books book = tvBooks.getSelectionModel().getSelectedItem();

            tfISBN.setText("" + book.getIsbn());
            tfType.setText(book.getType());
            tfTitle.setText(book.getTitle());
            tfAuthor.setText(book.getAuthor());
            tfYear.setText("" + book.getYear());
            tfPages.setText("" + book.getPages());
            tfCopies.setText("" + book.getCopies());
        }

        if(tvRentals.getSelectionModel().getSelectedItem() != null){
            Rentals rental = tvRentals.getSelectionModel().getSelectedItem();

            tfStudentID.setText("" + rental.getStudentID());
            tfRentISBN.setText("" + rental.getIsbn());
        }


    }

    private void clearFields(){
        tfISBN.clear();
        tfType.clear();
        tfTitle.clear();
        tfAuthor.clear();
        tfYear.clear();
        tfPages.clear();
        tfCopies.clear();
    }

    public Connection getConnection() {
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

    public ObservableList<Books> getBooksList() {
        ObservableList<Books> bookList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM books";

        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Books book;
            while(rs.next()){
                book = new Books(rs.getInt("isbn"), rs.getString("type"), rs.getString("title"),
                        rs.getString("author"), rs.getInt("year"), rs.getInt("pages"), rs.getInt("copies"));
                bookList.add(book);
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        return bookList;
    }

    private void downloadCatalog(){
        Connection conn = getConnection();
        String query = "SELECT * FROM books;";

        Statement st;
        ResultSet rs;

        try {
            st = conn.createStatement();
            rs = st.executeQuery(query);
            File f = new File("catalog.txt");
            PrintWriter pw = new PrintWriter(f);

            while(rs.next()){
                pw.println(rs.getInt("isbn") +  "\t" + rs.getString("type") + "\t" + rs.getString("title")
                        + "\t" + rs.getString("author") + "\t" + rs.getInt("year") + "\t" + rs.getInt("pages") + "\t" + rs.getInt("copies"));
            }
            pw.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showBooks(){
        ObservableList<Books> list = getBooksList();

        tcISBN.setCellValueFactory(new PropertyValueFactory<Books, Integer>("isbn"));
        tcType.setCellValueFactory(new PropertyValueFactory<Books, String>("type"));
        tcTitle.setCellValueFactory(new PropertyValueFactory<Books, String>("title"));
        tcAuthor.setCellValueFactory(new PropertyValueFactory<Books, String>("author"));
        tcYear.setCellValueFactory(new PropertyValueFactory<Books, Integer>("year"));
        tcPages.setCellValueFactory(new PropertyValueFactory<Books, Integer>("pages"));
        tcCopies.setCellValueFactory(new PropertyValueFactory<Books, Integer>("copies"));

        tvBooks.setItems(list);
        countBooks();
    }

    public void showRentals(){
        ObservableList<Rentals> list = getRentalsList();
        tcRentISBN.setCellValueFactory(new PropertyValueFactory<Rentals, Integer>("isbn"));
        tcStudentID.setCellValueFactory(new PropertyValueFactory<Rentals, Integer>("studentID"));

        tvRentals.setItems(list);
    }


    private ObservableList<Rentals> getRentalsList() {
        ObservableList<Rentals> rentalList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM rentals";

        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            Rentals rental;
            while(rs.next()){
                rental = new Rentals(rs.getInt("isbn"), rs.getInt("studentID"));
                rentalList.add(rental);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        }
        return rentalList;
    }

    private void countBooks(){
        String query = "SELECT COUNT(isbn) AS count FROM books";
        Connection conn = getConnection();
        Statement st;
        ResultSet rs;

        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            int count;
            count = rs.getInt("count");
            lblCount.setText("Number of Items: " + String.valueOf(count));
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void outputBooks() {

    }


    private void addBook(){
        if(validateBooksTextFields()){
            String query = "INSERT INTO books VALUES (" + tfISBN.getText() + ", '" + tfType.getText() + "', '" + tfTitle.getText() + "', '" + tfAuthor.getText() + "', " + tfYear.getText() + ", " + tfPages.getText() + ", " + tfCopies.getText() + ");";
            executeQuery(query);
            showBooks();
            clearFields();
        }
    }

    private void updateBook(){
        if(validateBooksTextFields()){
            String query = "UPDATE books SET type = '" + tfType.getText() + "', title ='" +  tfTitle.getText() + "', author = '" + tfAuthor.getText() + "', year = " + tfYear.getText() + ", pages = " + tfPages.getText() + ", copies = " + tfCopies.getText() + " WHERE isbn = " + tfISBN.getText() + ";";
            executeQuery(query);
            showBooks();
            clearFields();
        }
    }

    private boolean validateBooksTextFields(){
        boolean isValid = true;
        booksError.setText("");
        if(tfISBN.getText() == "" || tfType.getText() == "" || tfTitle.getText() == "" || tfAuthor.getText() == "" || tfYear.getText() == "" || tfPages.getText() == "" || tfCopies.getText() == ""){
            booksError.setText("ERROR: Must enter all values");
            isValid = false;
        }
        else if(!tfISBN.getText().matches("[0-9]+")  || !tfYear.getText().matches("[0-9]+") || !tfPages.getText().matches("[0-9]+") || !tfCopies.getText().matches("[0-9]+")){
            booksError.setText("ERROR: Isbn, Year, Pages & Copies must be integers");
            isValid = false;
        }
        else if(Integer.parseInt(tfCopies.getText()) < 0){
            booksError.setText("ERROR: # of copies must be >= 0");
            isValid = false;
        }

        return isValid;
    }

    private void deleteBook() {
        booksError.setText("");
        if(tfISBN.getText() != "" && tfISBN.getText().matches("[0-9]+")){
            String query = "DELETE FROM books WHERE isbn = " + tfISBN.getText() + "";
            executeQuery(query);
            showBooks();
            clearFields();
        }
        else{
            booksError.setText("ERROR: ISBN cannot must be not empty and an Integer");
        }
    }

    private boolean validateRentals(){
        boolean isValid = true;
        lblRentalsError.setText("");
        if(tfRentISBN.getText() == "" || tfStudentID.getText() == ""){
            lblRentalsError.setText("ERROR: Fields cannot be empty.");
            isValid = false;
        }
        else if(!tfRentISBN.getText().matches("[0-9]+")  || !tfStudentID.getText().matches("[0-9]+")){
            lblRentalsError.setText("ERROR: ISBN and StudentID must be integers");
            isValid = false;
        }

        return isValid;
    }

    private void addRental() {
        if(validateRentals()){
            if(checkCopiesForRent(tfRentISBN.getText(), true)){
                String query = "INSERT INTO rentals(isbn, studentID) VALUES(" + tfRentISBN.getText() + ", " + tfStudentID.getText() + ");";
                executeQuery(query);
                showBooks();
                showRentals();
                clearRentFields();
            }
        }

    }

    private boolean checkCopiesForRent(String isbn, boolean renting) {
        boolean success = true;
        Connection conn = getConnection();
        String query = "SELECT copies FROM books WHERE isbn = " + isbn + ";";

        Statement st;
        Statement rentalsStatement;
        ResultSet rs;
        ResultSet rentalsISBN;

        try{
            st = conn.createStatement();
            rentalsStatement = conn.createStatement();
            rs = st.executeQuery(query);
            rentalsISBN  = rentalsStatement.executeQuery("SELECT isbn FROM rentals WHERE isbn = " + isbn + ";");

            int copies;
            copies = rs.getInt("copies");
            if(renting && copies > 0){//Rent
                st.executeUpdate("UPDATE books SET copies = " + String.valueOf(copies-1)  + " WHERE isbn = " + isbn + ";");

            }
            else if(!renting && rentalsISBN.next()){ //Return
                st.executeUpdate("UPDATE books SET copies = " + String.valueOf(copies+1)  + " WHERE isbn = " + isbn + ";");
            }
            else {
                success = false;
            }
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            success = false;
        }

        return success;
    }


    private void returnRental(){
        if(validateRentals()){
            if(checkCopiesForRent(tfRentISBN.getText(), false)){
                String query = "DELETE FROM rentals WHERE isbn = " + tfRentISBN.getText() + " AND studentID = " + tfStudentID.getText() + ";";
                executeQuery(query);
                showBooks();
                showRentals();
                clearFields();
            }

        }
    }


    private void clearRentFields() {
        tfRentISBN.clear();
        tfStudentID.clear();
    }


    private void executeQuery(String query) {
        Connection conn = getConnection();
        Statement st;
        try{
            st = conn.createStatement();
            st.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println(query);
            System.out.println(e.getMessage());
//            e.printStackTrace();
        }
    }

    public static void changeScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarianApplication.class.getResource("librarian-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1210,790); // scene

        Stage stage = LibrarianApplication.getPrimaryStage();
        stage.setTitle("Library Management System");
        stage.setScene(scene);
        stage.show();
    }
}
