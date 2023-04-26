module com.example.airman {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.airman to javafx.fxml;
    exports com.example.airman;
}