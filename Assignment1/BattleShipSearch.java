package test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// This is the client class which can set the type of strategy to search for Carrier and Submarine
public class BattleShipSearch{
	private int[][] grid;	// Variable to hold the position of the ships in 2D Array
	private int[][] coordinates; // Variable to store the x and y coordinates of submarine and carrier
	private int cellCounter; // Variable to keep count of number of cells searched
	private SearchStrategy searchStrategy; // Client class has an object reference of search strategy interface implying Has a relation.
	public BattleShipSearch() {
		// Setting the default strategy to Horizontal Sweep Search
		searchStrategy = new HorizontalSweepSearchStrategy();
	}
	public SearchStrategy getSearchStrategy() {
		return searchStrategy;
	}
	public void setSearchStrategy(SearchStrategy searchStrategy) {
		this.searchStrategy = searchStrategy;
	}
	// Function to read input file and populate grid array
	public void readInputFile(String filePath)
	{
		try 
		{
			FileReader fileReader = new FileReader(filePath);
			try (BufferedReader bufferedReader = new BufferedReader(fileReader)) 
			{
				String line = bufferedReader.readLine();
				while(line!=null)
				{
					String regex = "\\(([0-9]+),([0-9]+)\\)";
					Pattern p = Pattern.compile(regex);
					Matcher matcher = p.matcher(line);
					grid = new int [25][25];
					coordinates = new int[8][2];
					while(matcher.find())
					{
						int x = Integer.parseInt(matcher.group(1));
						int y = Integer.parseInt(matcher.group(2));						
						grid[x][y] = 1;
					}
					// Every new line in input file is a new scenario -> new grid, so we call search method for every new grid
					// Calls the search method of interface SearchStrategy. Using Polymorphism OOO principle here
					// This search method blindly collects the coordinates of ships without knowing if a specific coordinate is for submarine or carrier 
					coordinates=searchStrategy.search(grid, coordinates);
					printCoordinates();// Once all the coordinates are collected, printCoordinates method will determine specific coordinates of submarine and carrier 
					line = bufferedReader.readLine();
				}
			} 
			catch (NumberFormatException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 
		catch (FileNotFoundException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// Method to calculate and determine specific coordinates of Submarine and Carrier
	/* First sorting the coordinates array based on X coordinate. 
	If the X coordinate of first 5 elements of coordinates array is same, 
	then it implies that first 5 elements/coordinates are of Carrier and next 3 elements/coordinates are of Submarine
	Else If the X coordinate of last 5 elements of coordinates array is same,
	then it implies that last 5 elements/coordinates are of Carrier and first 3 elements/coordinates are of Submarine
	If neither of the above 2 conditions are met then we can sort coordinates array based on Y coordinate 
	and compare the Y coordinates of top 5/last 5 elements to determine Carrier and Submarine positions 
	*/
	public void printCoordinates()
	{
		cellCounter = searchStrategy.getCellCount();// search strategy interface has an abstract method to return number of cells searched
		System.out.println("Cells searched: " + cellCounter);
		Arrays.sort(coordinates, Arrays::compare);
		if(coordinates[0][0]==coordinates[4][0])
		{
			System.out.println("Carrier found: (" + coordinates[0][0] + "," + coordinates[0][1] + ") to (" + coordinates[4][0] + "," + coordinates[4][1] 
					+ "). Submarine found: " + "(" + coordinates[5][0] + "," + coordinates[5][1] + ") to (" + coordinates[7][0] + "," + coordinates[7][1] + ")");
			return;
		}
		else if(coordinates[3][0]==coordinates[7][0])
		{
			System.out.println("Carrier found: (" + coordinates[3][0] + "," + coordinates[3][1] + ") to (" + coordinates[7][0] + "," + coordinates[7][1] 
					+ "). Submarine found: " + "(" + coordinates[0][0] + "," + coordinates[0][1] + ") to (" + coordinates[2][0] + "," + coordinates[2][1] + ")");
			return;
		}
		Arrays.sort(coordinates, Comparator.comparingInt(o->o[1]));
		if(coordinates[0][1]==coordinates[4][1])
		{
			System.out.println("Carrier found: (" + coordinates[0][0] + "," + coordinates[0][1] + ") to (" + coordinates[4][0] + "," + coordinates[4][1] 
					+ "). Submarine found: " + "(" + coordinates[5][0] + "," + coordinates[5][1] + ") to (" + coordinates[7][0] + "," + coordinates[7][1] + ")");
			return;
		}
		else if(coordinates[3][1]==coordinates[7][1])
		{
			System.out.println("Carrier found: (" + coordinates[3][0] + "," + coordinates[3][1] + ") to (" + coordinates[7][0] + "," + coordinates[7][1] 
					+ "). Submarine found: " + "(" + coordinates[0][0] + "," + coordinates[0][1] + ") to (" + coordinates[2][0] + "," + coordinates[2][1] + ")");
			return;
		}
	}
	public static void main(String[] args)
	{
		BattleShipSearch battleShipSearch = new BattleShipSearch();
		String filePath = "src\\input.txt";
		System.out.println("Horizontal Sweep Search:");
		battleShipSearch.readInputFile(filePath);		
		battleShipSearch.setSearchStrategy(new RandomSearchStrategy()); // Changing the Search Strategy to Random Cell Search
		System.out.println("Random Cells Search:");
		battleShipSearch.readInputFile(filePath);
		battleShipSearch.setSearchStrategy(new EfficientSearchStrategy()); // Changing the Search Strategy to Strategic Cell Search
		System.out.println("Strategic Cells Search:");
		battleShipSearch.readInputFile(filePath);
	}
}
