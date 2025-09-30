package test; // put this class in the "test" package

import java.io.BufferedReader; // used for reading text line by line
import java.io.FileNotFoundException; // thrown when file path is wrong
import java.io.FileReader; // reads raw characters from file
import java.io.IOException; // handles IO related issues
import java.util.Arrays; // utility for sorting
import java.util.Comparator; // used for custom sorting
import java.util.regex.Matcher; // regex matcher object
import java.util.regex.Pattern; // regex pattern compiler

public class BattleShipSearch {
	private int[][] grid;	// 25x25 grid to hold ship positions
	private int[][] coordinates; // stores 8 ship coordinates
	private int cellCounter; // total number of cells searched
	private SearchStrategy searchStrategy; // current search strategy in use

	public BattleShipSearch() {
		// default strategy starts as horizontal sweep
		searchStrategy = new HorizontalSweepSearchStrategy();
	}

	public SearchStrategy getSearchStrategy() {
		// getter to fetch currently set strategy
		return searchStrategy;
	}

	public void setSearchStrategy(SearchStrategy searchStrategy) {
		// setter to swap in a new strategy dynamically
		this.searchStrategy = searchStrategy;
	}

	public void readInputFile(String filePath) {
		// tries to read input.txt file line by line
		try {
			FileReader fileReader = new FileReader(filePath); // open file
			try (BufferedReader bufferedReader = new BufferedReader(fileReader)) { // wrap in buffered reader
				String line = bufferedReader.readLine(); // read first line
				while (line != null) { // loop until file ends
					String regex = "\\(([0-9]+),([0-9]+)\\)"; // regex for (x,y)
					Pattern p = Pattern.compile(regex); // compile regex
					Matcher matcher = p.matcher(line); // match regex on line

					grid = new int[25][25]; // reset grid
					coordinates = new int[8][2]; // reset coordinates

					while (matcher.find()) { // find all (x,y)
						int x = Integer.parseInt(matcher.group(1)); // extract x
						int y = Integer.parseInt(matcher.group(2)); // extract y
						grid[x][y] = 1; // mark ship position
					}

					// delegate searching work to active strategy
					coordinates = searchStrategy.search(grid, coordinates);

					// after searching, print identified coordinates
					printCoordinates();

					line = bufferedReader.readLine(); // move to next line
				}
			} catch (NumberFormatException e) {
				// handles invalid number formats in input
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// triggered if file not found at path
			e.printStackTrace();
		} catch (IOException e) {
			// triggered if IO operation fails
			e.printStackTrace();
		}
	}

	public void printCoordinates() {
		// fetch count of cells searched from strategy
		cellCounter = searchStrategy.getCellCount();
		System.out.println("Cells searched: " + cellCounter);

		Arrays.sort(coordinates, Arrays::compare); // sort by x then y

		// case 1: first 5 coordinates have same x → carrier
		if (coordinates[0][0] == coordinates[4][0]) {
			System.out.println("Carrier found: (" + coordinates[0][0] + "," + coordinates[0][1] + ") to (" 
					+ coordinates[4][0] + "," + coordinates[4][1] + "). Submarine found: (" 
					+ coordinates[5][0] + "," + coordinates[5][1] + ") to (" 
					+ coordinates[7][0] + "," + coordinates[7][1] + ")");
			return; // end function
		}
		// case 2: last 5 coordinates share same x → carrier
		else if (coordinates[3][0] == coordinates[7][0]) {
			System.out.println("Carrier found: (" + coordinates[3][0] + "," + coordinates[3][1] + ") to (" 
					+ coordinates[7][0] + "," + coordinates[7][1] + "). Submarine found: (" 
					+ coordinates[0][0] + "," + coordinates[0][1] + ") to (" 
					+ coordinates[2][0] + "," + coordinates[2][1] + ")");
			return;
		}

		// if not clear, sort by Y coordinate
		Arrays.sort(coordinates, Comparator.comparingInt(o -> o[1]));

		// case 3: first 5 share Y → carrier
		if (coordinates[0][1] == coordinates[4][1]) {
			System.out.println("Carrier found: (" + coordinates[0][0] + "," + coordinates[0][1] + ") to (" 
					+ coordinates[4][0] + "," + coordinates[4][1] + "). Submarine found: (" 
					+ coordinates[5][0] + "," + coordinates[5][1] + ") to (" 
					+ coordinates[7][0] + "," + coordinates[7][1] + ")");
			return;
		}
		// case 4: last 5 share Y → carrier
		else if (coordinates[3][1] == coordinates[7][1]) {
			System.out.println("Carrier found: (" + coordinates[3][0] + "," + coordinates[3][1] + ") to (" 
					+ coordinates[7][0] + "," + coordinates[7][1] + "). Submarine found: (" 
					+ coordinates[0][0] + "," + coordinates[0][1] + ") to (" 
					+ coordinates[2][0] + "," + coordinates[2][1] + ")");
			return;
		}
	}

	public static void main(String[] args) {
		BattleShipSearch battleShipSearch = new BattleShipSearch(); // create object
		String filePath = "src\\input.txt"; // file location

		System.out.println("Horizontal Sweep Search:"); // announce strategy
		battleShipSearch.readInputFile(filePath); // run sweep

		battleShipSearch.setSearchStrategy(new RandomSearchStrategy()); // switch to random
		System.out.println("Random Cells Search:");
		battleShipSearch.readInputFile(filePath); // run random

		battleShipSearch.setSearchStrategy(new EfficientSearchStrategy()); // switch to efficient
		System.out.println("Strategic Cells Search:");
		battleShipSearch.readInputFile(filePath); // run efficient
	}
}
