package application;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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
	
	/*
	 * Used in the meal analysis, keeps track of the total values of each 
	 * nutrient, initially set to 0, because the meal list will be empty.
	 */
	private double totalCalories = 0;
	private double totalCarbs = 0;
	private double totalFat = 0;
	private double totalFiber = 0;
	private double totalProtein = 0;
	
	/*
	 * Used in creating a new FoodItem that the user inputs, each value is
	 * initially set to 0 if user does not enter a value for a nutrient. The
	 * name and the ID must be set however.
	 */
	private double newCalorie = 0;
	private double newCarbs = 0;
	private double newFat = 0;
	private double newFiber = 0;
	private double newProtein = 0;
	private String newName;
	private String newID;
	
	
	public static void main(String[] args) {
		
/////////////////////// TESTING FOOD DATA ///////////////////////////////
		 
		//Diego commented all this so I can test my own stuff.
	    //Make sure we delete this before we submit
		
		
 		FoodData foodData = new FoodData();
		foodData.loadFoodItems("application/foodItems.txt");
		//ArrayList<FoodItem> foodList = (ArrayList<FoodItem>) foodData.getAllFoodItems();
		//for(FoodItem i : foodList)
		//	System.out.println(i.getName());
		
		//foodData.saveFoodItems("application/foodSaved.txt");
		/*
		foodData.getIndexes().put("calories", new BPTree<Double, FoodItem>(3));
		foodData.getIndexes().put("carbs", new BPTree<Double, FoodItem>(3));
		foodData.getIndexes().put("fat", new BPTree<Double, FoodItem>(3));
		foodData.getIndexes().put("fiber", new BPTree<Double, FoodItem>(3));
		foodData.getIndexes().put("protein", new BPTree<Double, FoodItem>(3));
		String[] names = {"calories",
				"carbs", "fat", "fiber", "protein"};
		for (int i = 0; i < 5; i++){
			BPTree<Double, FoodItem> tree = foodData.getIndexes().get(names[i]);
			for (FoodItem fi : foodList){
				tree.insert(fi.getNutrientValue(names[i]), fi);
			}
		}
		BPTree<Double, FoodItem> tree = foodData.getIndexes().get(names[3]);
		List<FoodItem> check = tree.rangeSearch(0.0, ">=");
		System.out.println(foodList.size());
		//System.out.println(tree.toString());
		System.out.println(check.size());
		for (FoodItem fi : check){
			System.out.println(fi.getName() + " " + fi.getNutrientValue("fiber"));
		}
		*/
		
		
		
		
		
		
//////////////////////////////////////////////////////////////////////////
		
		launch(args);

	}
	public void start(Stage primaryStage) {
		//Different BorderPanes for each scene
		BorderPane mainBorderPanel = new BorderPane();
		BorderPane filterBorderPanel = new BorderPane();
		
		/*
		 * 2 GUI Scenes, one handles most of the GUI and is considered
		 * the 'main' one. The other one is for applying the search filters
		 * for search queries.
		 */
		primaryStage.setTitle("FoodQuery and Meal Analysis");
		StackPane root = new StackPane();
		Scene mainScene = new Scene(root, 600, 600);
		StackPane filter = new StackPane();
		Scene filterScene = new Scene(filter, 600, 600);
		
		//Main (root) boxes
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
		HBox idBox = new HBox();
		
		//Filter boxes
		VBox FVBMin = new VBox();
		VBox FVBLabelNames = new VBox();
		VBox FVBMax = new VBox();
		VBox FVBAddRules = new VBox();
		VBox FVBRemoveRules = new VBox();
		HBox FHBMainBox = new HBox();
		
		//All the buttons
		Button analyzeMeal = new Button(); 		//will calculate nutrition totals
		Button filterSceneBtn = new Button(); 	//changes to filter scene
		Button rootBtn = new Button();			//changes back to root
		Button addIndividual = new Button();	//Adds individual FoodItems
		Button saveBtn = new Button();			//Saves the current FoodList
		Button addCalRule = new Button();
		Button removeCalRule = new Button();
		Button addCarbRule = new Button();
		Button removeCarbRule = new Button();
		Button addFatRule = new Button();
		Button removeFatRule = new Button();
		Button addFiberRule = new Button();
		Button removeFiberRule = new Button();
		Button addProteinRule = new Button();
		Button removeProteinRule = new Button();
		addCalRule.setText("Add rule");
		addCarbRule.setText("Add rule");
		addFatRule.setText("Add rule");
		addFiberRule.setText("Add rule");
		addProteinRule.setText("Add rule");
		removeCalRule.setText("Remove rule");
		removeCarbRule.setText("Remove rule");
		removeFatRule.setText("Remove rule");
		removeFiberRule.setText("Remove rule");
		removeProteinRule.setText("Remove rule");
		saveBtn.setText("Save current food list");
		addIndividual.setText("Add new item to food list");
		rootBtn.setText("Apply filters and go back");
		filterSceneBtn.setText("Click to apply Filters");
		analyzeMeal.setText("Click to analyze your meal");
		
		//Labels seen on the root scene
		Label topLabel = new Label("FoodQuery and Meal Analysis");
		topLabel.setFont(new Font("Arial", 30));
		Label fileInput = new Label();
		Label foodName = new Label("Name: ");
		Label foodProtein = new Label("Protein: ");
		Label foodFiber = new Label("Fiber: ");
		Label foodCalories = new Label("Calories :");
		Label foodCarbs = new Label("Carbohydrates:");
		Label foodFat = new Label("Fat: ");
		Label foodID = new Label("ID: ");
		Label foodListLabel = new Label("Food List");
		foodListLabel.setFont(new Font("Arial" , 15));
		Label mealListLabel = new Label("Meal List");
		mealListLabel.setFont(new Font("Arial" , 15));
		fileInput.setText("Enter your file name:");
		
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
		Label minMaxGap = new Label("		");	//Used for aesthetic
		Label addGap = new Label("		");
		Label removeGap = new Label("		");
		Label calFilter = new Label("<= Calories <= ");
		Label carbFilter = new Label("<= Carbs <=");
		Label fatFilter = new Label("<= Fat <=");
		Label fiberFilter = new Label("<= Fiber <=");
		Label proteinFilter = new Label("<= Protein <=");
		FVBLabelNames.getChildren().addAll(minMaxGap, calFilter, carbFilter,
				fatFilter, fiberFilter, proteinFilter);
		FVBLabelNames.setSpacing(8);
		FVBLabelNames.setPrefWidth(87);
		
		//TextFields for inputting own individual foods
		TextField foodNameField = new TextField();
		TextField foodProteinField = new TextField();
		TextField foodFiberField = new TextField();
		TextField foodCaloriesField = new TextField();
		TextField foodCarbsField = new TextField();
		TextField foodFatField = new TextField();
		TextField foodIDField = new TextField();
		TextField fileInputField = new TextField();
			
		//TextFields for searching in the lists
		TextField foodListSearchBar = new TextField();
		foodListSearchBar.setPromptText("Search");
		TextField mealListSearchBar = new TextField();
		mealListSearchBar.setPromptText("Search");
		
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
		idBox.getChildren().addAll(foodID, foodIDField);
			
		/*
		 * Alerts that are shown throughout the GUI in the cases where users
		 * might input values incorrectly, such as negative calories, or forget
		 * a vital input.
		 */
		Alert dupName = new Alert(AlertType.ERROR);
		dupName.setTitle("Duplicate Name Error");
		dupName.setContentText("Can't add an item with a duplicate "
		                + "name to the food list.");
		Alert dupID = new Alert(AlertType.ERROR);
		dupID.setTitle("Duplicate ID Error");
		dupID.setContentText("Can't add an item with a duplicate ID "
		                + "to the food list.");
		Alert negative = new Alert(AlertType.ERROR);
		negative.setTitle("Negative Nutritonal Value Error");
		negative.setContentText("A nutritional value can not be less than 0, "
				+ "your nutrient has been set to 0.");
		Alert notNumber = new Alert(AlertType.ERROR);
		notNumber.setTitle("Alphanumeric Nutritional Value Error");
		notNumber.setContentText("A nutritional value has to be a number.");
		Alert nameAlert = new Alert(AlertType.ERROR);
		nameAlert.setTitle("Name Error");
		nameAlert.setContentText("Name can't be null (remember to hit enter)");
		Alert IDAlert = new Alert(AlertType.ERROR);
		IDAlert.setTitle("ID Error");
		IDAlert.setContentText("ID can't be null (remember to hit enter)");
		
		/*
		 * These are the Lists and ListViews that handle the FoodItem objects
		 * and their names. This is because the names are displayed to the
		 * GUI, not the entire FoodItem, need a List for both the FoodItem
		 * and the the names of them for this reason.
		 */
		ListView<String> foodList = new ListView<String>();
		ListView<String> mealList = new ListView<String>();
		List<String> foodListNames = new ArrayList<String>();
		List<String> mealListNames = new ArrayList<String>();
		List<FoodItem> foodListItems = new ArrayList<FoodItem>();
		List<FoodItem> mealListItems = new ArrayList<FoodItem>();
		mealList.setPrefWidth(100);
		mealList.setPrefHeight(150);
		foodList.setPrefWidth(100);
		foodList.setPrefHeight(250);
		
		/*
		 * When analyzeMeal is pressed, the total nutritional value of the meal
		 * will be added up and displayed on the root scene.
		 */
		analyzeMeal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Clear the list every time to reset values
				mealListItems.clear();
				/*
				 * Find each FoodItem that has its name in the mealList and
				 * add those FoodItems to the mealListItems so their 
				 * nutritional values can be grabbed.
				 */
				for(String s : mealListNames) {
					for(FoodItem f: foodListItems) {
						if(s.equals(f.getName())) {
							mealListItems.add(f);
						}
					}
				}
				//clears the calories so new meals don't add to it
				totalCalories = 0;
				totalCarbs = 0;
				totalFat = 0;
				totalFiber = 0;
				totalProtein = 0;
				/*
				 * If mealListNames is empty, the for each loop is never 
				 * entered, this if statement will allow the meal to properly
				 * update to 0 for an empty meal.
				 */
				if(mealListNames.isEmpty()) {
					mealCalories.setText("Total Calories: " + totalCalories);
					mealCarbohydrates.setText("Total Carbs: " + totalCarbs);
					mealFat.setText("Total Fat: " + totalFat);
					mealFiber.setText("Total Fiber: " + totalFiber);
					mealProtein.setText("Total Protein: " + totalProtein);
				}
				//Add and update displays with total nutrional values
				for(FoodItem f: mealListItems) {
					totalCalories += f.getNutrientValue("calories");
					mealCalories.setText("Total Calories: " + totalCalories);
					totalCarbs += f.getNutrientValue("carbohydrate");
					mealCarbohydrates.setText("Total Carbs: " + totalCarbs);
					totalFat += f.getNutrientValue("fat");
					mealFat.setText("Total Fat: " + totalFat);
					totalFiber += f.getNutrientValue("fiber");
					mealFiber.setText("Total Fiber: " + totalFiber);
					totalProtein += f.getNutrientValue("protein");
					mealProtein.setText("Total Protein: " + totalProtein);
				}
			}
		});
		
		/*
		 * When fileInputField has had a file typed in and enter has been
		 * pressed, it will read the contents of that file and make a list of
		 * FoodItems and add them to the food list on the root scene. Also 
		 * clears the previous FoodList that was displayed on the GUI.
		 */
		fileInputField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				foodListItems.clear();
				foodListNames.clear();
				FoodData fileReadIn = new FoodData();	//create FoodData object to read file
				fileReadIn.loadFoodItems(fileInputField.getText()); //Load file in
				//Get an observable list of FoodItems
				ObservableList<FoodItem> foodListItemsObserve = 
						FXCollections.observableArrayList(fileReadIn.getAllFoodItems());
				//Get the name of each item from foodListItems and put it into separate list.
				for(FoodItem f: foodListItemsObserve) {
					if(!f.getName().equals("")) {	//make sure empty entries don't make it in
						foodListNames.add(f.getName());
						foodListItems.add(f);
					}
				}
				//Make the list of foodItemNames into an ObservableList to be displayed.
				ObservableList<String> displayFood = FXCollections.observableArrayList(foodListNames);
				Collections.sort(displayFood);	//sort the list in alphabetical order
				foodList.setItems(displayFood);
			}
		});
		
		//ObservableList<String> mealListObserve;
		/*
		 * When an individual item is clicked in foodList, it adds it to mealList.
		 * This does this by adding the clicked item to a list, then turning that list 
		 * into an observable list to be added to the ListView object.
		 */
		foodList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				//prevents adding null items on empty ListView
				if(foodList.getSelectionModel().getSelectedItem() != null) {
					mealListNames.add(foodList.getSelectionModel().getSelectedItem());
					Collections.sort(mealListNames);
					ObservableList<String> mealListObserve = FXCollections.observableArrayList(mealListNames);
					mealList.setItems(mealListObserve);
				}
				
			}
		});
		
		/*
		 * Handles removing items when an item is clicked on the meal list.
		 */
		mealList.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event){
				String removedName = mealList.getSelectionModel().getSelectedItem();
				if(removedName != null) {
					mealListNames.remove(removedName);
					ObservableList<String> mealListObserve = FXCollections.observableArrayList(mealListNames);
					mealList.setItems(mealListObserve);
				}
			}
		});
		
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
		
		/*
		 * Following TextFields handle taking in user data and storing them
		 * in variables to make new FoodItem. Checks if the Fields are not
		 * empty Strings. If nothing is put for nutritional values or the
		 * input value is negative, the default value of 0 is used. Also checks
		 * that the input value is a number, and not a string or anything else.
		 */
		//Name case
		foodNameField.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
			    boolean flag = true;
				if(!foodNameField.getText().equals("")) {
				    for(FoodItem f : foodListItems) {
				        if(f.getName().equals(foodNameField.getText())) {
				            //Not working yet, will work on later
				            flag = false;
				            dupName.showAndWait();
				        }
				    }
				    if(flag) {
				        newName = foodNameField.getText();
				    }
				}
			}
		});
		
		//ID case
		foodIDField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!foodIDField.getText().equals("")) {
					newID = foodIDField.getText();
				}
			}
		});
		
		//Protein case
		foodProteinField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!foodProteinField.getText().equals("")) {
					try {
						newProtein = Double.parseDouble(foodProteinField.getText());
						if(newProtein < 0) {
							newProtein = 0;
							negative.showAndWait();
						}
					}
					catch(NumberFormatException e) {
						notNumber.showAndWait();
					}
				}		
			}
		});
		
		//Fiber case
		foodFiberField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!foodFiberField.getText().equals("")) {
					try {
						newFiber = Double.parseDouble(foodFiberField.getText());
						if(newFiber < 0) {
							newFiber = 0;
							negative.showAndWait();
						}
					}
					catch(NumberFormatException e) {
						notNumber.showAndWait();
					}
				}
			}
		});
		
		//Fat case
		foodFatField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!foodFatField.getText().equals("")) {
					try {
						newFat = Double.parseDouble(foodFatField.getText());
						if(newFat < 0) {
							newFat = 0;
							negative.showAndWait();
						}
					}
					catch(NumberFormatException e) {
						notNumber.showAndWait();
					}
	
				}
			}
		});
		
		//Calories case
		foodCaloriesField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!foodCaloriesField.getText().equals("")) {
					try {
						newCalorie = Double.parseDouble(foodCaloriesField.getText());
						if(newCalorie < 0) {
							newCalorie = 0;
							negative.showAndWait();
						}
					}
					catch(NumberFormatException e) {
						notNumber.showAndWait();
					}
				}
			}
		});
		
		//Carbohydrate case
		foodCarbsField.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(!foodCarbsField.getText().equals("")) {
					try {
						newCarbs = Double.parseDouble(foodCarbsField.getText());
						if(newCarbs < 0) {
							newCarbs = 0;
							negative.showAndWait();
						}
					}
					catch(NumberFormatException e) {
						notNumber.showAndWait();
					}
				}
			}
		});
		
		/*
		 * Creates new FoodItem and adds it to the foodList by taking all of
		 * the user input data from the text fields for a custom FoodItem
		 */
		addIndividual.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/*
				 * If the ID or name is null, don't make a new FoodItem, prompt
				 * the user.
				 */
				if(newID == null) {
					IDAlert.showAndWait();
				}
				else if(newName == null) {
					nameAlert.showAndWait();
				}
				//create the new FoodItem and add it to the lists and sorts
				else {
					FoodItem newFood = new FoodItem(newID, newName); 
					newFood.addNutrient("fat", newFat);
					newFood.addNutrient("fiber", newFiber);
					newFood.addNutrient("calories", newCalorie);
					newFood.addNutrient("carbohydrate", newCarbs);
					newFood.addNutrient("protein", newProtein);
					foodListItems.add(newFood);
					foodListNames.add(newFood.getName());
					Collections.sort(foodListNames);
					ObservableList<String> foodListObserve = FXCollections.observableArrayList(foodListNames);
					foodList.setItems(foodListObserve);
				}
				
			}
		});
		
		/*
		 * Saves the current FoodList being displayed to foodSaved.txt. 
		 * Gets every foodItem in the foodList and puts it into a new FoodData
		 * object to read from to write to the new file.
		 */
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FoodData writeFile = new FoodData();
				for(FoodItem f: foodListItems) {
					writeFile.addFoodItem(f);
				}
				writeFile.saveFoodItems("foodSaved.txt");
			}
		});
		
		/*
		 * Handles searching the FoodList by names of the FoodItems in it.
		 * Example: Searching for 'yes' displays all FoodItems that have
		 * the string 'yes' in it.
		 */
		foodListSearchBar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/*
				 * Make the FoodData object that the search will be called on 
				 * and add the current FoodItems to it.
				 */
				FoodData nameSearch = new FoodData();
				for(FoodItem f: foodListItems) {
					nameSearch.addFoodItem(f);
				}
				/*
				 * Get all of the names that match and put them into a ListView
				 * This is done by grabbing all of the FoodItems that meet the
				 * search and adding their names to an ObservableList
				 */
				List<FoodItem> nameSearched = new ArrayList<FoodItem>();
				nameSearched = nameSearch.filterByName(foodListSearchBar.getText());
				List<String> nameSearchedNames = new ArrayList<String>();
				for(FoodItem f: nameSearched) {
					nameSearchedNames.add(f.getName());
				}
				Collections.sort(nameSearchedNames);
				ObservableList<String> nameSearchObserve = FXCollections.observableArrayList(nameSearchedNames);
				foodList.setItems(nameSearchObserve);
			}
		});
		
		/*
		 * Extremly similar to the above foodListSearchBar. Added clearing
		 * and adding the FoodItems to mealListItems otherwise the search 
		 * results would come up blank everytime if done before a meal 
		 * analysis where the FoodItems would be added to the mealListItems.
		 */
		mealListSearchBar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				mealListItems.clear();
				for(String s : mealListNames) {
					for(FoodItem f: foodListItems) {
						if(s.equals(f.getName())) {
							mealListItems.add(f);
						}
					}
				}
				FoodData nameSearchMeal = new FoodData();
				for(FoodItem f: mealListItems) {
					nameSearchMeal.addFoodItem(f);
				}
				List<FoodItem> mealSearched = new ArrayList<FoodItem>();
				mealSearched = nameSearchMeal.filterByName(mealListSearchBar.getText());
				List<String> mealSearchedNames = new ArrayList<String>();
				for(FoodItem f: mealSearched) {
					mealSearchedNames.add(f.getName());
				}
				Collections.sort(mealSearchedNames);
				ObservableList<String> mealSearchObserve = FXCollections.observableArrayList(mealSearchedNames);
				mealList.setItems(mealSearchObserve);
			}
		});
			
		//Left side of the main scene
		VB1.getChildren().addAll(foodListLabel, foodListSearchBar, foodList, 
				addFromList, saveBtn, filterSceneBtn);
		VB1.setSpacing(10);
		
		//Right side of the main scene
		VB2.getChildren().addAll(mealListLabel, mealListSearchBar, 
				mealList, removeFromMeal, analyzeMeal);
		VB2.setSpacing(10);
		
		//Used in bottom of main scene
		VB3.getChildren().addAll(fileInput, fileInputField); 
		
		//Used in center of main scene (mealAnalysis)
		VB4.getChildren().addAll(mealAnalysis, mealCalories, mealCarbohydrates, mealFat, 
				mealFiber, mealProtein);
		VB4.setAlignment(Pos.CENTER);
		
		//Used in bottom of main scene to input individual foods.
		VB5.getChildren().addAll(addIndividual, nameBox, proteinBox, fiberBox);
		VB5.setSpacing(2);
		VB6.getChildren().addAll(idBox, calorieBox, carbohydrateBox, fatBox);
		VB6.setSpacing(2);
		
		//Put all the bottom VBoxes together
		HB.getChildren().addAll(VB3, VB5, VB6);
		
		//The one box used in filters
		FVBMin.getChildren().addAll(minimum, minCal, minCarbs, minFat, 
				minFiber, minProtein, rootBtn);
		FVBMax.getChildren().addAll(maximum, maxCal, maxCarbs, maxFat, 
				maxFiber, maxProtein);
		FVBAddRules.getChildren().addAll(addGap, addCalRule, addCarbRule, addFatRule, 
				addFiberRule, addProteinRule);
		FVBRemoveRules.getChildren().addAll(removeGap, removeCalRule, removeCarbRule, removeFatRule, 
				removeFiberRule, removeProteinRule);
		FHBMainBox.getChildren().addAll(FVBMin, FVBLabelNames, FVBMax, FVBAddRules, FVBRemoveRules);
		
		//Putting top level boxes in correct locations
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
		filterBorderPanel.setCenter(FHBMainBox);
		filter.getChildren().add(filterBorderPanel);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
}