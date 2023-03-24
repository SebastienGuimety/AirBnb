package com.example.airbnb;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
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

	public static void main(String[] args) throws IOException {

		launch();
	}
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

	public void checkUser(String login, String password) throws IOException {
		String csvFilePath = "src/main/java/com/example/airbnb/data/user.csv";
		String currentPath = Paths.get("").toAbsolutePath().toString();
		FileReader reader = new FileReader(csvFilePath);
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

		for (CSVRecord record : csvParser) {

			// Récupérer les valeurs de chaque colonne en utilisant leur index
			String nom = record.get(0);
			String prenom = record.get(1);
			String login_= record.get(2);
			String password_ = record.get(3);
			boolean trouve =false;
			if(login.equals(login_)) {
				
				if(password.equals(password_)) {
					trouve=true;
					System.out.println("User trouvé");
				}
				else {
					System.out.println("Mot de passe ou identifiant sont incorrects!!");
				}
				
			}


		}
		csvParser.close();
		reader.close();
	}

	

    
}