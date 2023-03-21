module com.example.airbnb {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.airbnb to javafx.fxml;
    exports com.example.airbnb;
    exports com.example.airbnb.controller;
    opens com.example.airbnb.controller to javafx.fxml;
}