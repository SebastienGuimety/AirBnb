package com.example.airbnb.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.time.ZonedDateTime;
import java.util.*;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.example.airbnb.models.CalendarActivity;
import com.example.airbnb.models.Reservation;

public class CalendarController {

	ZonedDateTime dateFocus;
	ZonedDateTime today;

	@FXML
	private Text year;

	@FXML
	private Text month;

	@FXML
	private FlowPane calendar;
	private List<CalendarActivity> calendarActivities = new ArrayList<>();

	public void initialize() {
		dateFocus = ZonedDateTime.now();
		today = ZonedDateTime.now();
		drawCalendar();
	}

	@FXML
	void backOneMonth(ActionEvent event) {
		dateFocus = dateFocus.minusMonths(1);
		calendar.getChildren().clear();
		calendarActivities = new ArrayList<>();
		drawCalendar();
	}

	@FXML
	void forwardOneMonth(ActionEvent event) {
		dateFocus = dateFocus.plusMonths(1);
		calendar.getChildren().clear();
		calendarActivities = new ArrayList<>();
		drawCalendar();
	}

	private void drawCalendar() {
		year.setText(String.valueOf(dateFocus.getYear()));
		month.setText(String.valueOf(dateFocus.getMonth()));

		double calendarWidth = calendar.getPrefWidth();
		double calendarHeight = calendar.getPrefHeight();
		double strokeWidth = 1;
		double spacingH = calendar.getHgap();
		double spacingV = calendar.getVgap();

		// List of activities for a given month
		Map<Integer, List<CalendarActivity>> calendarActivityMap = null;
		try {
			calendarActivityMap = getCalendarActivitiesMonth(dateFocus);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int monthMaxDate = dateFocus.getMonth().maxLength();
		// Check for leap year
		if (dateFocus.getYear() % 4 != 0 && monthMaxDate == 29) {
			monthMaxDate = 28;
		}
		int dateOffset = ZonedDateTime
				.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1, 0, 0, 0, 0, dateFocus.getZone()).getDayOfWeek()
				.getValue();

		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				StackPane stackPane = new StackPane();

				Rectangle rectangle = new Rectangle();
				rectangle.setFill(Color.TRANSPARENT);
				rectangle.setStroke(Color.BLACK);
				rectangle.setStrokeWidth(strokeWidth);
				double rectangleWidth = (calendarWidth / 7) - strokeWidth - spacingH;
				rectangle.setWidth(rectangleWidth);
				double rectangleHeight = (calendarHeight / 6) - strokeWidth - spacingV;
				rectangle.setHeight(rectangleHeight);
				stackPane.getChildren().add(rectangle);

				int calculatedDate = (j + 1) + (7 * i);
				if (calculatedDate > dateOffset) {
					int currentDate = calculatedDate - dateOffset;
					if (currentDate <= monthMaxDate) {
						Text date = new Text(String.valueOf(currentDate));
						double textTranslationY = -(rectangleHeight / 2) * 0.75;
						date.setTranslateY(textTranslationY);
						stackPane.getChildren().add(date);

						List<CalendarActivity> calendarActivities = calendarActivityMap.get(currentDate);
						if (calendarActivities != null) {
							createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
						}
					}
					if (today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth()
							&& today.getDayOfMonth() == currentDate) {
						rectangle.setStroke(Color.BLUE);
					}
				}
				calendar.getChildren().add(stackPane);
			}
		}
	}

	private void createCalendarActivity(List<CalendarActivity> calendarActivities, double rectangleHeight,
			double rectangleWidth, StackPane stackPane) {
		VBox calendarActivityBox = new VBox();
		for (int k = 0; k < calendarActivities.size(); k++) {
			if (k >= 2) {
				Text moreActivities = new Text("...");
				calendarActivityBox.getChildren().add(moreActivities);
				moreActivities.setOnMouseClicked(mouseEvent -> {
					// On ... click print all activities for given date
					System.out.println(calendarActivities);
				});
				break;
			}
			Text text = new Text(calendarActivities.get(k).getClientName());
			calendarActivityBox.getChildren().add(text);
			text.setOnMouseClicked(mouseEvent -> {
				// On Text clicked
				System.out.println(text.getText());
			});
		}
		calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
		calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
		calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
		calendarActivityBox.setStyle("-fx-background-color:GRAY");
		stackPane.getChildren().add(calendarActivityBox);
	}

	private Map<Integer, List<CalendarActivity>> createCalendarMap(ZonedDateTime date) {

		/*
		 * Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();
		 * 
		 * for (CalendarActivity activity: calendarActivities) { int activityDate =
		 * activity.getDate().getDayOfMonth();
		 * if(!calendarActivityMap.containsKey(activityDate)){
		 * calendarActivityMap.put(activityDate, List.of(activity)); } else {
		 * List<CalendarActivity> OldListByDate = calendarActivityMap.get(activityDate);
		 * 
		 * List<CalendarActivity> newList = new ArrayList<>(OldListByDate);
		 * newList.add(activity); calendarActivityMap.put(activityDate, newList); } }
		 * return calendarActivityMap;
		 */

		Map<Integer, List<CalendarActivity>> calendarActivityMap = new HashMap<>();

		int monthValue = date.getMonthValue();
		int yearValue = date.getYear();
		for (CalendarActivity activity : calendarActivities) {

			if (activity.getDate().getMonthValue() == monthValue && activity.getDate().getYear() == yearValue) {
				int activityDate = activity.getDate().getDayOfMonth();
				if (!calendarActivityMap.containsKey(activityDate)) {
					calendarActivityMap.put(activityDate, List.of(activity));
				} else {
					List<CalendarActivity> OldListByDate = calendarActivityMap.get(activityDate);

					List<CalendarActivity> newList = new ArrayList<>(OldListByDate);
					newList.add(activity);
					calendarActivityMap.put(activityDate, newList);
				}
			}
		}
		return calendarActivityMap;
	}

	public void addCalendarActivity(ZonedDateTime date, String clientName, ZonedDateTime date1) {

		calendarActivities.add(new CalendarActivity(date1, clientName, 1));
	}

	private Map<Integer, List<CalendarActivity>> getCalendarActivitiesMonth(ZonedDateTime dateFocus)
			throws IOException {

		int year = dateFocus.getYear();
		int month = dateFocus.getMonth().getValue();

		/*
		 * Random random = new Random(); for (int i = 0; i < 50; i++) { ZonedDateTime
		 * time = ZonedDateTime.of(year, month, random.nextInt(27)+1,
		 * 16,0,0,0,dateFocus.getZone()); calendarActivities.add(new
		 * CalendarActivity(time, "Hans", 111111)); }
		 */

		String csvFilePath = "src/main/java/com/example/airbnb/data/Reservation.csv";
		String currentPath = Paths.get("").toAbsolutePath().toString();
		FileReader reader = new FileReader(csvFilePath);
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
		boolean trouve = false;
		int i = 0;
		for (CSVRecord record : csvParser) {
			if (i != 0) {
				// Récupérer les valeurs de chaque colonne en utilisant leur index

				String status = record.get(8);

				if (status.equals("Accepté")) {

					String client = record.get(4);

					String datedebut = record.get(6);
					String[] dateArr = datedebut.split("/");
					System.out.println("date debut ********** " + datedebut);
					ZonedDateTime time_debut = ZonedDateTime.of(Integer.parseInt(dateArr[2]),
							Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]), 16, 0, 0, 0,
							dateFocus.getZone());
					calendarActivities.add(new CalendarActivity(time_debut, client + " : Debut", 111111));

					String datefin = record.get(7);
					dateArr = datefin.split("/");
					ZonedDateTime time_fin = ZonedDateTime.of(Integer.parseInt(dateArr[2]),
							Integer.parseInt(dateArr[1]), Integer.parseInt(dateArr[0]), 16, 0, 0, 0,
							dateFocus.getZone());
					calendarActivities.add(new CalendarActivity(time_fin, client + " : Fin", 111111));
				}

			}
			i += 1;

		}

		csvParser.close();
		reader.close();

		return createCalendarMap(dateFocus);
	}

}
