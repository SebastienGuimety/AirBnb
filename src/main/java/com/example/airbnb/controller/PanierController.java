package com.example.airbnb.controller;

import com.example.airbnb.HelloApplication;
import com.example.airbnb.models.Sejours;
import com.example.airbnb.models.Session;
import com.example.airbnb.models.User;
import com.jfoenix.controls.JFXButton;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class PanierController implements Initializable {
    @FXML
    private VBox vboxcentre;

    @FXML
    private HBox hboxcentre,hboxcentre1;

    @FXML
    private ScrollPane scroll,scroll1;

    @FXML
    private Label erreur;


    private Sejours sejours;

    //private JFXButton

    ObservableList<Sejours> sejoursObservableListPanier = FXCollections.observableArrayList();
    ObservableList<Sejours> sejoursObservableListAccepteOuAttente = FXCollections.observableArrayList();
    //ObservableList<Sejours> sejoursObservableListAccepte = FXCollections.observableArrayList();

    private final Session session = Session.getInstance();



    public static void deleteLine(String csvFilePath, String id) throws IOException {
        File inputFile = new File(csvFilePath);
        File tempFile = new File("src/main/java/com/example/airbnb/views/temp.csv");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;


        while ((currentLine = reader.readLine()) != null) {
            String[] fields = currentLine.split(",");
            if (!fields[fields.length - 1].equals(id)) {
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }

        writer.close();
        reader.close();
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }

    public static void changeCSVLine(String csvFilePath, String id, int columnIndex, String newValue) throws IOException {
        File inputFile = new File(csvFilePath);
        File tempFile = new File("temp.csv");

        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String currentLine;

        while ((currentLine = reader.readLine()) != null) {
            String[] lineValues = currentLine.split(","); // On sépare les valeurs de la ligne en utilisant la virgule comme délimiteur
            if (lineValues[lineValues.length - 1].equals(id)) { // On compare l'ID recherché avec la dernière colonne de chaque ligne
                // On a trouvé la ligne correspondante
                // Modifier la valeur de la colonne souhaitée :
                lineValues[columnIndex] = newValue;
                // Reconstituer la ligne :
                StringBuilder newLine = new StringBuilder();
                for (String value : lineValues) {
                    newLine.append(value).append(",");
                }
                newLine.deleteCharAt(newLine.length() - 1); // Supprimer la dernière virgule
                // Sauvegarder la ligne modifiée :
                writer.write(newLine.toString() + System.getProperty("line.separator"));
            } else {
                writer.write(currentLine + System.getProperty("line.separator"));
            }
        }

        writer.close();
        reader.close();
        inputFile.delete();
        tempFile.renameTo(inputFile);
    }




    public void printAllSejours(List<Sejours> list ){
        for (Sejours sejour : list) {
            // Create a VBox to hold the flight details

            HBox hboxville = new HBox(new Label("Ville : " + sejour.getVille()));
            HBox hboxpays = new HBox(new Label("Pays : " + sejour.getPays()));
            HBox hboxprix = new HBox(new Label("Prix : " + sejour.getPrix()));
            HBox hboxnb = new HBox(new Label("Nombres de passagers max : " + sejour.getNbPassagers()));
            HBox hboxhote = new HBox(new Label("Hote : " + sejour.getNomHote()));
            JFXButton btn = new JFXButton("Supprimer");
            //HBox hboxavis = new HBox(new Label("avis : " + flight.getAvis()));

            VBox vboxDetails = new VBox();
            Insets padding = new Insets(50, 0, 0, 0);
            vboxDetails.setPadding(padding);

            // Add each HBox to the VBox
            vboxDetails.getChildren().addAll(hboxville, hboxpays, hboxprix, hboxnb, hboxhote, btn);
            vboxDetails.setUserData(sejour);
            vboxDetails.setSpacing(10);
            vboxDetails.setMinWidth(300);

            hboxcentre.getChildren().add(vboxDetails);
            hboxcentre.setAlignment(Pos.CENTER);

            for (Node child : vboxDetails.getChildren()) {
                if (child instanceof JFXButton bouton) {
                    // Code à exécuter lorsque le bouton est cliqué
                    bouton.setUserData(sejour.getId());
                    System.out.println("id du sejour du bouton" + sejour.getId());

                    bouton.setOnAction(event -> {
                        String id = (String) bouton.getUserData();
                        System.out.println("id du sejour du bouton apres le get" + bouton.getUserData());

                        //remove au btn supprimer
                        sejoursObservableListPanier.removeIf(s -> Objects.equals(s.getId(), id));
                        System.out.println("suppression");


                        try {
                            deleteLine("src/main/java/com/example/airbnb/views/reservation.csv",id);
                            updateHome();
                            erreur.setText("Sejour bien supprimer de votre panier");
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                    });
                }
            }
        }
    }


    public void printSejoursAttente(List<Sejours> list ){
        for (Sejours sejour : list) {
            // Create a VBox to hold the flight details

            HBox hboxville = new HBox(new Label("Ville : " + sejour.getVille()));
            HBox hboxpays = new HBox(new Label("Pays : " + sejour.getPays()));
            HBox hboxprix = new HBox(new Label("Prix : " + sejour.getPrix()));
            HBox hboxnb = new HBox(new Label("Nombres de passagers max : " + sejour.getNbPassagers()));
            HBox hboxhote = new HBox(new Label("Hote : " + sejour.getNomHote()));
            Label lab = new Label();
            if(Objects.equals(sejour.getReserver(), "Attente")){
                lab = new Label("En attente");
                lab.setBackground(Background.fill(Color.BEIGE));
            } else {
                lab = new Label("Accepte");
                lab.setBackground(Background.fill(Color.DARKGREEN));
            }


            //HBox hboxavis = new HBox(new Label("avis : " + flight.getAvis()));

            VBox vboxDetails = new VBox();
            Insets padding = new Insets(50, 0, 0, 0);
            vboxDetails.setPadding(padding);

            // Add each HBox to the VBox
            vboxDetails.getChildren().addAll(hboxville, hboxpays, hboxprix, hboxnb, hboxhote, lab);
            vboxDetails.setUserData(sejour);
            vboxDetails.setSpacing(10);
            vboxDetails.setMinWidth(300);

            hboxcentre1.getChildren().add(vboxDetails);
            hboxcentre1.setAlignment(Pos.CENTER);

        }
    }

    public void quit(ActionEvent e) {
        final Node source = (Node) e.getSource();
        // Récupère le Stage de la scène actuelle
        final Stage stage = (Stage) source.getScene().getWindow();

        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (session.isLoggedIn()) {
            User currentUser = session.getCurrentUser();

            System.out.println("il est connecté le boug sur panier");
            // afficher les informations de l'utilisateur


            System.out.println(currentUser.getPrenom());

            String csvFilePath = "src/main/java/com/example/airbnb/views/reservation.csv";
            String currentPath = Paths.get("").toAbsolutePath().toString();
            FileReader reader = null;
            CSVParser csvParser = null;
            try {
                reader = new FileReader(csvFilePath);
                csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            boolean trouve = false;

            for (CSVRecord record : csvParser) {

                // Récupérer les valeurs de chaque colonne en utilisant leur index
                String Ville = record.get(0);
                String Pays = record.get(1);
                String Prix = record.get(2);
                //String Sejour = record.get(3);
                String Hote = record.get(3);
                String Client = record.get(4);
                String nbPassagers = record.get(5);
                String datedebut = record.get(6);
                String datefin = record.get(7);
                String status = record.get(8);
                String id = record.get(9);

                System.out.println("bon l'id c quoi : " + id);


                if (Objects.equals(Client, currentUser.getId()) && Objects.equals("Panier", status) ){
                    Sejours sej = new Sejours(record.get(0), record.get(1), record.get(2), record.get(3), "nomhote", record.get(4), record.get(5), record.get(6), record.get(7),record.get(8), record.get(9) );
                    //Label label = new Label(record.get(0) + " "+ record.get(1) + " "+5+ " "+record.get(3)+ " "+record.get(4)+" "+ 87);
                    sejoursObservableListPanier.add(sej);
                }
                if (Objects.equals(Client, currentUser.getId()) && Objects.equals("Attente", status) || Objects.equals(Client, currentUser.getId()) && Objects.equals("Accepte", status)){
                    Sejours sej = new Sejours(record.get(0), record.get(1), record.get(2), record.get(3), "nomhote", record.get(4), record.get(5), record.get(6), record.get(7),record.get(8), record.get(9) );
                    //Label label = new Label(record.get(0) + " "+ record.get(1) + " "+5+ " "+record.get(3)+ " "+record.get(4)+" "+ 87);
                    sejoursObservableListAccepteOuAttente.add(sej);
                }
            }
            try {
                csvParser.close();
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        } else {
            System.out.println("il est pas du tout connecté le boug");
            //rediriger l'utilisateur vers la page de connexion
            erreur.setText("Connectez-vous pour acceder a votre panier");
        }

        printAllSejours(sejoursObservableListPanier);
        printSejoursAttente(sejoursObservableListAccepteOuAttente);

    }

    public void updateHome() throws IOException {
        // créer une nouvelle instance du contrôleur de la page home
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("panier.fxml"));

        // initialiser une nouvelle scène avec la nouvelle instance de contrôleur
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // récupérer la fenêtre de la scène actuelle
        Stage stage = (Stage) erreur.getScene().getWindow();

        // cacher la fenêtre actuelle et afficher la nouvelle scène
        stage.setScene(scene);
        stage.hide();
        stage.show();

    }

    public void valider(ActionEvent actionEvent) throws IOException {

        for (Sejours sejour : sejoursObservableListPanier) {
            changeCSVLine("src/main/java/com/example/airbnb/views/reservation.csv", sejour.getId(), 8, "Attente");
        }

        updateHome();
        erreur.setText("Votre panier a bien été validé");

    }
}
