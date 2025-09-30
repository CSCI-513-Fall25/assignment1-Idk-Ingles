package test; // class is part of "test" package

public class EfficientSearchStrategy implements SearchStrategy {
	private int cellCounter = 0; // tracks how many cells we scanned
	private int totalShips = 0;  // how many ship parts we’ve found so far
	private boolean[][] visited; // marks which cells we’ve already checked

	@Override
	public int[][] search(int[][] grid, int[][] coordinates) {
		// reset coordinates storage to 8 slots
		coordinates = new int[8][2]; 
		cellCounter = 0; // reset counter
		totalShips = 0; // reset ship counter
		visited = new boolean[25][25]; // track visited cells

		// jump in diagonal steps across the grid
		for (int i = 0; i < 25 && totalShips < 8; i += 3) {
			for (int j = 0; j < 25 && totalShips < 8; j += 3) {
				int x = i, y = j, k = 0; // set initial point
				// step diagonally 3 times or until ships found
				while (k < 3 && totalShips < 8 && x < 25 && y < 25 && !visited[x][y]) {
					if (grid[x][y] == 1) { // found a ship
						coordinates[totalShips][0] = x; // store X
						coordinates[totalShips][1] = y; // store Y
						totalShips++; // increase ship count
						searchSurroundings(grid, coordinates, x, y); // explore around hit
					}
					cellCounter++; // always increase cells checked
					visited[x][y] = true; // mark visited
					k++; // move step
					x++; y++; // go diagonally
				}
			}
		}
		return coordinates; // return collected coordinates
	}

	public void searchSurroundings(int[][] grid, int[][] coordinates, int x, int y) {
		// go upward from the current hit
		int r = x - 1;
		while (r < 25 && r >= 0 && totalShips < 8 && !visited[r][y]) {
			if (grid[r][y] == 1) { // found a ship above
				coordinates[totalShips][0] = r;
				coordinates[totalShips][1] = y;
				totalShips++;
			}
			cellCounter++; // searched a cell
			visited[r][y] = true; // mark as visited
			if (grid[r][y] != 1) break; // stop if no ship above
			r--; // move further up
		}

		// now check below
		r = x + 1;
		while (r < 25 && r >= 0 && totalShips < 8 && !visited[r][y]) {
			if (grid[r][y] == 1) { // found below
				coordinates[totalShips][0] = r;
				coordinates[totalShips][1] = y;
				totalShips++;
			}
			cellCounter++;
			visited[r][y] = true;
			if (grid[r][y] != 1) break; // stop if no ship downwards
			r++; // move down
		}

		// check to the left
		int c = y - 1;
		while (c < 25 && c >= 0 && totalShips < 8 && !visited[x][c]) {
			if (grid[x][c] == 1) { // found left
				coordinates[totalShips][0] = x;
				coordinates[totalShips][1] = c;
				totalShips++;
			}
			cellCounter++;
			visited[x][c] = true;
			if (grid[x][c] != 1) break; // stop if empty
			c--; // keep moving left
		}

		// check to the right
		c = y + 1;
		while (c < 25 && c >= 0 & totalShips < 8 && !visited[x][c]) {
			if (grid[x][c] == 1) { // found right
				coordinates[totalShips][0] = x;
				coordinates[totalShips][1] = c;
				totalShips++;
			}
			cellCounter++;
			visited[x][c] = true;
			if (grid[x][c] != 1) break; // stop if empty
			c++; // move right
		}
	}

	@Override
	public int getCellCount() {
		// return how many cells searched in total
		return cellCounter;
	}
}
