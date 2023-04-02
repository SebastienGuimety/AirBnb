module com.example.airbnb {
    requires javafx.controls;
    requires javafx.fxml;
    requires commons.csv;
    requires com.jfoenix;
    requires com.opencsv;


    opens com.example.airbnb to javafx.fxml;
    exports com.example.airbnb;
    exports com.example.airbnb.controller;
    opens com.example.airbnb.controller to javafx.fxml;
    exports com.example.airbnb.models;
    opens com.example.airbnb.models to javafx.fxml;
}