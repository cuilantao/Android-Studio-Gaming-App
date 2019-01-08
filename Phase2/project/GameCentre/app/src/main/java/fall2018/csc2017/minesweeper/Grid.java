package fall2018.csc2017.minesweeper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;

/**
 * The source code is originated from
 * https://github.com/marcellelek/Minesweeper.git
 * It is used to construct basic game structure and modified by our group member.
 * Grid containing game information.
 */
public class Grid extends GridView {

    /**
     * Constructor of class grid.
     */
    public Grid(Context context, AttributeSet attrs) {
        super(context, attrs);

        GridManagerMinesweeper.getInstance().createGrid(context);

        setNumColumns(GridManagerMinesweeper.getWIDTH());
        setAdapter(new GridAdapter());
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    private class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return GridManagerMinesweeper.getWIDTH() * GridManagerMinesweeper.getHEIGHT();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return GridManagerMinesweeper.getInstance().getCellAt(position);
        }
    }
}
