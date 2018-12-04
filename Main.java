package application;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		
/////////////////////// TESTING FOOD DATA ///////////////////////////////
		
 		FoodData foodData = new FoodData();
		foodData.loadFoodItems("application/gabesFoodList");
		ArrayList<FoodItem> foodList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
		for(FoodItem i : foodList)
			System.out.println(i.getName());
		
		foodData.saveFoodItems("application/foodSaved.txt");

//////////////////////////////////////////////////////////////////////////
		
		launch(args);

	}
	public void start(Stage primaryStage) {
		//Different BorderPanes for each scene
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
		
		//All the buttons
		Button analyzeMeal = new Button(); 		//will calculate nutrition totals
		Button filterSceneBtn = new Button(); 	//changes to filter scene
		Button rootBtn = new Button();			//changes back to root
		rootBtn.setText("Apply filters and go back");
		filterSceneBtn.setText("Click to apply Filters");
		
		//Labels seen on the root scene
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
		
		//Label foodFiltersLabel = new Label("Food Filters"); it was here, don't know why,
		//might delete later
		
		
		//Labels for total nutrients
		Label mealCalories = new Label("Total Calories: ");
		Label mealCarbohydrates = new Label("Total Carbs: ");
		Label mealFat = new Label("Total Fat: ");
		Label mealFiber = new Label("Total Fiber: ");
		Label mealProtein = new Label("Total Protein: ");
		Label mealAnalysis = new Label("Anaylzed Meal: ");
		Label addFromList = new Label("Click from list to add to meal");
		Label removeFromMeal = new Label("Click from meal list to remove");
		
		
		//Labels used on the filter scene
		Label minimum = new Label("Minmum Value");
		Label maximum = new Label("Maximum Value");
		Label minMaxGap = new Label("							");	
		Label calFilter = new Label("<= Calories <");
		Label carbFilter = new Label("<= Carbohydrates <");
		Label fatFilter = new Label("<= Fat <");
		Label fiberFilter = new Label("<= Fiber <");
		Label proteinFilter = new Label("<= Protein <");
		
		//TextFields for inputting own individual foods
		TextField foodNameField = new TextField();
		TextField foodProteinField = new TextField();
		TextField foodFiberField = new TextField();
		TextField foodCaloriesField = new TextField();
		TextField foodCarbsField = new TextField();
		TextField foodFatField = new TextField();
		TextField fileInputField = new TextField();
		
		//TextFields for searching in the lists
		TextField foodListSearchBar = new TextField("Search");
		TextField mealListSearchBar = new TextField("Search");
		
		//TextFields for applying filters
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
		
		//Boxes for adding individual FoodItems
		nameBox.getChildren().addAll(foodName, foodNameField);
		proteinBox.getChildren().addAll(foodProtein, foodProteinField);
		fiberBox.getChildren().addAll(foodFiber, foodFiberField);
		calorieBox.getChildren().addAll(foodCalories, foodCaloriesField);
		carbohydrateBox.getChildren().addAll(foodCarbs, foodCarbsField);
		fatBox.getChildren().addAll(foodFat, foodFatField);
		
		fileInput.setText("Enter your file name");
		analyzeMeal.setText("Click to analyze your meal");
		
		/*
		 * When analyzeMeal is pressed, the total nutritional value of the meal
		 * will be added up and displayed on the root scene.
		 */
		analyzeMeal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println("Meal analysis");
			}
		});
		ListView<String> foodList = new ListView<String>();
		ListView<String> mealList = new ListView<String>();
		List<String> mealListItems = new ArrayList<String>();
		/*
		 * When fileInputField has had a file typed in and enter has been
		 * pressed, it will read the contents of that file and make a list of
		 * FoodItems and add them to the food list on the root scene.
		 */
		fileInputField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FoodData fileReadIn = new FoodData();	//create FoodData object to read file
				fileReadIn.loadFoodItems(fileInputField.getText()); //Load file in
				//Get an observable list of FoodItems
				ObservableList<FoodItem> foodListItems = 
						FXCollections.observableArrayList(fileReadIn.getAllFoodItems());
				
				List<String> foodItemsNames = new ArrayList<String>();
				//Get the name of each item from foodListItems and put it into separate list.
				for(FoodItem f: foodListItems) {
					if(!f.getName().equals("")) {	//make sure empty entries don't make it in
						foodItemsNames.add(f.getName());
					}
				}
				//Make the list of foodItemNames into an ObservableList to be displayed.
				ObservableList<String> displayFood = FXCollections.observableArrayList(foodItemsNames);
				Collections.sort(displayFood);	//sort the list in alphabetical order
				foodList.setItems(displayFood);
			}
		});
		
		foodList.setPrefWidth(100);
		foodList.setPrefHeight(250);
		
		/*
		 * When an individual item is clicked in foodList, it adds it to mealList.
		 * This does this by adding the clicked item to a list, then turning that list 
		 * into an observable list to be added to the ListView object.
		 */
		foodList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				mealListItems.add(foodList.getSelectionModel().getSelectedItem());
				ObservableList<String> mealListObserve = FXCollections.observableArrayList(mealListItems);
				mealList.setItems(mealListObserve);
			}
		});
		
		//For aesthetics
		mealList.setPrefWidth(100);
		mealList.setPrefHeight(150);
		/*
		 * When the filterSceneBtn is pressed, the scene changes to the filter
		 * scene where the user can input their filters accordingly.
		 */
		filterSceneBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(filterScene);
			}
		});
		/*
		 * When rootBtn is pressed, the scene changes to the main scene.
		 * Filters will also be applied here.
		 */
		rootBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(mainScene);
			}
		});
		
		//Hard coded mealList for milestone 2
