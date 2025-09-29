package test;


public class HorizontalSweepSearchStrategy implements SearchStrategy{

	private int cellCounter;// variable to keep count of number of cells searched
	// search method which was inherited from parent SearchStrategy interface 
 	@Override
	public int[][] search(int[][] grid, int[][] coordinates) {
		// TODO Auto-generated method stub

		int r=0;// variable to keep track of number of ships encountered till the ith iteration
		cellCounter = 0;
		/* We linearly search each cell one by one in the grid starting from top left, moving towards the top right and then search the next row in grid and so on
		 * If we encounter a ship, increment the counter variable r and store in coordinates array. 
		 * Also, increment the cellCounter after every cell search even if a ship is found or not
		*/
		for(int i=0;i<grid.length&&r<8;i++)
		{
			for(int j=0;j<grid[0].length&&r<8;j++)
			{
				if(grid[i][j]==1)
				{
					coordinates[r][0]=i;
					coordinates[r][1]=j;
					r++;
				}
				cellCounter++;				
			}			
		}
		return coordinates;
	}
	// method which was inherited from parent SearchStrategy interface to return number of cells searched
	@Override
	public int getCellCount() {
		// TODO Auto-generated method stub
		return cellCounter;
	}
}
