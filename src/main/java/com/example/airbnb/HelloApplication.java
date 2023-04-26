package com.example.airbnb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import com.example.airbnb.controller.*;
import com.example.airbnb.models.*;
import java.io.IOException;
import java.io.FileReader;
import java.io.IOException;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.nio.file.Paths;

public class HelloApplication extends Application {
    private double x, y;


	public static void main(String[] args) throws IOException {

		launch();
	}

	@Override
	public void start(Stage stage) throws IOException {
		/*FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("vue/Home.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		stage.setTitle("Hello!");
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();*/
		 Parent root = FXMLLoader.load(getClass().getResource("vue/hello-view.fxml"));
	        stage.setScene(new Scene(root));
	        //set stage borderless
	        stage.initStyle(StageStyle.UNDECORATED);

	        //drag it here
	        root.setOnMousePressed(event -> {
	            x = event.getSceneX();
	            y = event.getSceneY();
	        });
	        root.setOnMouseDragged(event -> {

	        	stage.setX(event.getScreenX() - x);
	        	stage.setY(event.getScreenY() - y);

	        });
	        stage.show();
	}

}