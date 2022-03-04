module com.example.librarian {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;

    opens com.example.librarian to javafx.fxml;
    exports com.example.librarian;
}