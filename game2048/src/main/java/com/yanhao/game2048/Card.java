package com.yanhao.game2048;

import android.content.Context;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by yons on 2015/4/2.
 */
public class Card extends FrameLayout {

    private int num;
    private TextView label;

    public Card(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {

        label = new TextView(context);
        label.setTextSize(32);
        label.setGravity(Gravity.CENTER);
        label.setBackgroundColor(0x33ffffff);

        LayoutParams lp = new LayoutParams(-1, -1);//MATCH_PARENT = -1; WRAP_CONTENT = -2;
        lp.setMargins(10, 10, 0, 0);
        addView(label, lp);

        setNum(0);


    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        if (num <= 0) {
            label.setText("");
        } else {
            label.setText(String.valueOf(num));
        }

    }

    public boolean equals(Card card) {
        return getNum() == card.getNum();
    }
}
