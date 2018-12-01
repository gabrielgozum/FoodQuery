package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FoodParser 
{

	public static void main(String[] args)
	{
		ArrayList<FoodItem> data = parse("application/foodItems.txt");
		printNames(data);
	}
	
	private static ArrayList<FoodItem> parse(String fileName)
	{
		ArrayList<FoodItem> foodItems = new ArrayList<>();
		
		File file = new File(fileName);
		try {
			Scanner input = new Scanner(file);
			
			while(input.hasNextLine()) {
				String line = input.nextLine();
				String[] data = line.split(",", 12);

				FoodItem cur = new FoodItem(data[0], data[1]); // adds food item with id and name
				
				
				for(int i = 2; i < data.length; i = i + 2)
				{
					if(data[i] != "" && data[i+1] != "")
					{
						// PRODCUES ERROR MAY ... GABE WILL FIX LATER
						System.out.println(Double.parseDouble(data[i+1]));
						cur.addNutrient(data[i], Double.parseDouble(data[i+1]));
					}
				}
				
				foodItems.add(cur); // adds food item with id and name
			}
			input.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	
		return foodItems;
	}
	
	private static void printNames(ArrayList<FoodItem> list)
	{
		for(FoodItem i : list)
		{
			System.out.println(i.getName());
		}
	}
}
