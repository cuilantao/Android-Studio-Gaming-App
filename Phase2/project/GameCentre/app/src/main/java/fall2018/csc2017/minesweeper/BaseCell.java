package fall2018.csc2017.minesweeper;

import android.content.Context;
import android.view.View;

/**
 * The source code is originated from
 * https://github.com/marcellelek/Minesweeper.git
 * It is used to construct basic game structure and modified by our group member.
 * Abstract class BaseCell represent each single cell on the grid.
 */
public abstract class BaseCell extends View {

    /**
     * Identify what the cell is.
     */
    private int value;

    /**
     * Whether the cell is a bomb.
     */
    private boolean isBomb;

    /**
     * Whether the cell is revealed.
     */
    private boolean isRevealed;

    /**
     * Whether the cell is clicked.
     */
    private boolean isClicked;

    /**
     * Whether the cell is flagged.
     */
    private boolean isFlagged;

    /**
     * X, Y coordinate of a cell.
     */
    private int x, y;

    /**
     * Position of a cell.
     */
    private int position;

    /**
     * Constructor of class base cell.
     *
     * @param context context of the cell.
     */
    public BaseCell(Context context) {
        super(context);
    }

    /**
     * Get the value of the base cell
     *
     * @return int value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Set the value of the base cell.
     *
     * @param value value of the base cell as int.
     */
    public void setValue(int value) {
        isBomb = false;
        isRevealed = false;
        isClicked = false;
        isFlagged = false;

        if (value == -1) {
            isBomb = true;
        }

        this.value = value;
    }

    /**
     * Check the base cell is bomb or not.
     *
     * @return True if is bomb, otherwise false.
     */
    public boolean isBomb() {
        return isBomb;
    }

    /**
     * Check the base cell is revealed or not.
     *
     * @return True if is revealed, otherwise false.
     */
    public boolean isRevealed() {
        return isRevealed;
    }

    /**
     * Set the base cell to revealed.
     */
    public void setRevealed() {
        isRevealed = true;
        invalidate();
    }

    /**
     * Check the base cell is clicked or not.
     *
     * @return True if is clicked, otherwise false.
     */
    public boolean isClicked() {
        return isClicked;
    }

    /**
     * Set the base cell to isClicked.
     */
    public void setClicked() {
        this.isClicked = true;
        this.isRevealed = true;
        invalidate();
    }

    /**
     * Check the base cell is flagged or not.
     *
     * @return True if is flagged, otherwise false.
     */
    public boolean isFlagged() {
        return isFlagged;
    }

    /**
     * Set the base cell to flagged.
     */
    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
    }

    /**
     * Get x coordinate of the base cell.
     *
     * @return x position.
     */
    public int getXPos() {
        return x;
    }

    /**
     * Get y coordinate of the base cell.
     *
     * @return y position.
     */
    public int getYPos() {
        return y;
    }

    /**
     * Get position of the base cell on the grid.
     *
     * @return int position.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Set position of base cell on the grid.
     *
     * @param x x position.
     * @param y y position.
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;

        this.position = y * GridManagerMinesweeper.getWIDTH() + x;

        invalidate();
    }

}
