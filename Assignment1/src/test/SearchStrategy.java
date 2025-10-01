package test; 

public interface SearchStrategy {
	// Here, we search the grid for 8 ship coordinates
	public int[][] search(int[][] grid, int[][] coordinates); 

	// Basic us returning how many cells it's got
	public int getCellCount(); 
}
