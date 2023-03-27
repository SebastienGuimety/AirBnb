package com.example.airbnb.vue;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.example.airbnb.HelloApplication;
import com.jfoenix.controls.JFXButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
	@FXML
	Button loginButton;
	@FXML
	TextField login;
	@FXML
	TextField password;
	@FXML
	Label erreur;

	public void connexion() throws IOException {

		System.out.println(" je suis dans connexion!!");
		String csvFilePath = "src/main/java/com/example/airbnb/data/user.csv";
		String currentPath = Paths.get("").toAbsolutePath().toString();
		FileReader reader = new FileReader(csvFilePath);
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
		boolean trouve = false;
		for (CSVRecord record : csvParser) {

			// Récupérer les valeurs de chaque colonne en utilisant leur index
			String nom = record.get(0);
			String prenom = record.get(1);
			String login_file = record.get(2);
			String password_file = record.get(3);
			System.out.println("file login is " + login_file);
			if (login_file.equals(login.getText())) {
				trouve = true;
				if (password_file.equals(password.getText())) {

					erreur.setText("User trouvé");
					
				} else {
					erreur.setText("Mot de passe incorrect!!");
				}

			}

		}
		if (!trouve)
			erreur.setText("Cet utilisateur n'esxiste pas ");

		csvParser.close();
		reader.close();
	}

}
