package com.yanhao.game2048;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yons on 2015/4/1.
 */
public class GameView extends GridLayout implements View.OnTouchListener {
    private static final String TAG = "GameView";
    private final int MAX_DIS = 10;//滑动临界值
    private final int EDGE_MARGIN = 10;//距屏幕边界像素值
    private final int CARD_ROW = 4;//卡片行数
    private final int CARD_COL = 4;//卡片列数
    private final int INIT_CARD_NUM = 3;//初始卡片数目
    private Card[][] cardsMap = new Card[CARD_ROW][CARD_COL];
    private List<Point> emptyPoint = new ArrayList<>();
    private Point point;
    private float dx, dy;

    public GameView(Context context) {
        super(context, null);
        initView(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        initView(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context c) {
        setColumnCount(CARD_COL);
        setBackgroundColor(0xffbbada0);
        point = new Point();
        setOnTouchListener(this);
    }

    //宽高发生改变时调用
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        int cardWith = (Math.min(w, h) - EDGE_MARGIN) / 4;

        addCards(cardWith, cardWith);
        startGame();
    }

    public void startGame() {
        //清空记分牌
        MainActivity.getMainActivity().clearScore();
        //卡片清空
        for (int i = 0; i < CARD_ROW; i++) {
            for (int j = 0; j < CARD_COL; j++) {
                cardsMap[j][i].setNum(0);
            }
        }
        //添加初始卡片
        for (int i = 0; i < INIT_CARD_NUM; i++) {
            addRandomNumber();
        }
    }


    //添加卡片
    private void addCards(int cardWith, int CardHeight) {
        Card c;
        for (int i = 0; i < CARD_ROW; i++) {
            for (int j = 0; j < CARD_COL; j++) {
                c = new Card(getContext());
                c.setNum(0);
                addView(c, cardWith, CardHeight);

                cardsMap[j][i] = c;
            }
        }
    }

    //添加随机数字
    private void addRandomNumber() {
        emptyPoint.clear();

        for (int i = 0; i < CARD_ROW; i++) {
            for (int j = 0; j < CARD_COL; j++) {
                if (cardsMap[j][i].getNum() <= 0) {
                    emptyPoint.add(new Point(j, i));
                }
            }
        }
        if (emptyPoint.size() > 0) {
            Point p = emptyPoint.remove((int) (Math.random() * emptyPoint.size()));
            cardsMap[p.x][p.y].setNum(Math.random() > 0.1 ? 2 : 4);//0.1的概率生成4
        }

    }
    //合并时添加分数
    private void addScore(int i, int j) {
        MainActivity.getMainActivity().addScore(cardsMap[j][i].getNum());
    }
    private List<Card> getNeighbor(int i, int j){
        List<Card> cardsNeighbor=new ArrayList<>();
        if (0==i){
            if (0==j){
                cardsNeighbor.add(cardsMap[j+1][i]);
                cardsNeighbor.add(cardsMap[j][i+1]);
            }else if(CARD_ROW-1==j){
                cardsNeighbor.add(cardsMap[j][i+1]);
                cardsNeighbor.add(cardsMap[j-1][i]);
            }else{
                cardsNeighbor.add(cardsMap[j+1][i]);
                cardsNeighbor.add(cardsMap[j][i+1]);
                cardsNeighbor.add(cardsMap[j-1][i]);
            }
        }else if (CARD_COL-1==i){
            if (0==j){
                cardsNeighbor.add(cardsMap[j+1][i]);
                cardsNeighbor.add(cardsMap[j][i-1]);
            }else if(CARD_ROW-1==j){
                cardsNeighbor.add(cardsMap[j][i-1]);
                cardsNeighbor.add(cardsMap[j-1][i]);
            }else{
                cardsNeighbor.add(cardsMap[j+1][i]);
                cardsNeighbor.add(cardsMap[j][i-1]);
                cardsNeighbor.add(cardsMap[j-1][i]);
            }
        }else{
            if (0==j){
                cardsNeighbor.add(cardsMap[j+1][i]);
                cardsNeighbor.add(cardsMap[j][i-1]);
                cardsNeighbor.add(cardsMap[j][i+1]);
            }else if(CARD_ROW-1==j){
                cardsNeighbor.add(cardsMap[j][i-1]);
                cardsNeighbor.add(cardsMap[j][i+1]);
                cardsNeighbor.add(cardsMap[j-1][i]);
            }else{
                cardsNeighbor.add(cardsMap[j+1][i]);
                cardsNeighbor.add(cardsMap[j][i-1]);
                cardsNeighbor.add(cardsMap[j-1][i]);
                cardsNeighbor.add(cardsMap[j][i+1]);
            }
        }
        return cardsNeighbor;
    }
    //检查游戏是否结束
    private boolean checkComplete(){
        boolean isComplete=true;
        for (int i = 0; i < CARD_ROW; i++) {
            for (int j = 0; j < CARD_COL; j++) {
                if (cardsMap[j][i].getNum()==0 ){
                    return false;
                }else {
                    List<Card> neighbors=getNeighbor(j,i);
                    for (Card card:neighbors){
                        if (cardsMap[j][i].equals(card));{
                            return false;
                        }
                    }
                }
            }
        }
        return isComplete;
    }
    //游戏结束对话框
    private void showScoreDialog(){
        new AlertDialog.Builder(getContext())
                .setTitle("您好,游戏者")
                .setMessage("游戏结束")
                .setPositiveButton("重来",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame();
                    }
                }).show();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                point.x = (int) event.getX();
                point.y = (int) event.getY();
                Log.i(TAG, "point:" + point.x + "@" + point.y);
                break;
            case MotionEvent.ACTION_UP:
                dx = event.getX() - point.x;
                dy = event.getY() - point.y;

