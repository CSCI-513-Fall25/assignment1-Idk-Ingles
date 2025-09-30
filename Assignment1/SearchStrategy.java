package test; // base interface for all strategies

public interface SearchStrategy {
	// search the grid for 8 ship coordinates
	public int[][] search(int[][] grid, int[][] coordinates); 

	// return how many cells were searched in process
	public int getCellCount(); 
}
