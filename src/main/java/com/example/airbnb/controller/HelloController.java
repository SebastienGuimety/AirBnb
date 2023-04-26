package com.example.airbnb.controller;

import com.example.airbnb.vue.LoginController;

import com.example.airbnb.HelloApplication;
import com.example.airbnb.models.Sejour;
import com.example.airbnb.models.Sejours;
import com.example.airbnb.models.Session;
import com.example.airbnb.models.User;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
	JFXButton connexion;

	@FXML
	private HBox hboxcentre;
	@FXML
	private TableView<Sejours> tableView;

	@FXML
	private TableColumn<Sejours, String> ville;

	@FXML
	private TableColumn<Sejours, String> pays;

	@FXML
	private TableColumn<Sejours, Integer> prix;

	@FXML
	private TableColumn<Sejours, String> hote;

	@FXML
	private TableColumn<Sejours, String> nbPassagers;

	@FXML
	private TableColumn<Sejours, Integer> avis;

	@FXML
	private ScrollPane scroll;

	@FXML
	private TextField destination, depart, retour;

	@FXML
	private JFXButton submitForm;
	@FXML
	JFXButton connection;
	@FXML
	JFXButton deconnexion;

	@FXML
	ImageView panierpng;
	private double x, y;

	public void updateHome() throws IOException {
		// créer une nouvelle instance du contrôleur de la page home
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("vue/hello-view.fxml"));
		// initialiser une nouvelle scène avec la nouvelle instance de contrôleur
		Parent root = fxmlLoader.load();
		Scene scene = new Scene(root);

		// récupérer la fenêtre de la scène actuelle
		Stage stage = (Stage) connection.getScene().getWindow();

		// cacher la fenêtre actuelle et afficher la nouvelle scène
		stage.setScene(scene);
		stage.hide();
		stage.show();

	}

	public void seConnecter() throws IOException {

		Stage newStage = new Stage();
		FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("vue/login.fxml"));
		Scene scene = new Scene(fxmlLoader.load(), 320, 240);
		LoginController loginController = fxmlLoader.getController();

		// Passer la référence au contrôleur de la page Home
		loginController.setHomeController(this);
		newStage.setWidth(350); // définir la largeur de la fenêtre
		newStage.setHeight(300); // définir la hauteur de la fenêtre
		newStage.setScene(scene);
		newStage.show();
		Alert alert = new Alert(null);
	}

	private final Session session = Session.getInstance();

	List<Sejours> sejours = new ArrayList<>();
	ObservableList<Sejours> sejoursObservableList = FXCollections.observableArrayList();

	public void getAllSejours() throws IOException {
		String csvFilePath = "src/main/java/com/example/airbnb/views/sejours.csv";
		String currentPath = Paths.get("").toAbsolutePath().toString();
		FileReader reader = new FileReader(csvFilePath);
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

		for (CSVRecord record : csvParser) {

			// Récupérer les valeurs de chaque colonne en utilisant leur index

			Sejours sej = new Sejours(record.get(0), record.get(1), record.get(2), record.get(3), record.get(4),
					record.get(5), record.get(6), record.get(7), record.get(8));
			// Label label = new Label(record.get(0) + " "+ record.get(1) + " "+5+ "
			// "+record.get(3)+ " "+record.get(4)+" "+ 87);
			sejoursObservableList.add(sej);

		}

		csvParser.close();
		reader.close();
	}

	public void seDeconnecter() throws IOException {

		session.logout();
		updateHome();

	}

	public void printAllSejours(List<Sejours> list) {
		for (Sejours flight : list) {
			// Create a VBox to hold the flight details

			HBox hboxville = new HBox(new Label("Ville : " + flight.getVille()));
			HBox hboxpays = new HBox(new Label("Pays : " + flight.getPays()));
			HBox hboxprix = new HBox(new Label("Prix : " + flight.getPrix()));
			HBox hboxnb = new HBox(new Label("Nombres de passagers max : " + flight.getNbPassagers()));
			HBox hboxhote = new HBox(new Label("Hote : " + flight.getHote()));
			HBox hboxavis = new HBox(new Label("avis : " + flight.getAvis()));

			VBox vboxDetails = new VBox();
			Insets padding = new Insets(50, 0, 0, 0);
			vboxDetails.setPadding(padding);

			// Add each HBox to the VBox
			vboxDetails.getChildren().addAll(hboxville, hboxpays, hboxprix, hboxnb, hboxhote, hboxavis);
			vboxDetails.setUserData(flight);
			vboxDetails.setSpacing(10);
			vboxDetails.setMinWidth(300);

			hboxcentre.getChildren().add(vboxDetails);
			hboxcentre.setAlignment(Pos.CENTER);

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
		updateView();

		Session session = Session.getInstance();
		if (session.isLoggedIn()) {
			User currentUser = session.getCurrentUser();
			System.out.println("il est connecté le boug");
			// afficher les informations de l'utilisateur
		} else {
			System.out.println("il est pas du tout connecté le boug");
			// rediriger l'utilisateur vers la page de connexion
		}

		// Afficher tous les sejours dans la Hbox qui elle meme est dans un ScrollPane
		getAllSejours();

		// Les 3 Listener sur le formulaire

		// Ici Destination
		destination.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() >= 2) {
				// Effectuer la recherche ici
				updateVoyagesList();
			} else {
				printAllSejours(sejoursObservableList);
			}
		});

		// Ici Depart date
		depart.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() >= 2) {
				// Effectuer la recherche ici
				updateVoyagesList();
			} else {
				printAllSejours(sejoursObservableList);
			}
		});

		// Ici Fin date
		retour.textProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.length() >= 2) {
				// Effectuer la recherche ici
				updateVoyagesList();
			} else {
				printAllSejours(sejoursObservableList);
			}

		});

		// print all the sejours in the first load of the view
		printAllSejours(sejoursObservableList);
	}

	// Fonction qui update la liste de voyage pour chaque champ dans le formulaire
	// qui est changé
	private void updateVoyagesList() {
		String dep = depart.getText();
		String ret = retour.getText();
		String dest = destination.getText();

		List<Sejours> filteredFlights = sejoursObservableList.stream()
				.filter(flight -> flight.getVille().contains(dest) || flight.getHote().contains(dest)
						|| flight.getPays().contains(dest) || flight.getPrix().contains(dest))
				.filter(flight -> flight.getDatedebut().contains(dep))
				.filter(flight -> flight.getDatefin().contains(ret)).toList();

		// supprimer tous les voyages
		hboxcentre.getChildren().clear();
		// puis afficher les nouveaux avec la list bien filtrée
		printAllSejours(filteredFlights);

	}

	public void submitForm(ActionEvent actionEvent) {

		List<Sejours> sejourstrie = new ArrayList<>();

		if (destination != null || depart != null || retour != null) {

		}

	}

	private void updateView() {
		if (session != null && session.isLoggedIn()) {
			// Si l'utilisateur est connecté, on affiche les éléments liés à la connexion
			connection.setVisible(false);
			deconnexion.setVisible(true);
			panierpng.setVisible(true);

		} else {
			// Si l'utilisateur n'est pas connecté, on affiche les éléments liés à la
			// déconnexion
			connection.setVisible(true);
			deconnexion.setVisible(false);
			panierpng.setVisible(false);

		}
	}

	@FXML
	void panier(MouseEvent event) throws IOException {
		System.out.println("ouais panier");
		/*
		 * 
		 * Stage newStage = new Stage(); FXMLLoader fxmlLoader = new
		 * FXMLLoader(HelloApplication.class.getResource("vue/login.fxml")); Scene scene
		 * = new Scene(fxmlLoader.load(), 320, 240); newStage.setWidth(800); // définir
		 * la largeur de la fenêtre newStage.setHeight(600); // définir la hauteur de la
		 * fenêtre newStage.setScene(scene); newStage.show(); Alert alert = new
		 * Alert(null);
		 * 
		 */
		
		Session session = Session.getInstance();
		User currentUser = session.getCurrentUser();
		if (session.isHote(currentUser)) {

			Stage newStage = new Stage();
			Parent root = FXMLLoader.load(HelloApplication.class.getResource("vue/Home.fxml"));
			newStage.setScene(new Scene(root));
			// set stage borderless
			newStage.initStyle(StageStyle.UNDECORATED);

			// drag it here
			root.setOnMousePressed(evente -> {
				x = event.getSceneX();
				y = event.getSceneY();
			});
			root.setOnMouseDragged(evente -> {

				newStage.setX(event.getScreenX() - x);
				newStage.setY(event.getScreenY() - y);

			});
			newStage.show();
			final Node source = (Node) event.getSource();

			final Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
			

		}
		/*
		 * else { Stage newStage = new Stage(); FXMLLoader fxmlLoader = new
		 * FXMLLoader(HelloApplication.class.getResource("vue/Home.fxml")); Scene scene
		 * = new Scene(fxmlLoader.load(), 320, 240); newStage.setWidth(800); // définir
		 * newStage.setHeight(600); // définir la hauteur de la
		 * newStage.setScene(scene); newStage.show(); }
		 */

	}

}