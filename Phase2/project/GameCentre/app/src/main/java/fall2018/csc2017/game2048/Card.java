package fall2018.csc2017.game2048;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Card in game2048.
 * The source code is originated from
 * https://github.com/JimZhou-001/2048-Android.git
 * It is used to construct basic game structure and modified by our group member.
 */
public class Card extends FrameLayout {

    /**
     * The number on the card.
     */
    private int num = 0;

    /**
     * Each individual card.
     */
    private TextView label;

    /**
     * Constructor of card.
     *
     * @param context The context of card.
     */
    public Card(Context context) {
        super(context);
        label = new TextView(getContext());
        label.setTextSize(32);
        label.setGravity(Gravity.CENTER);
        LayoutParams lp = new LayoutParams(-1, -1);
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);
        setNum(0);
    }

    /**
     * Get the number on the card.
     *
     * @return the number on the card.
     */
    public int getNum() {
        return num;
    }

    /**
     * Set the number on the card.
     *
     * @param num the number on the card.
     */
    public void setNum(int num) {

        this.num = num;
        if (num > 0) {
            String s = Integer.toString(num);
            label.setText(s);
        } else {
            label.setText("");
        }

        switch (num) {
            case 0:
                label.setBackgroundColor(0xffccc0b2);
                break;

            case 2:
                label.setBackgroundColor(0xffffe4e1);
                label.setTextColor(0xffcd853f);
                label.setTextSize(50);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                break;

            case 4:
                label.setBackgroundColor(0xffff8c00);
                label.setTextColor(0xfffffaf0);
                label.setTextSize(50);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                break;

            case 8:
                label.setBackgroundColor(0xfffa8072);
                label.setTextColor(0xffffe4b5);
                label.setTextSize(50);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                break;

            case 16:
                label.setBackgroundColor(0xff66cdaa);
                label.setTextColor(0xffe0ffff);
                label.setTextSize(50);
                break;

            case 32:
                label.setBackgroundColor(0xffff6347);
                label.setTextColor(0xffffe4c4);
                label.setTextSize(50);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                break;

            case 64:
                label.setBackgroundColor(0xffcd5c5c);
                label.setTextColor(0xffff8c00);
                label.setTextSize(50);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 128:
                label.setBackgroundColor(0xff8b4513);
                label.setTextSize(50);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 256:
                label.setBackgroundColor(0xff1e90ff);
                label.setTextColor(0xfffa8072);
                label.setTextSize(50);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 512:
                label.setBackgroundColor(0xff191970);
                label.setTextColor(0xff008b8b);
                label.setTextSize(50);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 1024:
                label.setBackgroundColor(0xffffff00);
                label.setTextColor(0xff99cc00);
                label.setTextSize(40);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 2048:
                label.setBackgroundColor(0xffcd5c5c);
                label.setTextColor(0xff8b4513);
                label.setTextSize(40);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 4096:
                label.setBackgroundColor(0xffff9933);
                label.setTextColor(0xff663300);
                label.setTextSize(40);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            case 8192:
                label.setBackgroundColor(0xff99ccff);
                label.setTextColor(0xff003366);
                label.setTextSize(40);
                label.setTypeface(Typeface.DEFAULT_BOLD);
                break;
            default:
                label.setBackgroundColor(0xffedc22d);
                break;
        }

    }

    /**
     * Find if the numbers on two cards are equal.
     *
     * @param card the other card.
     * @return whether the numbers on two cards are equal.
     */
    public boolean equals(Card card) {
        return getNum() == card.getNum();
    }

}
