module com.example.airbnb {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires commons.csv;
    requires com.jfoenix;

    opens com.example.airbnb to javafx.fxml;
    opens com.example.airbnb.vue to javafx.fxml;
    exports com.example.airbnb;
    exports com.example.airbnb.controller;
    opens com.example.airbnb.controller to javafx.fxml;
}