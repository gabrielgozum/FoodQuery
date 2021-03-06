package application;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * Filename: Main.java
 * Project: p5 (final project)
 * 
 * This class represents the JavaFx GUI portion of the project.
 * Here, all the functionality and initializations are performed,
 * which operate on the back-end of FoodData and BPTree.
 * 
 * @author Diego Fratta
 * 
 * No known bugs.
 *
 */

public class Main extends Application{
	
	//Keeps track of how many FoodItems are being displayed
	private int count = 0;
	
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
	
	/*
	 * Used in creating rules, filter(NutrientName) is the value associated
	 * with the nutrient when being filtered. They are Strings because rules
	 * are of type String, they get converted to doubles in filterByNutrient
	 */
	private String filterCalorie;
	private String filterCarbs;
	private String filterFat;
	private String filterFiber;
	private String filterProtein;
	
	private String calorieOperator;
	private String carbOperator;
	private String fatOperator;
	private String fiberOperator;
	private String proteinOperator;
	
	private String calorieRule = "";
	private String carbRule = "";
	private String fatRule = "";
	private String fiberRule = "";
	private String proteinRule = "";
	
	public static void main(String[] args) {
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
		Scene filterScene = new Scene(filter, 580, 250);
		
		//Main (root) boxes
		VBox VB1 = new VBox(); 	//Used in left Border
		VBox VB2 = new VBox();	//Used in right border
		VBox VB3 = new VBox();	//Used in bottom border in HB
		VBox VB4 = new VBox();	//Used in the center
		VBox VB5 = new VBox(); 	//Used in bottom border in HB
		VBox VB6 = new VBox(); 	//Used in bottom border in HB
		HBox HB = new HBox();	//Used in bottom border
		HBox HB1 = new HBox();  //Used in top border
		HBox nameBox = new HBox();
		HBox proteinBox = new HBox();
		HBox fiberBox = new HBox();
		HBox calorieBox = new HBox();
		HBox carbohydrateBox = new HBox();
		HBox fatBox = new HBox();
		HBox idBox = new HBox();
		
		//Filter boxes
		VBox FVBOperator = new VBox();
		VBox FVBLabelNames = new VBox();
		VBox FVBValues = new VBox();
		VBox FVBAddRules = new VBox();
		VBox FVBRemoveRules = new VBox();
		HBox FHBMainBox = new HBox();
		HBox FHBBottomBox = new HBox();
		
		//All the buttons
		Button analyzeMeal = new Button(); 		//will calculate nutrition totals
		Button filterSceneBtn = new Button(); 	//changes to filter scene
		Button rootBtn = new Button();			//changes back to root
		Button applyBtn = new Button();			//changes back to root and filters
		Button addIndividual = new Button();	//Adds individual FoodItems
		Button saveBtn = new Button();			//Saves the current FoodList
		//Used for adding and removing filter rules for each nutrient
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
		rootBtn.setText("Go back without applying filters");
		applyBtn.setText("Apply filters and go back");
		filterSceneBtn.setText("Click to apply Filters");
		analyzeMeal.setText("Click to analyze your meal");
		
		//Labels seen on the root scene
		Label spacingTop = new Label("            ");
		spacingTop.setFont(new Font("Arial", 30));
		Label topLabel = new Label("FoodQuery and Meal Analysis");
		topLabel.setFont(new Font("Arial", 30));
		topLabel.setUnderline(true);
		//Label searchHelp = new Label("Perform an empty search \nto get full list back.");
		//searchHelp.setFont(new Font("Arial", 8));
		Label fileInput = new Label();
		Label foodName = new Label("Name:     ");
		Label foodProtein = new Label("Protein:   ");
		Label foodFiber = new Label("Fiber:      ");
		Label foodCalories = new Label("  Calories:               ");
		Label foodCarbs = new Label("  Carbohydrates:    ");
		Label foodFat = new Label("  Fat:                       ");
		Label foodID = new Label("  ID:                        ");
		Label foodListLabel = new Label("Food List");
		Label foodListCount = new Label(count + " food items displayed");
		foodListLabel.setFont(new Font("Arial" , 15));
		Label mealListLabel = new Label("Meal List");
		mealListLabel.setFont(new Font("Arial" , 15));
		fileInput.setText("Enter your file name:");
		
		//Labels for total nutrients
		Label mealCalories = new Label("Total Calories: ");
		mealCalories.setFont(new Font("Arial", 20));
		Label mealCarbohydrates = new Label("Total Carbs: ");
		mealCarbohydrates.setFont(new Font("Arial", 20));
		Label mealFat = new Label("Total Fat: ");
		mealFat.setFont(new Font("Arial", 20));
		Label mealFiber = new Label("Total Fiber: ");
		mealFiber.setFont(new Font("Arial", 20));
		Label mealProtein = new Label("Total Protein: ");
		mealProtein.setFont(new Font("Arial", 20));
		Label mealAnalysis = new Label("Anaylzed Meal:");
		mealAnalysis.setFont(new Font("Arial", 30));
		mealAnalysis.setUnderline(true);
		Label addFromList = new Label("Click from list to add to meal");
		Label removeFromMeal = new Label("Click from meal list to remove");
		
		//Labels used on the filter scene
		Label operator = new Label("Operator");
		Label value = new Label("Value");
		Label nutrientGap = new Label("		");	//Used for aesthetic
		Label addGap = new Label("		");
		Label removeGap = new Label("		");
		Label calFilter = new Label(" Calories ");
		Label carbFilter = new Label(" Carbs");
		Label fatFilter = new Label(" Fat");
		Label fiberFilter = new Label(" Fiber");
		Label proteinFilter = new Label(" Protein");
		Label filterDescribe = new Label(" Filter your search by typing one of the operators '==',"
				+ "' <=', or '>= then the value you wish to use that \n operator on with the respective nutrient."
				+ " Then click the 'Add Rule' button. To remove a rule, hit the \n 'Remove Rule'"
				+ " button. Remember to hit the apply button at the end.");
		FVBLabelNames.getChildren().addAll(nutrientGap, calFilter, carbFilter,
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
		TextField operatorCal = new TextField();
		TextField filterValueCal = new TextField();
		TextField operatorCarbs = new TextField();
		TextField filterValueCarbs = new TextField();
		TextField operatorFat = new TextField();
		TextField filterValueFat = new TextField();
		TextField operatorFiber = new TextField();
		TextField filterValueFiber = new TextField();
		TextField operatorProtein = new TextField();
		TextField filterValueProtein = new TextField();
		
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
		Alert invalidOperator = new Alert(AlertType.ERROR);
		invalidOperator.setTitle("Operator Error");
		invalidOperator.setContentText("The operator must be '==', '>=', or '<='.");
		Alert invalidRule = new Alert(AlertType.ERROR);
		invalidRule.setTitle("Rule Error");
		invalidRule.setContentText("Your rule is invalid, you had a null or empty value"
				+ " or operator.");
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
		//Keep tracks of what is currently displayed to the GUI
		List<FoodItem> displayedItems = new ArrayList<FoodItem>();
		List<String> rules = new ArrayList<String>();
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
						displayedItems.add(f);
					}
				}
				//Make the list of foodItemNames into an ObservableList to be displayed.
				ObservableList<String> displayFood = FXCollections.observableArrayList(foodListNames);
				Collections.sort(displayFood);	//sort the list in alphabetical order
				foodList.setItems(displayFood);
				count = foodListNames.size();
				foodListCount.setText(count + " food items displayed");
			}
		});
		
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
		 * Filters are not applied.
		 */
		rootBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				primaryStage.setScene(mainScene);
			}
		});
		
		/*
		 * When applyBtn is pressed, the scene changes to the main scene.
		 * Filters are applied here as well.
		 */
		applyBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				//Clear the rules to make sure they are applied correctly
				rules.clear();
				if(!calorieRule.equals("")) {
					rules.add(calorieRule);
				}
				if(!carbRule.equals("")) {
					rules.add(carbRule);
				}
				if(!fatRule.equals("")) {
					rules.add(fatRule);
				}
				if(!fiberRule.equals("")) {
					rules.add(fiberRule);
				}
				if(!proteinRule.equals("")) {
					rules.add(proteinRule);
				}
				//Make a FoodData object with all FoodItems
				FoodData filtering = new FoodData();
				for(FoodItem f: foodListItems) {
					filtering.addFoodItem(f);
				}
				//Put the BPTrees and nutrients in the FoodData object
				filtering.getIndexes().put("calories", new BPTree<Double,FoodItem>(3));
				filtering.getIndexes().put("carbohydrate", new BPTree<Double, FoodItem>(3));
				filtering.getIndexes().put("fat", new BPTree<Double, FoodItem>(3));
				filtering.getIndexes().put("fiber", new BPTree<Double, FoodItem>(3));
				filtering.getIndexes().put("protein", new BPTree<Double, FoodItem>(3));
				String[] names = {"calories",
				"carbohydrate", "fat", "fiber", "protein"};
				for (int i = 0; i < 5; i++){
					BPTree<Double, FoodItem> tree = filtering.getIndexes().get(names[i]);
					for (FoodItem fi : filtering.getAllFoodItems()){
						tree.insert(fi.getNutrientValue(names[i]), fi);
					}
				}
				//Start filtering the FoodData object
				List<FoodItem> filteredFoods = new ArrayList<FoodItem>();
				filteredFoods = filtering.filterByNutrients(rules);
				List<String> filteredNames = new ArrayList<String>();
				displayedItems.clear();
				for(FoodItem f : filteredFoods) {
					filteredNames.add(f.getName());
					displayedItems.add(f);
				}
				//Display the sorted filtered list
				Collections.sort(filteredNames);
				ObservableList<String> filterNamesObserve = FXCollections.observableArrayList(filteredNames);
				primaryStage.setScene(mainScene);
				foodList.setItems(filterNamesObserve);
				count = filterNamesObserve.size();
				foodListCount.setText(count + " food items displayed");
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
			    String testStr = foodNameField.getText();
				if(!testStr.equals("")) {
				   newName = testStr;
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
				 * If the ID or name is null, or matches an existing 
				 * item's ID or name, don't make a new FoodItem, prompt
				 * the user.
				 */
			    for(FoodItem f : foodListItems) {
                    if(newID == f.getID()) {
                        dupID.showAndWait();
                        return;
                    }
                    if(newName == f.getName()) {
                        dupName.showAndWait();
                        return;
                    }
                }
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
					displayedItems.add(newFood);
					Collections.sort(foodListNames);
					ObservableList<String> foodListObserve = FXCollections.observableArrayList(foodListNames);
					foodList.setItems(foodListObserve);
					count = foodListObserve.size();
					foodListCount.setText(count + " food items displayed");
				}
			}
		});
		
		/*
		 * Saves the current FoodList being displayed to a filepath of users
		 * choice. Gets every foodItem in the foodList and puts it 
		 * into a new FoodData object to read from to write to the new file.
		 */
		saveBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FoodData writeFile = new FoodData();
				for(FoodItem f: displayedItems) {
					writeFile.addFoodItem(f);
				}
				//Prompt user
				TextInputDialog getFilePath = new TextInputDialog();
				getFilePath.setHeaderText("Input Filepath");
				getFilePath.setContentText("Please enter the filepath you wish to save to");
				Optional<String> filePath = getFilePath.showAndWait();
				if(filePath.isPresent()) {
					writeFile.saveFoodItems(filePath.get());
				}
			}
		});
		
		/*
		 * Handles searching the FoodList by names of the FoodItems in it.
		 * Example: Searching for 'yes' displays all FoodItems that have
		 * the string 'yes' in it. Works with filtered lists.
		 */
		foodListSearchBar.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				/*
				 * This section is copied from the applying filters button. It
				 * allows a filtered list to be searched by applying the rules
				 * to create a filteredList, then using that filteredList in
				 * filterByNames.
				 */
				rules.clear();
				if(!calorieRule.equals("")) {
					rules.add(calorieRule);
				}
				if(!carbRule.equals("")) {
					rules.add(carbRule);
				}
				if(!fatRule.equals("")) {
					rules.add(fatRule);
				}
				if(!fiberRule.equals("")) {
					rules.add(fiberRule);
				}
				if(!proteinRule.equals("")) {
					rules.add(proteinRule);
				}
				FoodData filtering = new FoodData();
				for(FoodItem f: foodListItems) {
					filtering.addFoodItem(f);
				}
				filtering.getIndexes().put("calories", new BPTree<Double,FoodItem>(3));
				filtering.getIndexes().put("carbohydrate", new BPTree<Double, FoodItem>(3));
				filtering.getIndexes().put("fat", new BPTree<Double, FoodItem>(3));
				filtering.getIndexes().put("fiber", new BPTree<Double, FoodItem>(3));
				filtering.getIndexes().put("protein", new BPTree<Double, FoodItem>(3));
				String[] names = {"calories",
				"carbohydrate", "fat", "fiber", "protein"};
				for (int i = 0; i < 5; i++){
					BPTree<Double, FoodItem> tree = filtering.getIndexes().get(names[i]);
					for (FoodItem fi : filtering.getAllFoodItems()){
						tree.insert(fi.getNutrientValue(names[i]), fi);
					}
				}
				List<FoodItem> filteredFoods = new ArrayList<FoodItem>();
				filteredFoods = filtering.filterByNutrients(rules);
				/*
				 * Make the FoodData object that the search will be called on 
				 * and add the current FoodItems to it.
				 */
				FoodData nameSearch = new FoodData();
				for(FoodItem f: filteredFoods) {
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
				displayedItems.clear();
				for(FoodItem f: nameSearched) {
					nameSearchedNames.add(f.getName());
					displayedItems.add(f);
				}
				Collections.sort(nameSearchedNames);
				ObservableList<String> nameSearchObserve = FXCollections.observableArrayList(nameSearchedNames);
				foodList.setItems(nameSearchObserve);
				count = nameSearchObserve.size();
				foodListCount.setText(count + " food items displayed");
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
		
		/*
		 * Following lines check whether or not the user has inputted a correct
		 * operator to be used in rules.
		 */
		//Calorie case
		operatorCal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(operatorCal.getText().equals("==") || 
						operatorCal.getText().equals("<=") ||
						operatorCal.getText().equals(">=")) {
					calorieOperator = operatorCal.getText();
				}
				else {
					invalidOperator.showAndWait();
				}
			}
		});
		
		//Carbohydrate case
		operatorCarbs.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(operatorCarbs.getText().equals("==") || 
						operatorCarbs.getText().equals("<=") ||
						operatorCarbs.getText().equals(">=")) {
					carbOperator = operatorCarbs.getText();
				}
				else {
					invalidOperator.showAndWait();
				}
			}
		});
		
		//Fat case
		operatorFat.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(operatorFat.getText().equals("==") || 
						operatorFat.getText().equals("<=") ||
						operatorFat.getText().equals(">=")) {
					fatOperator = operatorFat.getText();
				}
				else {
					invalidOperator.showAndWait();
				}
			}
		});
		
		//Fiber case
		operatorFiber.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(operatorFiber.getText().equals("==") || 
						operatorFiber.getText().equals("<=") ||
						operatorFiber.getText().equals(">=")) {
					fiberOperator = operatorFiber.getText();
				}
				else {
					invalidOperator.showAndWait();
				}
			}
		});
		
		//Protein case
		operatorProtein.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(operatorProtein.getText().equals("==") || 
						operatorProtein.getText().equals("<=") ||
						operatorProtein.getText().equals(">=")) {
					proteinOperator = operatorProtein.getText();
				}
				else {
					invalidOperator.showAndWait();
				}
			}
		});	
		
		//Top side of the main scene
		HB1.getChildren().addAll(spacingTop, topLabel);
		HB1.setSpacing(0);
		/*
		 * Following lines handle taking in the value that the the filter
		 * will use in conjunction with the operator to make rules. The value
		 * must be a positive double. 
		 */
		//Calorie case
		filterValueCal.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String value = filterValueCal.getText();
				try {
					double valueDouble = Double.parseDouble(value);
					if(valueDouble < 0) {
						negative.showAndWait();
					}
					filterCalorie = value;
				}
				catch(NumberFormatException e) {
					notNumber.showAndWait();
				}
			}
		});
		
		//Carbohydrate case
		filterValueCarbs.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				String value = filterValueCarbs.getText();
				try {
					double valueDouble = Double.parseDouble(value);
					if(valueDouble < 0) {
						negative.showAndWait();
					}
					filterCarbs = value;
				}
				catch(NumberFormatException e) {
					notNumber.showAndWait();
				}
			}
		});
		
		//Fat case
		filterValueFat.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				String value = filterValueFat.getText();
				try {
					double valueDouble = Double.parseDouble(value);
					if(valueDouble < 0) {
						negative.showAndWait();
					}
					filterFat = value;
				}
				catch(NumberFormatException e) {
					notNumber.showAndWait();
				}
			}
		});
		
		//Fiber case
		filterValueFiber.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				String value = filterValueFiber.getText();
				try {
					double valueDouble = Double.parseDouble(value);
					if(valueDouble < 0) {
						negative.showAndWait();
					}
					filterFiber = value;
				}
				catch(NumberFormatException e) {
					notNumber.showAndWait();
				}
			}	
		});
		
		//Protein case
		filterValueProtein.setOnAction(new EventHandler<ActionEvent>() {
			@Override 
			public void handle(ActionEvent event) {
				String value = filterValueProtein.getText();
				try {
					double valueDouble = Double.parseDouble(value);
					if(valueDouble < 0) {
						negative.showAndWait();
					}
					filterProtein = value;
				}
				catch(NumberFormatException e) {
					notNumber.showAndWait();
				}
			}	
		});
		
		/*
		 * Following lines describe the Add rule buttons for each nutrient
		 * which creates the rule by putting the nutrient name with the 
		 * operator and respective value. Also make sures that the
		 * operator or value is not null.
		 */
		//Calorie case
		addCalRule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(calorieOperator == null || filterCalorie == null)   {
					invalidRule.showAndWait();
					calorieRule = "";
				}
				else {
					calorieRule = "calories " + calorieOperator + " " + filterCalorie;
				}
			}	
		});
		
		//Carbohydrate case
		addCarbRule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(carbOperator == null || filterCarbs == null) {
					invalidRule.showAndWait();
					carbRule = "";
				}
				else {
					carbRule = "carbohydrate " + carbOperator + " " + filterCarbs;
				}	
			}
		});
		
		//Fat case
		addFatRule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(fatOperator == null || filterFat == null) {
					invalidRule.showAndWait();
					fatRule = "";
				}
				else {
					fatRule = "fat " + fatOperator + " " + filterFat;
				}
			}
		});

		//Fiber case
		addFiberRule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(fiberOperator == null || filterFiber == null) {
					invalidRule.showAndWait();
					fiberRule = "";
				}
				else {
					fiberRule = "fiber " + fiberOperator + " " + filterFiber;
				}
			}
		});
		
		//Protein case
		addProteinRule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(proteinOperator == null || filterProtein == null) {
					invalidRule.showAndWait();
					proteinRule = "";
				}
				else {
					proteinRule = "protein " + proteinOperator + " " + filterProtein;
				}
			}
		});
		
		/*
		 * Following lines handle when the rule is removed, it is just set to 
		 * an empty string that will be checked when filters are applied.
		 */
		//Calorie case
		removeCalRule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				calorieRule = "";
			}
		});
		
		//Carbohydrate case
		removeCarbRule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				carbRule = "";
			}
		});
		
		//Fat case
		removeFatRule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fatRule = "";
			}
		});
		
		//Fiber case
		removeFiberRule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				fiberRule = "";
			}
		});
		
		//Protein case
		removeProteinRule.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				proteinRule = "";
			}
		});
		
		//Left side of the main scene
		VB1.getChildren().addAll(foodListLabel, foodListSearchBar, foodList, 
				foodListCount, addFromList, saveBtn, filterSceneBtn);
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
		FVBOperator.getChildren().addAll(operator, operatorCal, operatorCarbs, operatorFat, 
				operatorFiber, operatorProtein, rootBtn);
		FVBValues.getChildren().addAll(value, filterValueCal, filterValueCarbs, filterValueFat, 
				filterValueFiber, filterValueProtein, applyBtn);
		FVBAddRules.getChildren().addAll(addGap, addCalRule, addCarbRule, addFatRule, 
				addFiberRule, addProteinRule);
		FVBRemoveRules.getChildren().addAll(removeGap, removeCalRule, removeCarbRule, removeFatRule, 
				removeFiberRule, removeProteinRule);
		FHBMainBox.getChildren().addAll(FVBLabelNames, FVBOperator, FVBValues, FVBAddRules, FVBRemoveRules);
		
		//Putting top level boxes in correct locations
		mainBorderPanel.setTop(HB1);
		mainBorderPanel.setLeft(VB1);
		mainBorderPanel.setRight(VB2);
		mainBorderPanel.setBottom(HB);
		mainBorderPanel.setCenter(VB4);
		root.getChildren().add(mainBorderPanel);
		
		/*
		 * Add all the horizontal boxes used in filters to the vertical box on filter
		 * to make it look nice.
		 */
		FHBBottomBox.getChildren().add(filterDescribe);
		filterBorderPanel.setCenter(FHBMainBox);
		filterBorderPanel.setTop(FHBBottomBox);
		filter.getChildren().add(filterBorderPanel);
		primaryStage.setScene(mainScene);
		primaryStage.show();
	}
}