package com.example.airbnb.controller;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

import com.example.airbnb.models.Session;
import com.example.airbnb.models.User;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
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
    Button connecter;
    @FXML
    TextField login;
    @FXML
    TextField password;
    @FXML
    Label erreur;



    private HelloController helloController;

    // Méthode pour passer la référence au contrôleur de la page Home
    public void setHomeController(HelloController helloController) {
        this.helloController = helloController;
    }

    public void connexion(ActionEvent e) throws IOException, InterruptedException {

        System.out.println(" je suis dans connexion!!");
        String csvFilePath = "src/main/java/com/example/airbnb/views/user.csv";
        String currentPath = Paths.get("").toAbsolutePath().toString();
        FileReader reader = new FileReader(csvFilePath);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        boolean trouve = false;
        for (CSVRecord record : csvParser) {

            // Récupérer les valeurs de chaque colonne en utilisant leur index
            String id = record.get(0);
            String nom = record.get(1);
            String prenom = record.get(2);
            String login_file = record.get(3);
            String password_file = record.get(4);


            System.out.println("file login is " + login_file);
            if (login_file.equals(login.getText())) {
                trouve = true;
                if (password_file.equals(password.getText())) {

                    erreur.setText("User trouvé");
                    User user = new User();
                    user.setNom(nom);
                    user.setPrenom(prenom);
                    user.setId(id);
                    Session.getInstance().login(user);

                    //Thread.sleep(5000);



                    final Node source = (Node) e.getSource();
                    // Récupère le Stage de la scène actuelle
                    final Stage stage = (Stage) source.getScene().getWindow();

                    stage.close();


                    // Mettre à jour la page Home
                    helloController.updateHome();

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