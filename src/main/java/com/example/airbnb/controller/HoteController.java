package com.example.airbnb.controller;

import com.example.airbnb.models.Reservation;
import com.example.airbnb.models.Session;
import com.example.airbnb.models.User;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Paths;

public class HoteController {
	@FXML
	private TableView<Reservation> studentsTable;
	@FXML
	private TableColumn<Reservation, String> ville;
	@FXML
	private TableColumn<Reservation, String> depart;
	@FXML
	private TableColumn<Reservation, String> nbPassagers;
	@FXML
	private TableColumn<Reservation, String> retour;
	@FXML
	private TableColumn<Reservation, String> prix;
	@FXML
	private TableColumn<Reservation, Reservation> editCol;
	@FXML
	private TableColumn<Reservation, Reservation> status;
	@FXML
	private Label labeltitre;

	public void initialize() throws IOException {
		int i = 0;

		Session session = Session.getInstance();
		User currentUser = session.getCurrentUser();
		System.out.println("********** le prenom de l'utilisteur est*****:  "+currentUser.getPrenom());

		labeltitre.setText( currentUser.getNom()+ " " + currentUser.getPrenom());


		ville.setCellValueFactory(new PropertyValueFactory<>("ville"));
		depart.setCellValueFactory(new PropertyValueFactory<>("datedebut"));
		retour.setCellValueFactory(new PropertyValueFactory<>("datefin"));
		nbPassagers.setCellValueFactory(new PropertyValueFactory<>("nbPassagers"));
		prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
		status.setCellValueFactory(new PropertyValueFactory<>("status"));

		editCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		editCol.setCellFactory(param -> new TableCell<Reservation, Reservation>() {

			private final Button deleteButton = new Button();
			private final Button acceptButton = new Button();
			private final HBox hbox = new HBox(10, acceptButton, deleteButton);
			private final ImageView editImage = new ImageView(new Image(
					"file:///C:/Users/wadii/java/AIRBNB/AirBnb/src/main/java/com/example/airbnb/vue/accept.png"));
			private final ImageView deleteImage = new ImageView(new Image(
					"file:///C:/Users/wadii/java/AIRBNB/AirBnb/src/main/java/com/example/airbnb/vue/refuse.png"));

			@Override
			protected void updateItem(Reservation res, boolean empty) {
				super.updateItem(res, empty);

				if (res == null) {
					setGraphic(null);
					return;
				}
				editImage.setFitWidth(16);
				editImage.setFitHeight(16);
				deleteImage.setFitWidth(16);
				deleteImage.setFitHeight(16);
				acceptButton.setGraphic(editImage);
				deleteButton.setGraphic(deleteImage);
				setGraphic(hbox);

				deleteButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// Action à faire lors de la suppression d'une réservation
						Reservation reservation = getTableView().getItems().get(getIndex());
						System.out.println("Accept clicked for reservation: " + reservation.getDatedebut());

						String csvFile = "src/main/java/com/example/airbnb/data/Reservation.csv";
						String line = "";
						String csvSplitBy = ",";
						int columnToModify = 7;
						// index of column to modify (0-based)7
						String idResToModify = reservation.getVille();
						String newColumnValue = "Refusé";
						try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
							StringBuilder sb = new StringBuilder();

							while ((line = br.readLine()) != null) {
								String[] data = line.split(csvSplitBy);

								if (data[0].equals(idResToModify)) {
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

				});
				acceptButton.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// Action à faire lors de la suppression d'une réservation
						Reservation reservation = getTableView().getItems().get(getIndex());
						System.out.println("Accept clicked for reservation: " + reservation.getDatedebut());

						String csvFile = "src/main/java/com/example/airbnb/data/Reservation.csv";
						String line = "";
						String csvSplitBy = ",";
						int columnToModify = 7;
						// index of column to modify (0-based)7
						String idResToModify = reservation.getVille();
						String newColumnValue = "Accepté";

						try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
							StringBuilder sb = new StringBuilder();

							while ((line = br.readLine()) != null) {
								String[] data = line.split(csvSplitBy);

								if (data[0].equals(idResToModify)) {
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

				});
			}
		});

		String csvFilePath = "src/main/java/com/example/airbnb/data/Reservation.csv";
		String currentPath = Paths.get("").toAbsolutePath().toString();
		FileReader reader = new FileReader(csvFilePath);
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
		boolean trouve = false;
		for (CSVRecord record : csvParser) {
			if (i != 0) {
				// Récupérer les valeurs de chaque colonne en utilisant leur index
				String ville = record.get(0);
				String prix = record.get(1);
				String sejour = record.get(2);
				String hote = record.get(3);
				String nbPassagers = record.get(4);
				String datedebut = record.get(5);
				String datefin = record.get(6);
				String status = record.get(7);
				studentsTable.getItems().add(new Reservation(ville, prix, sejour, hote, nbPassagers, datedebut, datefin, status));
			}
			i += 1;

		}
		editCol.setPrefWidth(500);
		csvParser.close();
		reader.close();
	}

}
