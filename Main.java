package application;


import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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

		primaryStage.setTitle("FoodQuery and Meal Analysis");
		StackPane root = new StackPane();
		Scene scene1 = new Scene(root, 600, 600);
		VBox VB1 = new VBox(); 	//Used in left Border
		VBox VB2 = new VBox();	//Used in right border
		VBox VB3 = new VBox();	//Used in bottom border in HB
		VBox VB4 = new VBox();	//Used in the center
		VBox VB5 = new VBox(); 	//Used in bottom border in HB
		VBox VB6 = new VBox(); 	//Used in bottom border in HB
		HBox HB = new HBox();	//Used in bottom border
		HBox nameBox = new HBox();
		HBox proteinBox = new HBox();
		HBox fiberBox = new HBox();
		HBox calorieBox = new HBox();
		HBox carbohydrateBox = new HBox();
		HBox fatBox = new HBox();
		
		Button analyzeMeal = new Button();
		
		Label topLabel = new Label("FoodQuery and Meal Analysis");
		topLabel.setFont(new Font("Arial", 30));
		Label individualFood = new Label("Add Food Manually: ");
		Label fileInput = new Label();
		Label foodName = new Label("Name: ");
		Label foodProtein = new Label("Protein: ");
		Label foodFiber = new Label("Fiber: ");
		Label foodCalories = new Label("Calories :");
		Label foodCarbs = new Label("Carbohydrates:");
		Label foodFat = new Label("Fat: ");
		Label foodListLabel = new Label("Food List");
		Label mealListLabel = new Label("Meal List");
		
		TextField foodNameField = new TextField();
		TextField foodProteinField = new TextField();
		TextField foodFiberField = new TextField();
		TextField foodCaloriesField = new TextField();
		TextField foodCarbsField = new TextField();
		TextField foodFatField = new TextField();
		TextField fileInputField = new TextField();
		TextField foodListSearchBar = new TextField("Search");
		TextField mealListSearchBar = new TextField("Search");
		
		nameBox.getChildren().addAll(foodName, foodNameField);
		proteinBox.getChildren().addAll(foodProtein, foodProteinField);
		fiberBox.getChildren().addAll(foodFiber, foodFiberField);
		calorieBox.getChildren().addAll(foodCalories, foodCaloriesField);
		carbohydrateBox.getChildren().addAll(foodCarbs, foodCarbsField);
		fatBox.getChildren().addAll(foodFat, foodFatField);
		
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
		   "Apples", "Avacado", "Bananas", "Beans", "Beer", "Burgers", "Fries", "Granola Bar", "Grapefruit", "Nuggets", "Pizza");
		list.setItems(items);
		list.setPrefWidth(100);
		list.setPrefHeight(250);

		ListView<String> mealList = new ListView<String>();
		ObservableList<String> mealItems = FXCollections.observableArrayList(
				"Burgers", "Fries", "Beer", "Fruit");
		mealList.setItems(mealItems);
		mealList.setPrefWidth(100);
		mealList.setPrefHeight(150);
		
		VB1.getChildren().addAll(foodListLabel, foodListSearchBar, list);
		VB1.setSpacing(10);
		VB2.getChildren().addAll(mealListLabel, mealListSearchBar, mealList, analyzeMeal);
		VB2.setSpacing(10);
		VB3.getChildren().addAll(fileInput, fileInputField); 

		VB5.getChildren().addAll(individualFood, nameBox, proteinBox, fiberBox);
		VB6.getChildren().addAll(calorieBox, carbohydrateBox, fatBox);
		HB.getChildren().addAll(VB3, VB5, VB6);
		
		
		borderPanel.setTop(topLabel);
		borderPanel.setLeft(VB1);
		borderPanel.setRight(VB2);
		borderPanel.setBottom(HB);
		borderPanel.setCenter(VB4);
		root.getChildren().add(borderPanel);
		
	primaryStage.setScene(scene1);
	primaryStage.show();
	}
}