module com.example.zadanie3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.zadanie3 to javafx.fxml;
    exports com.example.zadanie3;
    exports com.example.zadanie3.model;
    opens com.example.zadanie3.model to javafx.fxml;
}