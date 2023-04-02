package com.example.airbnb.controller;

import com.example.airbnb.HelloApplication;
import com.example.airbnb.models.Commentaire;
import com.example.airbnb.models.Sejours;
import com.example.airbnb.models.Session;
import com.example.airbnb.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
import java.util.*;

public class SejourController   {

    @FXML
    private VBox vboxcentre;


    @FXML
    private TextField commentfield;


    @FXML
    private Label erreur;


    private Sejours sejours;


    ObservableList<Sejours> sejoursObservableList = FXCollections.observableArrayList();



    private final Session session = Session.getInstance();
    public void setSejour(Sejours sejour) throws IOException {
        this.sejours = sejour;

        List<Commentaire> allcomment = new ArrayList<>();

        HBox hboxville = new HBox(new Label("Ville : " + sejours.getVille()));
        HBox hboxpays = new HBox(new Label("Pays : " + sejours.getPays()));
        HBox hboxprix = new HBox(new Label("Prix : " + sejours.getPrix()));
        HBox hboxnb = new HBox(new Label("Nombres de passagers max : " + sejours.getNbPassagers()));
        HBox hboxhote = new HBox(new Label("Hote : " + sejours.getNomHote()));
        HBox dated = new HBox(new Label("Date debut sejour : " + sejours.getDatedebut()));
        HBox datef = new HBox(new Label("date fin sejour : " + sejours.getDatefin()));
        HBox reserver = new HBox(new Label("Reservé ? : " + sejours.getReserver()));
        HBox avis = new HBox(new Label("Note  : " + sejours.getReserver() + "/5"));
        HBox comm = new HBox(new Label("Commentaires  : " ));

        allcomment = getAllCommentWithId(sejour.getId());

        VBox v = new VBox();
        for (Commentaire commentaires : allcomment) {

            HBox textcomment = new HBox(new Label(commentaires.getNomClient()+ " : "+ commentaires.getCommentaire()));
           // HBox nomClient = new HBox(new Label( commentaires.getNomClient()));
            v.getChildren().addAll(textcomment);
        }


        vboxcentre.getChildren().addAll(hboxville, hboxpays, hboxprix, hboxnb, hboxhote, dated, datef, reserver, avis,comm ,v);


    }


    public List<Commentaire> getAllCommentWithId(String idsejour) throws IOException {

        List<Commentaire> comments = new ArrayList<>();


        String csvFilePath = "src/main/java/com/example/airbnb/views/commentaires.csv";
        String currentPath = Paths.get("").toAbsolutePath().toString();
        FileReader reader = new FileReader(csvFilePath);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

        for(CSVRecord record : csvParser) {

            // Récupérer les valeurs de chaque colonne en utilisant leur index

            if (Objects.equals(record.get(3), idsejour) ){
                Commentaire comm = new Commentaire(record.get(0),record.get(1),record.get(2),record.get(3));
                //Sejours sej = new Sejours(record.get(0), record.get(1), record.get(2), record.get(3), record.get(4), record.get(5), record.get(6), record.get(7), record.get(8),record.get(9), record.get(10) );
                //Label label = new Label(record.get(0) + " "+ record.get(1) + " "+5+ " "+record.get(3)+ " "+record.get(4)+" "+ 87);
                comments.add(comm);
            }

        }

        csvParser.close();
        reader.close();

        return comments;
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




    public static void writeLine(String commentaire, String idSejour, String idClient, String nomClient) throws IOException {
        CSVWriter writer = new CSVWriter(new FileWriter("src/main/java/com/example/airbnb/views/commentaires.csv", true),',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

        String[] line = {commentaire,idClient, nomClient, idSejour};
        writer.writeNext(line);

        writer.close();
    }

    public void updateHome() throws IOException {
        // créer une nouvelle instance du contrôleur de la page home
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("sejour.fxml"));

        // initialiser une nouvelle scène avec la nouvelle instance de contrôleur
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // récupérer la fenêtre de la scène actuelle
        Stage stage = (Stage) commentfield.getScene().getWindow();

        // cacher la fenêtre actuelle et afficher la nouvelle scène
        stage.setScene(scene);
        stage.hide();
        stage.show();

    }


    public void sendcomment(ActionEvent actionEvent) throws IOException {
        if (session != null && session.isLoggedIn()) {
            writeLine(commentfield.getText(), sejours.getId(), session.getCurrentUser().getId(),session.getCurrentUser().getNom());
        } else {
            erreur.setText("Connectez vous pour poster un commentaire");
        }

        //updateHome();

    }

}

// ...
