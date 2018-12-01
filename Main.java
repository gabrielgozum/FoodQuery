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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);

	}
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Food Query!");
		StackPane root = new StackPane();
		StackPane second = new StackPane();
		Scene scene1 = new Scene(root, 500, 500);
		VBox VB1 = new VBox();
		VBox VB2 = new VBox();
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
		    "Beans", "Burgers", "Fries", "Nuggets");
		list.setItems(items);
		ListView<String> mealList = new ListView<String>();
		ObservableList<String> mealItems = FXCollections.observableArrayList(
				"Burgers", "Fries", "Beer", "Fruit");
		mealList.setItems(mealItems);
		VB1.getChildren().addAll(analyzeMeal, list);
		VB2.getChildren().addAll(fileInput, fileInputField, mealList);
		HB.getChildren().addAll(VB1, VB2);
	root.getChildren().add(HB, 3 , 3);
	primaryStage.setScene(scene1);
	primaryStage.show();
	}
}