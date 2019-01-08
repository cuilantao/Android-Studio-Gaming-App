package fall2018.csc2017.minesweeper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import fall2018.csc2017.R;

/**
 * The source code is originated from
 * https://github.com/marcellelek/Minesweeper.git
 * It is used to construct basic game structure and modified by our group member.
 * Subclass of class BaseCell. Represent each cell of game minesweeper.
 */
public class Cell extends BaseCell implements View.OnClickListener, View.OnLongClickListener {


    /**
     * Constructor of class cell.
     *
     * @param context context of the cell
     * @param x       x position.
     * @param y       y position.
     */
    public Cell(Context context, int x, int y) {
        super(context);

        setPosition(x, y);

        setOnClickListener(this);
        setOnLongClickListener(this);
    }

    /**
     * Measure and adjust the display size.
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    /**
     * On click listener.
     */
    @Override
    public void onClick(View v) {

        if (!GridManagerMinesweeper.isLost())
            GridManagerMinesweeper.getInstance().click(getXPos(), getYPos());
    }

    /**
     * Set long click listener.
     */
    @Override
    public boolean onLongClick(View v) {
        if (!GridManagerMinesweeper.getInstance().getCellAt(getXPos(), getYPos()).isRevealed()) {
            GridManagerMinesweeper.getInstance().flag(getXPos(), getYPos());
        }
        return true;
    }


    /**
     * Things happens when onclick or on long click.
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("Minesweeper", "Cell::onDraw");
        drawButton(canvas);

        if (isFlagged()) {
            drawFlag(canvas);
            //GridManagerMinesweeper.setBombNumber(GridManagerMinesweeper.getBombNumber()-1);
        } else if (isRevealed() && isBomb() && !isClicked()) {
            drawNormalBomb(canvas);
        } else {
            if (isClicked()) {
                if (getValue() == -1) {
                    drawBombExploded(canvas);
                } else {
                    drawNumber(canvas);
                }
            } else {
                drawButton(canvas);
            }
        }
    }

    /**
     * Show exploded bomb.
     */
    private void drawBombExploded(Canvas canvas) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bomb_exploded);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    /**
     * Show flag.
     */
    private void drawFlag(Canvas canvas) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.flag);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    /**
     * Show button.
     */
    private void drawButton(Canvas canvas) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.button);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    /**
     * Show normal bomb.
     */
    private void drawNormalBomb(Canvas canvas) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.bomb_normal);
        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }

    /**
     * Show number of  bombs.
     */
    private void drawNumber(Canvas canvas) {
        Drawable drawable = null;

        switch (getValue()) {
            case 0:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_0);
                break;
            case 1:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.number_8);
                break;
        }

        drawable.setBounds(0, 0, getWidth(), getHeight());
        drawable.draw(canvas);
    }


}
