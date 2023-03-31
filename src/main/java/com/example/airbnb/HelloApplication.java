package com.example.airbnb;

import com.example.airbnb.controller.HelloController;
import com.example.airbnb.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader firstPaneLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        Parent firstPane = firstPaneLoader.load();
        Scene firstScene = new Scene(firstPane);


        /// Recuperation de la scene Humain vs Humain
        FXMLLoader loginPageLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent login = loginPageLoader.load();
        Scene loginScene = new Scene(login);




        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));

        stage.setTitle("Hello!");
        stage.setScene(firstScene);
        stage.setMaximized(true);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }
}