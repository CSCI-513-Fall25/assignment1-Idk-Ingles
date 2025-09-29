package test;


public class EfficientSearchStrategy implements SearchStrategy{

	private int cellCounter = 0;// variable to keep count of number of cells searched
	private int totalShips = 0;//variable to keep track of number of ships encountered
	private boolean[][] visited;//variable to track the cells already visited so that we don't have to revisit the same cell again
	
	// search method which was inherited from parent SearchStrategy interface 
	/*
	 * In Strategic search, we search the cells in diagonal pattern, in this pattern search, we eliminate 2/3rd of cells and search only 1/3 cells in worst case
	 * If we encounter a ship i.e., a grid cell with value 1, then it is possible that surrounding cells also contain 1
	 * So whenever we encounter grid cell whose value is 1, we search cell immediately above this cell, cell immediately below, cell immediately to the right and left
	 * We continue the search in this manner till we find all the coordinates(8) in grid.    
	 * */
	@Override
	public int[][] search(int[][] grid, int[][] coordinates) {
		// TODO Auto-generated method stub
		coordinates = new int[8][2]; // variable to store the X and Y coordinates of Submarine and Carrier
		cellCounter=0;
		totalShips=0;
		visited = new boolean[25][25]; 
		for(int i=0;i<25&&totalShips<8;i+=3)
		{
			for(int j=0;j<25&&totalShips<8;j+=3)
			{				
				int x=i,y=j,k=0;
				while(k<3&&totalShips<8&&x<25&&y<25&&!visited[x][y])
				{					
					if(grid[x][y]==1)
					{		
						coordinates[totalShips][0]=x;coordinates[totalShips][1]=y;
						totalShips++;
						searchSurroundings(grid,coordinates,x,y);// Once we encounter a grid cell with 1, we need to search its surroundings for immediate coordinates
					}
					cellCounter++;visited[x][y]=true;	
					k++;x++;y++;
				}
			}			
		}
		return coordinates;
	}
	// If we encounter a grid cell with value 1, we need to search cell above, below, to the right and to the left of this cell and continue in that direction. 
	public void searchSurroundings(int[][] grid, int[][] coordinates, int x, int y)
	{
		//Search Cells Above
		int r=x-1;
		while(r<25 && r>=0 && totalShips<8 && !visited[r][y])
		{
			if(grid[r][y]==1)
			{
				coordinates[totalShips][0]=r;coordinates[totalShips][1]=y;				
				totalShips++;				
			}			
			cellCounter++;	visited[r][y]=true;
			if(grid[r][y]!=1)
				break;
			r--;			
		}
		//Search Cells Below
		r=x+1;
		while(r<25 && r>=0 && totalShips<8 && !visited[r][y])
		{
			if(grid[r][y]==1)
			{
				coordinates[totalShips][0]=r;coordinates[totalShips][1]=y;
				totalShips++;				
			}				
			cellCounter++;visited[r][y]=true;
			if(grid[r][y]!=1)break;
			r++;
		}
		//Search Cells Left
		int c=y-1;
		while(c<25 && c>=0 && totalShips<8 && !visited[x][c])
		{
			if(grid[x][c]==1)
			{
				coordinates[totalShips][0]=x;coordinates[totalShips][1]=c;
				totalShips++;				
			}				
			cellCounter++;visited[x][c]=true;
			if(grid[x][c]!=1)break;
			c--;
		}
		//Search Cells Right
		c=y+1;
		while(c<25 && c>=0 & totalShips<8 && !visited[x][c])
		{
			if(grid[x][c]==1)
			{
				coordinates[totalShips][0]=x;coordinates[totalShips][1]=c;
				totalShips++;				
			}				
			cellCounter++;visited[x][c]=true;
			if(grid[x][c]!=1)break;
			c++;
		}
	}
	// method which was inherited from parent SearchStrategy interface to return number of cells searched 
	@Override
	public int getCellCount() {
		// TODO Auto-generated method stub
		return cellCounter;
	}
}
