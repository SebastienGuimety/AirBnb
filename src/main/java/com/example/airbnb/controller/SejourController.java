package com.example.airbnb.controller;

import com.example.airbnb.models.Sejours;
import com.example.airbnb.models.Session;
import com.example.airbnb.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import com.opencsv.CSVWriter;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class SejourController   {

    @FXML
    private VBox vboxcentre;

    @FXML
    private Label erreur;


    private Sejours sejours;


    private final Session session = Session.getInstance();
    public void setSejour(Sejours sejour) {
        this.sejours = sejour;

        HBox hboxville = new HBox( new Label("Ville : " + sejours.getVille()));
        HBox hboxpays = new HBox(new Label("Pays : " + sejours.getPays()));
        HBox hboxprix = new HBox(new Label("Prix : " + sejours.getPrix()));
        HBox hboxnb = new HBox(new Label("Nombres de passagers max : " + sejours.getNbPassagers()));
        HBox hboxhote = new HBox(new Label("Hote : " + sejours.getNomHote()));
        HBox dated = new HBox(new Label("Date debut sejour : " + sejours.getDatedebut()));
        HBox datef = new HBox(new Label("date fin sejour : " + sejours.getDatefin()));
        HBox reserver = new HBox(new Label("Reservé ? : " + sejours.getReserver()));
        HBox avis = new HBox(new Label("Note  : " + sejours.getReserver() + "/5"));

        vboxcentre.getChildren().addAll(hboxville,hboxpays,hboxprix,hboxnb,hboxhote,dated, datef,reserver,avis);


    }

    public void reserver(ActionEvent e) throws IOException {
        if (session != null && session.isLoggedIn()) {
            User currentUser = session.getCurrentUser();
            // Si l'utilisateur est connecté, on affiche les éléments liés à la connexion

            String csvFilePath = "src/main/java/com/example/airbnb/views/reservation.csv";
            String currentPath = Paths.get("").toAbsolutePath().toString();
            FileReader reader = new FileReader(csvFilePath);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            boolean trouve = false;
            for (CSVRecord record : csvParser) {

                // Récupérer les valeurs de chaque colonne en utilisant leur index
                String Ville = record.get(0);
                String Pays = record.get(1);
                String Prix = record.get(2);
                //String Sejour = record.get(3);
                String Hote = record.get(3);
                String nbPassagers = record.get(4);
                String datedebut = record.get(5);
                String datefin = record.get(6);
                String status = record.get(7);
                String id = record.get(8);



                System.out.println("bon l'id c quoi : " + id.toString());


                if (Objects.equals(sejours.getId(), id) ){
                    erreur.setText("Sejour deja reservé ");
                    trouve = true;
                }


            }
            csvParser.close();
            reader.close();


            if (!trouve){
                System.out.println("ON PASSE PAR LA POUR ECRIRE OU PAS APRES LE BREAK");

                // Chemin vers le fichier CSV existant
                String csvFilep = "src/main/java/com/example/airbnb/views/reservation.csv";

                // Création des données à ajouter (ici, 4 colonnes)
                List<String> data = Arrays.asList(sejours.getVille(),sejours.getPays(),sejours.getPrix(),sejours.getIdHote(),currentUser.getId(), sejours.getNbPassagers(),sejours.getDatedebut(),sejours.getDatefin(),"Panier",sejours.getId());

                // Création du FileWriter en mode ajout
                FileWriter writer = new FileWriter(csvFilep, true);

                // Création du CSVPrinter
                CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);

                // Écriture de la ligne CSV
                printer.printRecord(data);

                // Fermeture du CSVPrinter et du FileWriter
                printer.close();
                writer.close();

                erreur.setText("Votre demande de sejour est bien envoyé à l'hote");
            }

        } else {
            erreur.setText("Veuillez vous connecter pour reserver !");
        }

    }

    public void quit(ActionEvent e) {
        final Node source = (Node) e.getSource();
        // Récupère le Stage de la scène actuelle
        final Stage stage = (Stage) source.getScene().getWindow();

        stage.close();
    }
}

// ...
