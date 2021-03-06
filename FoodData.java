package application;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * Filename: FoodData.java
 * Project: p5 (final project)
 * 
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu)
 * Contributors: Desmond Fung, Jarred Hanson, Gabriel Gozum
 * 
 * No known bugs.
 */
public class FoodData implements FoodDataADT<FoodItem> {
    
    // List of all the food items.
    private List<FoodItem> foodItemList;

    // Map of nutrients and their corresponding index
    private HashMap<String, BPTree<Double, FoodItem>> indexes;
    
    
    /**
     * Public constructor
     */
    public FoodData() {
    	// initialize global variables
        indexes = new HashMap<String, BPTree<Double, FoodItem>>();
        foodItemList = new ArrayList<FoodItem>();
  
        // BPTrees for nutrients
        indexes.put("calories", new BPTree<Double, FoodItem>(3));
        indexes.put("carbohydrate", new BPTree<Double, FoodItem>(3));
        indexes.put("fiber", new BPTree<Double, FoodItem>(3));
        indexes.put("fat", new BPTree<Double, FoodItem>(3));
        indexes.put("protein", new BPTree<Double, FoodItem>(3));
    }
    
    /**
     * Protected helper method to returns the HashMap Index
     * @return
     */
    protected HashMap<String, BPTree<Double, FoodItem>> getIndexes(){
    	return this.indexes;
    }
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath) {
    	try 
    	{
    		// Initialize file and scanner objects
			File file = new File(filePath); 
			Scanner input = new Scanner(file);
			
			FoodItem cur = null; // FoodItem to be added to List
			boolean skipLine = false; // used for incorrect format
			
			while(input.hasNextLine()) {
				String line = input.nextLine();
				try 
				{
					String[] data = line.split(",", 12); // split csv line into separate strings
					skipLine = false; // initialize skipLine to false

					cur = new FoodItem(data[0], data[1]); // adds food item with id and name
					
					for(int i = 2; i < data.length; i = i + 2)
					{
							if(data[i].isEmpty() || data[i+1].isEmpty()) // check if index of array empty
							{
								skipLine = true;
							}
							else
							{
								cur.addNutrient(data[i], Double.parseDouble(data[i+1])); // add each nutrient
							}
					}
				} catch (Exception e) // accounts for ArrayOutOfBounds and Double.parseInt exceptions for incorrect data
				{
					skipLine = true;
				} 
				
				if(!skipLine)
				{
					foodItemList.add(cur); // adds food item to LinkedList
					nutritionInsert(cur);  // adds nutrients to index
				}
			}
			input.close();
			
		// If the file is not found, prompt user.
		} catch (FileNotFoundException e) {
			Alert fileAlert = new Alert(AlertType.WARNING);
			fileAlert.setTitle("File Error");
			fileAlert.setContentText("The file: " + filePath + 
					" was not found (Check your spelling?)");
			fileAlert.showAndWait();
		} catch (Exception e)
		{
			e.printStackTrace();
		}      
    }
    
    /**
     * Private helper method to add nutritional data to index
     * @param f
     */
    private void nutritionInsert(FoodItem f)
    {
    	HashMap<String, Double> nutrients = f.getNutrients();
    	
    	// Add each nutrient/value pair in HashMap to nutrient's BPTree
    	for(String i : nutrients.keySet())
    	{
    		indexes.get(i).insert(nutrients.get(i), f);
    	}
    }

    /**
     * filterByNutrients gets all the food items that have name containing the substring.
     * 
     * @param substring substring to be searched
     * @return list of filtered food items; if no food item matched, return empty list
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
        //turn foodItemList into a stream to filter the list and check if any food item matched 
        return foodItemList.stream().filter(x -> x.getName().toLowerCase() 
            .contains(substring.toLowerCase())).collect(Collectors.toList());
    }

    /**
     * filterByNutrients gets all the food items that fulfill aLL the provided rules
     * and return them in a list
     * 
     * @param rules a FoodItemADT object must satisfy
     * @return list of filtered food items; if no food item matched, return empty list
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
    	// copy foodItemList
    	List<FoodItem> filtered = new ArrayList<FoodItem>(foodItemList); 
    	String[] ruleArray;
    	
    	// iterate over inputed rules List
    	for(String r : rules) {
    		ruleArray = r.split(" "); // slit array into individual strings
    		ruleArray[0] = ruleArray[0].toLowerCase(); // case insensitive for nutrients
    		
    		// obtain nutrient and value pair
    		BPTree<Double, FoodItem> bpTree = indexes.get(ruleArray[0]);
    		double doubleValue = Double.parseDouble(ruleArray[2]);
    		
    		// create a list of FoodItems corresponding to filter
    		List<FoodItem> list = bpTree.rangeSearch(doubleValue, ruleArray[1]);
    		filtered = filtered.stream().filter(x -> list.contains(x)).collect(Collectors.toList());
    	}
    	
    	return filtered; 
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#addFoodItem(skeleton.FoodItem)
     */
    @Override
    public void addFoodItem(FoodItem foodItem) {
        foodItemList.add(foodItem);
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#getAllFoodItems()
     */
    @Override
    public List<FoodItem> getAllFoodItems() {
        return foodItemList;
    }


    @Override
    public void saveFoodItems(String filename) {
        try
        {
        	// Initialize file writing structures
	    	File file = new File(filename);
	    	BufferedWriter out = new BufferedWriter(new FileWriter(file));
	    	
	    	Comparator<FoodItem> foodComparator = (a, b) -> a.getName().compareTo(b.getName());  // Sort by name comparator
	    	ArrayList<FoodItem> sortedNames = (ArrayList<FoodItem>) foodItemList.stream().sorted(foodComparator).collect(Collectors.toList()); //ArrayList of sorted names
	    	
	    	String line; // line to be appended to saved file
	        for(FoodItem i : sortedNames)
	        {
	        	line = i.getID() + "," + i.getName(); // add ID and name to line
	        	
	        	// add nutrients to line
	        	HashMap<String, Double> nutrients = i.getNutrients(); 
	        	for(String j : nutrients.keySet())
	        	{
	        		line = line + "," + j + "," + Double.toString(nutrients.get(j));
	        	}
	        	out.write(line);
	        	out.newLine();
	        }
	        out.close();
        } catch (IOException e) {
        	Alert fileAlert = new Alert(AlertType.WARNING);
			fileAlert.setTitle("File Error");
			fileAlert.setContentText("The file: " + filename + " could not be saved (Check your spelling?)");
			fileAlert.showAndWait();
        }
    }
}
