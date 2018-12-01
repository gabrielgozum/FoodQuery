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
		BorderPane mainBorderPanel = new BorderPane();
		BorderPane filterBorderPanel = new BorderPane();

		primaryStage.setTitle("FoodQuery and Meal Analysis");
		StackPane root = new StackPane();
		Scene mainScene = new Scene(root, 600, 600);
		StackPane filter = new StackPane();
		Scene filterScene = new Scene(filter, 600, 600);
		
		//Main boxes
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
		
		//Filter boxes
		HBox FHBMinMax = new HBox();
		HBox FHBCal = new HBox();
		HBox FHBCarb = new HBox();
		HBox FHBFat = new HBox();
		HBox FHBFiber = new HBox();
		HBox FHBProtein = new HBox();
		VBox FVB1 = new VBox();
		
		Button analyzeMeal = new Button();
		Button filterSceneBtn = new Button();
		Button rootBtn = new Button();
		rootBtn.setText("Apply filters and go back");
		filterSceneBtn.setText("Click to apply Filters");
		
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
		foodListLabel.setFont(new Font("Arial" , 15));
		Label mealListLabel = new Label("Meal List");
		mealListLabel.setFont(new Font("Arial" , 15));
		Label foodFiltersLabel = new Label("Food Filters");
		Label mealCalories = new Label("Total Calories: ");
		Label mealCarbohydrates = new Label("Total Carbs: ");
		Label mealFat = new Label("Total Fat: ");
		Label mealFiber = new Label("Total Fiber: ");
		Label mealProtein = new Label("Total Protein: ");
		Label mealAnalysis = new Label("Anaylzed Meal: ");
		Label addFromList = new Label("Click from list to add to meal");
		Label removeFromMeal = new Label("Click from meal list to remove");
		
		Label minimum = new Label("Minmum Value");
		Label maximum = new Label("Maximum Value");
		Label minMaxGap = new Label("							");	
		Label calFilter = new Label("<= Calories <");
		Label carbFilter = new Label("<= Carbohydrates <");
		Label fatFilter = new Label("<= Fat <");
		Label fiberFilter = new Label("<= Fiber <");
		Label proteinFilter = new Label("<= Protein <");
		
		TextField foodNameField = new TextField();
		TextField foodProteinField = new TextField();
		TextField foodFiberField = new TextField();
		TextField foodCaloriesField = new TextField();
		TextField foodCarbsField = new TextField();
		TextField foodFatField = new TextField();
		TextField fileInputField = new TextField();
		TextField foodListSearchBar = new TextField("Search");
		TextField mealListSearchBar = new TextField("Search");
		
		TextField minCal = new TextField();
		TextField maxCal = new TextField();
		TextField minCarbs = new TextField();
		TextField maxCarbs = new TextField();
		TextField minFat = new TextField();
		TextField maxFat = new TextField();
		TextField minFiber = new TextField();
		TextField maxFiber = new TextField();
		TextField minProtein = new TextField();
		TextField maxProtein = new TextField();
		
		
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
		filterSceneBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(filterScene);
			}
		});
		rootBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(mainScene);
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
		
		VB1.getChildren().addAll(foodListLabel, foodListSearchBar, list, addFromList, filterSceneBtn);
		VB1.setSpacing(10);
		VB2.getChildren().addAll(mealListLabel, mealListSearchBar, mealList, removeFromMeal, analyzeMeal);
		VB2.setSpacing(10);
		VB3.getChildren().addAll(fileInput, fileInputField); 
		VB4.getChildren().addAll(mealAnalysis, mealCalories, mealCarbohydrates, mealFat, 
				mealFiber, mealProtein);
		VB4.setAlignment(Pos.CENTER);
		VB5.getChildren().addAll(individualFood, nameBox, proteinBox, fiberBox);
		VB5.setSpacing(2);
		VB6.getChildren().addAll(calorieBox, carbohydrateBox, fatBox);
		VB6.setSpacing(2);
		VB6.setAlignment(Pos.BOTTOM_CENTER);
		HB.getChildren().addAll(VB3, VB5, VB6);
		FHBMinMax.getChildren().addAll(minimum, minMaxGap, maximum);
		FHBCal.getChildren().addAll(minCal, calFilter, maxCal);
		FHBCarb.getChildren().addAll(minCarbs, carbFilter, maxCarbs);
		FHBFat.getChildren().addAll(minFat, fatFilter, maxFat);
		FHBFiber.getChildren().addAll(minFiber, fiberFilter, maxFiber);
		FHBProtein.getChildren().addAll(minProtein, proteinFilter, maxProtein);
		
		mainBorderPanel.setTop(topLabel);
		mainBorderPanel.setLeft(VB1);
		mainBorderPanel.setRight(VB2);
		mainBorderPanel.setBottom(HB);
		mainBorderPanel.setCenter(VB4);
		root.getChildren().add(mainBorderPanel);
		FVB1.getChildren().addAll(FHBMinMax, FHBCal, FHBCarb, FHBFat, FHBFiber, FHBProtein);
		filterBorderPanel.setCenter(FVB1);
		filterBorderPanel.setBottom(rootBtn);
		filter.getChildren().add(filterBorderPanel);
	primaryStage.setScene(mainScene);
	primaryStage.show();
	}
}