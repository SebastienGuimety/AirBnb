package com.example.airbnb.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    
    protected void checkUser() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
    
}