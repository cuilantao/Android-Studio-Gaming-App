package fall2018.csc2017.minesweeper;

import java.util.Random;

/**
 * The source code is originated from
 * https://github.com/marcellelek/Minesweeper.git
 * It is used to construct basic game structure and modified by our group member.
 * Class for generate cells.
 */
public class Generator {

    /**
     * Generate a random game minesweeper grid.
     *
     * @param bombNumber number of bombs.
     * @param width      width of the grid.
     * @param height     height of the grid.
     * @return grid containing game information.
     */
    public static int[][] generate(int bombNumber, int width, int height) {
        // Random for generating numbers
        Random r = new Random();

        int[][] grid = new int[width][height];
        for (int x = 0; x < width; x++) {
            grid[x] = new int[height];
        }

        while (bombNumber > 0) {
            int x = r.nextInt(width);
            int y = r.nextInt(height);

            // -1 is the bomb
            if (grid[x][y] != -1) {
                grid[x][y] = -1;
                bombNumber--;
            }
        }
        grid = calculateNeighbours(grid, width, height);

        return grid;
    }

    /**
     * Calculate number of bombs next to the cell.
     *
     * @param grid   grid containing game information.
     * @param width  width of the grid.
     * @param height height of the game.
     * @return number of bombs.
     */
    private static int[][] calculateNeighbours(int[][] grid, final int width, final int height) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = getNeighbourNumber(grid, x, y, width, height);
            }
        }

        return grid;
    }

    /**
     * Show the neighbours bomb number.
     *
     * @param grid   grid containing game information.
     * @param width  width of the grid.
     * @param height height of the grid.
     * @param x      x position.
     * @param y      y position.
     * @return number of bombs.
     */
    private static int getNeighbourNumber(final int grid[][], final int x, final int y, final int width, final int height) {
        if (grid[x][y] == -1) {
            return -1;
        }

        int count = 0;

        if (isMineAt(grid, x - 1, y + 1, width, height)) count++; // top-left
        if (isMineAt(grid, x, y + 1, width, height)) count++; // top
        if (isMineAt(grid, x + 1, y + 1, width, height)) count++; // top-right
        if (isMineAt(grid, x - 1, y, width, height)) count++; // left
        if (isMineAt(grid, x + 1, y, width, height)) count++; // right
        if (isMineAt(grid, x - 1, y - 1, width, height)) count++; // bottom-left
        if (isMineAt(grid, x, y - 1, width, height)) count++; // bottom
        if (isMineAt(grid, x + 1, y - 1, width, height)) count++; // bottom-right

        return count;
    }

    /**
     * Check is there a mine at the cell.
     *
     * @param grid   grid containing game information.
     * @param x      x position.
     * @param y      y position.
     * @param width  width of the grid.
     * @param height height of the grid.
     * @return Whether there is a mine at this position.
     */
    private static boolean isMineAt(final int[][] grid, final int x, final int y, final int width, final int height) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            if (grid[x][y] == -1) {
                return true;
            }
        }
        return false;
    }

}
