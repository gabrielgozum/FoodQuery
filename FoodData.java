package application;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class represents the backend for managing all 
 * the operations associated with FoodItems
 * 
 * @author sapan (sapan@cs.wisc.edu)
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
        indexes = new HashMap<String, BPTree<Double, FoodItem>>();
        foodItemList = new ArrayList<FoodItem>();
    }
    
    
    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#loadFoodItems(java.lang.String)
     */
    @Override
    public void loadFoodItems(String filePath) {
		try {
			File file = new File(filePath);
			Scanner input = new Scanner(file);
			
			while(input.hasNextLine()) {
				String line = input.nextLine();
				String[] data = line.split(",", 12);

				FoodItem cur = new FoodItem(data[0], data[1]); // adds food item with id and name
				
				
				for(int i = 2; i < data.length; i = i + 2)
				{
					if(!data[i].isEmpty() && !data[i+1].isEmpty())
					{
						cur.addNutrient(data[i], Double.parseDouble(data[i+1])); //add each nutrient
					}
				}
				
				foodItemList.add(cur); // adds food item to LinkedList
			}
			input.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}      
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByName(java.lang.String)
     */
    @Override
    public List<FoodItem> filterByName(String substring) {
        // TODO : Complete
        return null;
    }

    /*
     * (non-Javadoc)
     * @see skeleton.FoodDataADT#filterByNutrients(java.util.List)
     */
    @Override
    public List<FoodItem> filterByNutrients(List<String> rules) {
        // TODO : Complete
        return null;
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
    	//MAY BE IN REVERSE SORTED ORDER
        try
        {
	    	File file = new File(filename);
	    	BufferedWriter out = new BufferedWriter(new FileWriter(file));
	    	
	    	Comparator<FoodItem> foodComparator = (a, b) -> a.getName().compareTo(b.getName());
	    	ArrayList<FoodItem> sortedNames = (ArrayList<FoodItem>) foodItemList.stream().sorted(foodComparator).collect(Collectors.toList());
	    	
	    	String line;
	        for(FoodItem i : sortedNames)
	        {
	        	line = i.getID() + "," + i.getName();
	        	HashMap<String, Double> nutrients = i.getNutrients();
	        	for(String j : nutrients.keySet())
	        	{
	        		line = line + "," + j + "," + Double.toString(nutrients.get(j));
	        	}
	        	out.write(line);
	        	out.newLine(); //possibly unnecessary
	        }
	        out.close();
        } catch (Exception e)
        {
        	e.getMessage();
        }
    }
}
