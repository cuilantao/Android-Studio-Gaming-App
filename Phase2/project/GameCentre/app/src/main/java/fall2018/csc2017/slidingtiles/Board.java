package fall2018.csc2017.slidingtiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile>, Cloneable {

    /**
     * The number of rows.
     */
    static int NUM_ROWS = 4;

    /**
     * The number of rows.
     */
    static int NUM_COLS = 4;
    /**
     * The tiles on the board in row-major order.
     */
    public Tile[][] tiles = new Tile[NUM_ROWS][NUM_COLS];

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != Board.NUM_ROWS; row++) {
            for (int col = 0; col != Board.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * @param r The num of rows wanted to set
     *          Function to set num of rows for different complexity.
     */
    public static void setNumRows(int r) {
        NUM_ROWS = r;
    }

    /**
     * @param c The num of rows wanted to set
     *          Function to set num of cols for different complexity.
     */
    public static void setNumCols(int c) {
        NUM_COLS = c;
    }

    @Override
    @NonNull
    public Iterator<Tile> iterator() {
        return new BoardClassIterator();
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return NUM_ROWS * NUM_COLS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile tmp = tiles[row1][col1];
        tiles[row1][col1] = tiles[row2][col2];
        tiles[row2][col2] = tmp;
        setChanged();
        notifyObservers();
    }

    /**
     * Return the string representation of Board.
     */
    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    private class BoardClassIterator implements Iterator<Tile> {
        private int cur_index = 0;

        @Override
        public boolean hasNext() {
            return cur_index < NUM_ROWS * NUM_COLS;
        }

        @Override
        public Tile next() {
            Tile result = tiles[cur_index / 4][cur_index % 4];
            cur_index++;
            return result;
        }
    }
}
