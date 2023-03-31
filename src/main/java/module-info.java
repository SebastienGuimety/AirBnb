module com.example.airbnb {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires commons.csv;
    requires com.jfoenix;
	requires javafx.base;

    opens com.example.airbnb to javafx.fxml;
    opens com.example.airbnb.vue to javafx.fxml;
  
    exports com.example.airbnb;
    exports com.example.airbnb.controller;
    exports com.example.airbnb.models;
    opens com.example.airbnb.models to javafx.fxml;
    opens com.example.airbnb.controller to javafx.fxml;
}