                if (dx < 0 && Math.abs(dx) > MAX_DIS && Math.abs(dx) > Math.abs(dy)) {//向左滑动
                    swipeLeft();

                } else if (dx > 0 && Math.abs(dx) > MAX_DIS && Math.abs(dx) > Math.abs(dy)) { //向右滑动
                    swipeRight();
                } else if (dy < 0 && Math.abs(dy) > MAX_DIS && Math.abs(dy) > Math.abs(dx)) { //向上滑动
                    swipeUp();
                } else if (dy > 0 && Math.abs(dy) > MAX_DIS && Math.abs(dy) > Math.abs(dx)) { //向下滑动
                    swipeDown();
                }
                break;
        }
        return true;
    }

    //下滑动
    private void swipeDown() {
        boolean merge=false;
        for (int  j= 0; j < CARD_COL; j++) {
            for (int i = CARD_ROW-1; i>=0; i--) {
                for (int k = i - 1; k >=0; k--) {
                    //如果上面有面值不为0的卡片
                    if (cardsMap[j][k].getNum() > 0) {
                        //如果当前卡片的面值为0 则把上面的卡片移动过来
                        if (cardsMap[j][i].getNum() <= 0) {
                            cardsMap[j][i].setNum(cardsMap[j][k].getNum());
                            cardsMap[j][k].setNum(0);
                            i++;
                            merge=true;
                        }
                        //如果当前卡片的面值为与上面的卡片面值相等,则两张卡片合并
                        else if (cardsMap[j][k].equals(cardsMap[j][i])) {
                            cardsMap[j][i].setNum(cardsMap[j][k].getNum() * 2);
                            cardsMap[j][k].setNum(0);
                            addScore(i, j);
                            merge=true;
                        }
                        break;
                    }
                }

            }
        }
        if (merge){
            addRandomNumber();
            if (checkComplete()){
                showScoreDialog();
            }
        }
    }

    //上滑动
    private void swipeUp() {
        boolean merge=false;
        for (int  j= 0; j < CARD_COL; j++) {
            for (int i = 0; i < CARD_ROW; i++) {
                for (int k = i + 1; k < CARD_ROW; k++) {
                    //如果下面有面值不为0的卡片
                    if (cardsMap[j][k].getNum() > 0) {
                        //如果当前卡片的面值为0 则把下面的卡片移动过来
                        if (cardsMap[j][i].getNum() <= 0) {
                            cardsMap[j][i].setNum(cardsMap[j][k].getNum());
                            cardsMap[j][k].setNum(0);
                            i--;
                            merge=true;
                        }
                        //如果当前卡片的面值为与下面的卡片面值相等,则两张卡片合并
                        else if (cardsMap[j][k].equals(cardsMap[j][i])) {
                            cardsMap[j][i].setNum(cardsMap[j][k].getNum() * 2);
                            cardsMap[j][k].setNum(0);
                            addScore(i, j);
                            merge=true;
                        }
                        break;
                    }
                }

            }
        }
        if (merge){
            addRandomNumber();
            if (checkComplete()){
                showScoreDialog();
            }
        }
    }

    //右滑动
    private void swipeRight() {
        boolean merge=false;
        for (int i = 0; i < CARD_ROW; i++) {
            for (int j = CARD_COL - 1; j >= 0; j--) {
                for (int k = j - 1; k >= 0; k--) {
                    //如果左面有面值不为0的卡片
                    if (cardsMap[k][i].getNum() > 0) {
                        //如果当前卡片的面值为0 则把左面的卡片移动过来
                        if (cardsMap[j][i].getNum() <= 0) {
                            cardsMap[j][i].setNum(cardsMap[k][i].getNum());
                            cardsMap[k][i].setNum(0);
                            j++;
                            merge=true;
                        }
                        //如果当前卡片的面值为与左面的卡片面值相等,则两张卡片合并
                        else if (cardsMap[k][i].equals(cardsMap[j][i])) {
                            cardsMap[j][i].setNum(cardsMap[j][i].getNum() * 2);
                            cardsMap[k][i].setNum(0);
                            addScore(i, j);
                            merge=true;
                        }
                        break;
                    }
                }

            }
        }
        if (merge){
            addRandomNumber();
            if (checkComplete()){
                showScoreDialog();
            }
        }
    }

    //左滑动
    private void swipeLeft() {
        boolean merge=false;
        for (int i = 0; i < CARD_ROW; i++) {
            for (int j = 0; j < CARD_COL; j++) {
                for (int k = j + 1; k < CARD_COL; k++) {
                    //如果右面有面值不为0的卡片
                    if (cardsMap[k][i].getNum() > 0) {
                        //如果当前卡片的面值为0 则把右面的卡片移动过来
                        if (cardsMap[j][i].getNum() <= 0) {
                            cardsMap[j][i].setNum(cardsMap[k][i].getNum());
                            cardsMap[k][i].setNum(0);
                            j--;
                            merge=true;
                        }
                        //如果当前卡片的面值为与右面的卡片面值相等,则两张卡片合并
                        else if (cardsMap[k][i].equals(cardsMap[j][i])) {
                            cardsMap[j][i].setNum(cardsMap[j][i].getNum() * 2);
                            cardsMap[k][i].setNum(0);
                            addScore(i, j);
                            merge=true;
                        }
                        break;
                    }
                }

            }
        }
        if (merge){
            addRandomNumber();
            if (checkComplete()){
                showScoreDialog();
            }
        }
    }

}
