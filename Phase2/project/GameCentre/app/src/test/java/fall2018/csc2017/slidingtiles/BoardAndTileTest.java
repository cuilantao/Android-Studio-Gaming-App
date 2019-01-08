package fall2018.csc2017.slidingtiles;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fall2018.csc2017.R;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BoardAndTileTest {

    /** The board manager for testing. */
    BoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles() {
        Board.setNumCols(5);
        Board.setNumRows(5);
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = Board.NUM_ROWS * Board.NUM_COLS;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        Board board = new Board(tiles);
        boardManager = new BoardManager(board);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping c2 tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertEquals(true, boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertEquals(false, boardManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first c2 tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getBackground());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
        assertEquals(0, boardManager.getBoard().getTile(0, 1).getBackground());
    }

    /**
     * Test whether swapping the last c2 tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(18, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(19, boardManager.getBoard().getTile(3, 3).getId());
        boardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(19, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(18, boardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertEquals(true, boardManager.isValidTap(19));
        assertEquals(true, boardManager.isValidTap(23));
        assertEquals(false, boardManager.isValidTap(10));
    }

    /**
     * Test whether TouchMove works.
     */
    @Test
    public void testTouchMove(){
        setUpCorrect();
        boardManager.touchMove(24);
        assertTrue(boardManager.puzzleSolved());
        boardManager.getBoard().swapTiles(4, 4, 4, 3);
        assertFalse(boardManager.puzzleSolved());
        boardManager.touchMove(24);
        assertTrue(boardManager.puzzleSolved());
    }

    /**
     * Test whether Undo works.
     */
    @Test
    public void testUndo(){
        setUpCorrect();
        assertEquals(3, GameActivity.getUndoStep());
        GameActivity.setUndoStep(5);
        assertEquals(5, GameActivity.getUndoStep());
    }

    /**
     * Test whether BoardIterator works.
     */
    @Test
    public void testBoardIterator(){
        setUpCorrect();
        Iterator boardIterator = boardManager.getBoard().iterator();
        assertTrue(boardIterator.hasNext());
    }}

