package com.example.airbnb.vue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.example.airbnb.HelloApplication;
import com.example.airbnb.models.Reservation;
import com.example.airbnb.models.Session;
import com.example.airbnb.models.User;

public class TestController implements Initializable {

	@FXML
	private VBox pnItems = null;
	@FXML
	private Button btnOverview;

	@FXML
	private Button btnOrders;

	@FXML
	private Button btnCustomers;

	@FXML
	private Button res;

	@FXML
	private Button home;

	@FXML
	private Button planing;

	@FXML
	private Button deconnecter;

	@FXML
	private Pane pnlCustomer;

	@FXML
	private Pane pnlOrders;

	@FXML
	private Pane pnlOverview;

	@FXML
	private Pane pnlMenus;
	@FXML
	private AnchorPane rootNode;

	public void valideReservation(String numReservation) {
		String csvFile = "src/main/java/com/example/airbnb/data/Reservation.csv";
		String line = "";
		String csvSplitBy = ",";
		int columnToModify = 8;
		// index of column to modify (0-based)7

		String newColumnValue = "Accepté";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			StringBuilder sb = new StringBuilder();

			while ((line = br.readLine()) != null) {
				String[] data = line.split(csvSplitBy);

				if (data[9].equals(numReservation)) {

					data[columnToModify] = newColumnValue;

				}
				sb.append(String.join(",", data));
				sb.append(System.lineSeparator());
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
			writer.write(sb.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void refuseReservation(String numReservation) {
		String csvFile = "src/main/java/com/example/airbnb/data/Reservation.csv";
		String line = "";
		String csvSplitBy = ",";
		int columnToModify = 8;
		// index of column to modify (0-based)7

		String newColumnValue = "Refusé";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			StringBuilder sb = new StringBuilder();

			while ((line = br.readLine()) != null) {
				String[] data = line.split(csvSplitBy);

				if (data[9].equals(numReservation)) {

					data[columnToModify] = newColumnValue;

				}
				sb.append(String.join(",", data));
				sb.append(System.lineSeparator());
			}

			BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
			writer.write(sb.toString());
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		Session session = Session.getInstance();
		User currentUser = session.getCurrentUser();
		System.out.println("**********le nom de l'utilisteur est*****:  "+currentUser.getId());
		
		Node[] nodes = new Node[10];

		String csvFilePath = "src/main/java/com/example/airbnb/data/Reservation.csv";
		String currentPath = Paths.get("").toAbsolutePath().toString();
		FileReader reader = null;
		try {
			reader = new FileReader(csvFilePath);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CSVParser csvParser = null;
		try {
			csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int i = 0;
		boolean trouve = false;
		for (CSVRecord record : csvParser) {
			if (i != 0) {
				// Récupérer les valeurs de chaque colonne en utilisant leur index
				String ville = record.get(0);
				String pays = record.get(1);
				String prix = record.get(2);
				String hote = record.get(3);
				String client = record.get(4);
				String nbPassagers = record.get(5);
				String datedebut = record.get(6);
				String datefin = record.get(7);
				String status = record.get(8);
				String id = record.get(9);
				
				System.out.println("*********hote de l'utilisteur est*****:  "+hote);
				final int j = i;

				try {
					nodes[i] = FXMLLoader.load(getClass().getResource("Item.fxml"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				nodes[i].setId("" + id);
				Label status_l = (Label) nodes[i].lookup("#debut");
				status_l.setText(datedebut);
				Label retour = (Label) nodes[i].lookup("#retour");
				retour.setText(datefin);
				Label prix_l = (Label) nodes[i].lookup("#prix");
				retour.setText(prix);
				Label nb_personne = (Label) nodes[i].lookup("#passager");
				nb_personne.setText(nbPassagers);
				Button ok = (Button) nodes[i].lookup("#ok");
				Button non = (Button) nodes[i].lookup("#non");
				if ( hote.equals(currentUser.getId())&& !status.equals("Accepté") && !status.equals("Refusé")) {

					ok.setOnAction(e -> {
						System.out.println("renitialisation");
						valideReservation(nodes[j].getId());
						try {
							renitialiserHome();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						

					});
					non.setOnAction(e -> {
						System.out.println("Refusé test");
						refuseReservation(nodes[j].getId());
						try {
							renitialiserHome();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						

					});

					// give the items some effect

					nodes[i].setOnMouseEntered(event -> {
						nodes[j].setStyle("-fx-background-color : #0A0E3F");
					});
					nodes[i].setOnMouseExited(event -> {
						nodes[j].setStyle("-fx-background-color : #02030A");
					});
					pnItems.getChildren().add(nodes[i]);
				}
			}
			i += 1;

		}

		/*
		 * for (int i = 0; i < nodes.length; i++) { try {
		 * 
		 * final int j = i; nodes[i] =
		 * FXMLLoader.load(getClass().getResource("Item.fxml")); Label status = (Label)
		 * nodes[i].lookup("#status"); status.setText("Item " + i);
		 * 
		 * // give the items some effect
		 * 
		 * nodes[i].setOnMouseEntered(event -> {
		 * nodes[j].setStyle("-fx-background-color : #0A0E3F"); });
		 * nodes[i].setOnMouseExited(event -> {
		 * nodes[j].setStyle("-fx-background-color : #02030A"); });
		 * pnItems.getChildren().add(nodes[i]); } catch (IOException e) {
		 * e.printStackTrace(); } }
		 */

	}

	public void handleClicks(ActionEvent actionEvent) throws IOException {
		if (actionEvent.getSource() == home) {
			Stage newStage = new Stage();

			// Chargez le fichier FXML de votre nouvelle fenêtre
			FXMLLoader loader = new FXMLLoader(getClass().getResource("hello-view.fxml"));

			// Appelez la méthode load() sur l'objet FXMLLoader pour charger le fichier FXML
			Parent newRoot = loader.load();

			// Créez une nouvelle Scene à partir de l'objet racine de la hiérarchie de nœuds
			Scene newScene = new Scene(newRoot);

			// Définissez la Scene dans la Stage
			newStage.setScene(newScene);

			// Affichez la Stage
			newStage.show();
		}
		if (actionEvent.getSource() == res) {
			System.out.println("2");

		}
		if (actionEvent.getSource() == planing) {
			Stage newStage = new Stage();

			// Chargez le fichier FXML de votre nouvelle fenêtre
			FXMLLoader loader = new FXMLLoader(getClass().getResource("calendar.fxml"));

			// Appelez la méthode load() sur l'objet FXMLLoader pour charger le fichier FXML
			Parent newRoot = loader.load();

			// Créez une nouvelle Scene à partir de l'objet racine de la hiérarchie de nœuds
			Scene newScene = new Scene(newRoot);

			// Définissez la Scene dans la Stage
			newStage.setScene(newScene);

			// Affichez la Stage
			newStage.show();

		}
		if (actionEvent.getSource() == deconnecter) {
			System.out.println("4");
			
		}
	}

	public void renitialiserHome() throws IOException {
		Scene scene = rootNode.getScene();

		// Chargez un nouveau fichier FXML pour créer un nouveau nœud racine
		FXMLLoader loader = new FXMLLoader(getClass().getResource("home.fxml"));
		Parent newRoot = loader.load();

		// Réinitialisez la scène avec le nouveau nœud racine
		scene.setRoot(newRoot);

		// Obtenez la fenêtre correspondante et redimensionnez-la en fonction de la nouvelle scène
		Window window = rootNode.getScene().getWindow();
		window.sizeToScene();

	}

}