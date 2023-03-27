package com.example.airbnb.controller;

import com.example.airbnb.models.Sejours;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.jfoenix.controls.*;

public class HelloController {

    @FXML
    private HBox hboxcentre;
    @FXML
    private TableView<Sejours> tableView;

    @FXML
    private  TableColumn<Sejours, String> ville;

    @FXML
    private  TableColumn<Sejours, String> pays;

    @FXML
    private  TableColumn<Sejours, Integer> prix;

    @FXML
    private  TableColumn<Sejours, String> hote;

    @FXML
    private  TableColumn<Sejours, String> nbPassagers;

    @FXML
    private  TableColumn<Sejours, Integer> avis;

    @FXML
    private  ScrollPane scroll;

    @FXML
    private TextField destination, depart, retour;

    @FXML
    private JFXButton submitForm;

    List<Sejours> sejours = new ArrayList<>();
    ObservableList<Sejours> sejoursObservableList = FXCollections.observableArrayList();

    public void getAllSejours() throws IOException {
        String csvFilePath = "src/main/java/com/example/airbnb/views/sejours.csv";
        String currentPath = Paths.get("").toAbsolutePath().toString();
        FileReader reader = new FileReader(csvFilePath);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        for(CSVRecord record : csvParser) {

            // Récupérer les valeurs de chaque colonne en utilisant leur index

            Sejours sej = new Sejours(record.get(0), record.get(1), record.get(2), record.get(3), record.get(4), record.get(5) );
            //Label label = new Label(record.get(0) + " "+ record.get(1) + " "+5+ " "+record.get(3)+ " "+record.get(4)+" "+ 87);
            sejoursObservableList.add(sej);

        }

        csvParser.close();
        reader.close();
    }

    public void printAllSejours(List<Sejours> list ){
        for (Sejours flight : list) {
            // Create a VBox to hold the flight details



            HBox hboxville = new HBox( new Label("Ville : " + flight.getVille()));
            HBox hboxpays = new HBox(new Label("Pays : " + flight.getPays()));
            HBox hboxprix = new HBox(new Label("Prix : " + flight.getPrix()));
            HBox hboxnb = new HBox(new Label("Nombres de passagers max : " + flight.getNbPassagers()));
            HBox hboxhote = new HBox(new Label("Hote : " + flight.getHote()));
            HBox hboxavis = new HBox(new Label("avis : " + flight.getAvis()));


            VBox vboxDetails = new VBox();
            Insets padding = new Insets(50, 0, 0, 0);
            vboxDetails.setPadding(padding);

            // Add each HBox to the VBox
            vboxDetails.getChildren().addAll(hboxville,hboxpays,hboxprix,hboxnb,hboxhote,hboxavis);
            vboxDetails.setUserData(flight);
            vboxDetails.setSpacing(10);
            vboxDetails.setMinWidth(300);

            hboxcentre.getChildren().add(vboxDetails);
            hboxcentre.setAlignment( Pos.CENTER);

            for (Node child : hboxcentre.getChildren()) {
                if (child instanceof VBox vbox) {
                    vbox.getStyleClass().add("vbox-frame");
                    vbox.setOnMouseClicked(event -> {

                        // seulement pour avoir un css pour un clic sur un sejours
                        if (event.getButton() == MouseButton.PRIMARY) {
                            for (Node v : hboxcentre.getChildren()) {
                                v.getStyleClass().remove("selected");
                            }
                            vbox.getStyleClass().add("selected");
                        }


                        // Code à exécuter lorsque la VBox est cliquée
                        // Par exemple, afficher des informations supplémentaires sur le voyage
                        Sejours voyage = (Sejours) vbox.getUserData();



                        System.out.println(voyage.getPrix());
                    });
                }
            }


        }

    }



    public void initialize() throws IOException {

        getAllSejours();

        destination.textProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.length() >= 2) {
                // Effectuer la recherche ici

                // Filter flights based on search text
                List<Sejours> filteredFlights = sejoursObservableList.stream()
                        .filter(flight -> flight.getVille().contains(newValue)
                                || flight.getHote().contains(newValue)
                                || flight.getPays().contains(newValue)
                                || flight.getPrix().contains(newValue)).toList();


                // supprimer tous les voyages
                hboxcentre.getChildren().clear();
                // puis afficher les nouveaux avec la list bien filtrée
                printAllSejours(filteredFlights);

            } else {
                printAllSejours(sejoursObservableList);

            }

        });

        // print all the sejours in the first load of the view
        printAllSejours(sejoursObservableList);


    }


    public void submitForm(ActionEvent actionEvent) {

        List<Sejours> sejourstrie = new ArrayList<>();

        if ( destination != null || depart != null || retour != null){

        }

    }
}