//		ListView<String> mealList = new ListView<String>();
//		ObservableList<String> mealItems = FXCollections.observableArrayList(
//				"Burgers", "Fries", "Beer", "Fruit");
//		mealList.setItems(mealItems);
//		
		//Left side of the main scene
		VB1.getChildren().addAll(foodListLabel, foodListSearchBar, foodList, addFromList, filterSceneBtn);
		VB1.setSpacing(10);
		
		//Right side of the main scene
		VB2.getChildren().addAll(mealListLabel, mealListSearchBar, mealList, removeFromMeal, analyzeMeal);
		VB2.setSpacing(10);
		
		//Used in bottom of main scene
		VB3.getChildren().addAll(fileInput, fileInputField); 
		
		//Used in center of main scene (mealAnalysis)
		VB4.getChildren().addAll(mealAnalysis, mealCalories, mealCarbohydrates, mealFat, 
				mealFiber, mealProtein);
		VB4.setAlignment(Pos.CENTER);
		
		//Used in bottom of main scene to input individual foods.
		VB5.getChildren().addAll(individualFood, nameBox, proteinBox, fiberBox);
		VB5.setSpacing(2);
		VB6.getChildren().addAll(calorieBox, carbohydrateBox, fatBox);
		VB6.setSpacing(2);
		VB6.setAlignment(Pos.BOTTOM_CENTER);
		
		//Put all the bottom VBoxes together
		HB.getChildren().addAll(VB3, VB5, VB6);
		
		//All of the horizontal boxes used in filters
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
		/*
		 * Add all the horizontal boxes used in filters to the vertical box on filter
		 * to make it look nice.
		 */
		FVB1.getChildren().addAll(FHBMinMax, FHBCal, FHBCarb, FHBFat, FHBFiber, FHBProtein);
		filterBorderPanel.setCenter(FVB1);
		filterBorderPanel.setBottom(rootBtn);
		filter.getChildren().add(filterBorderPanel);
	primaryStage.setScene(mainScene);
	primaryStage.show();
	}
}