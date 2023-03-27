package com.example.airbnb.vue;

import java.io.IOException;

import com.example.airbnb.HelloApplication;
import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Controll {

	@FXML
	JFXButton connexion;
	

	public void seConnecter() throws IOException {
		 Stage newStage = new Stage();
		  FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("vue/login.fxml"));
	      Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		 newStage.setWidth(300); // définir la largeur de la fenêtre
	     newStage.setHeight(300); // définir la hauteur de la fenêtre
	     newStage.setScene(scene);
	     newStage.show();
		Alert alert = new Alert(null);
	}

}
