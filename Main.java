package application;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);

	}
	public void start(Stage primaryStage) {
		BorderPane borderPanel = new BorderPane();
		Label topLabel = new Label("     FoodQuery and Meal Analysis");
		topLabel.setFont(new Font("Arial", 30));
		borderPanel.setTop(topLabel);
		primaryStage.setTitle("FoodQuery and Meal Analysis");
		StackPane root = new StackPane();
		Scene scene1 = new Scene(root, 500, 500);
		VBox VB1 = new VBox();
		VBox VB2 = new VBox();
		VBox VB3 = new VBox();
		HBox HB = new HBox();
		Button analyzeMeal = new Button();
		Label fileInput = new Label();
		TextField fileInputField = new TextField();
		fileInput.setText("Enter your file name");
		analyzeMeal.setText("Click to analyze your meal");
		analyzeMeal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Meal analysis");
			}
		});
		fileInputField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Placeholder message until back end functionality is implemented
				System.out.println("This is when the file would be read");
			}
		});
		ListView<String> list = new ListView<String>();
		ObservableList<String> items =FXCollections.observableArrayList (
		   "Apples", "Avacado", "Bananas", "Beans", "Burgers", "Fries", "Granola Bar", "Grapefruit", "Nuggets", "Pizza");
		list.setItems(items);
		list.setPrefWidth(100);
		list.setPrefHeight(70);
		VB1.getChildren().addAll(analyzeMeal, list);
		VB1.setSpacing(10);
		VB3.getChildren().addAll(fileInput, fileInputField);
		HB.getChildren().addAll(VB1, VB2, VB3);
	//root.getChildren().add(HB);
	borderPanel.setLeft(VB1);
	root.getChildren().add(borderPanel);
	primaryStage.setScene(scene1);
	primaryStage.show();
	}
}