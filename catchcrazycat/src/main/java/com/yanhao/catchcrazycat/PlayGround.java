package com.yanhao.catchcrazycat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Vector;

import static android.view.SurfaceHolder.Callback;

/**
 * Created by yons on 2015/3/31.
 */
public class PlayGround extends SurfaceView implements View.OnTouchListener {
    private static int WIDTH = 40;
    private final static int COL = 10;
    private final static int ROW = 10;
    private final static int CAT_INITCOL = 4;
    private final static int CAT_INITROW = 5;
    private final static int BLOCKS = 15;//默认添加的路障数量
    private final static String TAG = "PlayGround";

    private Dot[][] matrix;
    private Dot cat;

    public PlayGround(Context context) {
        super(context);
        getHolder().addCallback(callback);
        matrix = new Dot[ROW][COL];
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                matrix[i][j] = new Dot(j, i);
            }
        }
        setOnTouchListener(this);
        initGame();

    }

    /**
     * 获取矩阵中的dot
     *
     * @param x
     * @param y
     * @return
     */
    private Dot getDot(int x, int y) {
        return matrix[y][x];
    }

    /**
     * 判断点是否处于绘制的场景边缘
     *
     * @param dot
     * @return
     */
    private boolean isAtEdge(Dot dot) {
        if (dot.getX() * dot.getY() == 0 || dot.getX() + 1 == COL || dot.getY() + 1 == ROW) {
            return true;
        }
        return false;
    }

    /**
     * 根据方向得到临接点
     *
     * @param dot
     * @param dir 从左边,顺时针遍历周围6个 1:左边 2:左上 3:右上 4:右边 4:右下 5:左下
     * @return
     */
    private Dot getNeighbor(Dot dot, int dir) {

        switch (dir) {
            case 1:
                return getDot(dot.getX() - 1, dot.getY());
            case 2:
                if (dot.getY() % 2 == 0) {
                    return getDot(dot.getX() - 1, dot.getY() - 1);
                } else {
                    return getDot(dot.getX(), dot.getY() - 1);
                }
            case 3:
                if (dot.getY() % 2 == 0) {
                    return getDot(dot.getX(), dot.getY() - 1);
                } else {
                    return getDot(dot.getX() + 1, dot.getY() - 1);
                }
            case 4:
                return getDot(dot.getX() + 1, dot.getY());
            case 5:
                if (dot.getY() % 2 == 0) {
                    return getDot(dot.getX(), dot.getY() + 1);
                } else {
                    return getDot(dot.getX() + 1, dot.getY() + 1);
                }
            case 6:
                if (dot.getY() % 2 == 0) {
                    return getDot(dot.getX() - 1, dot.getY() + 1);
                } else {
                    return getDot(dot.getX(), dot.getY() + 1);
                }
        }
        return null;
    }

    /**
     * 得到给定的点离路障或场景边缘的距离
     *
     * @param dot
     * @param dir 方向
     * @return 正数为从这个方向可以一直到场景边缘, 负数为这个方向上有路障
     */
    private int getDistance(Dot dot, int dir) {
        int distance = 0;
        if (isAtEdge(dot)) {
            return 1;
        }
        Dot ori = dot, next;
        while (true) {
            next = getNeighbor(ori, dir);
            if (next.getStatus() == Dot.STATUS_ON) {
                return distance * -1;
            }
            if (isAtEdge(next)) {
                distance++;
                return distance;
            }
            distance++;
            ori = next;
        }
    }

    /**
     * 移动猫
     *
     * @param dot
     */
    private void MoveTo(Dot dot) {
        dot.setStatus(Dot.STATUS_IN);
        getDot(cat.getX(), cat.getY()).setStatus(Dot.STATUS_OFF);
        cat.setXY(dot.getX(), dot.getY());
    }

    //猫的自动移动
    private void moveCat() {

        if (isAtEdge(cat)) {
            //游戏失败
            lose();
            return;
        }
        Vector<Dot> avaliable = new Vector<>();
        Vector<Dot> positive = new Vector<>();
        HashMap<Dot, Integer> allLength = new HashMap<>();
        for (int i = 1; i < 7; i++) {
            Dot n = getNeighbor(cat, i);
            if (n.getStatus() == Dot.STATUS_OFF) {
                avaliable.add(n);
                allLength.put(n, i);
                if (getDistance(n, i) > 0) {
                    positive.add(n);
                }
            }
        }
        if (avaliable.size() == 0) {
            win();
        } else if (avaliable.size() == 1) {
            MoveTo(avaliable.get(0));

        } else {
            Dot best = null;
            if (positive.size() != 0) {//存在可以直接到达屏幕边缘的走向
                int min = 999;
                for (int i = 0; i < positive.size(); i++) {
                    int a = getDistance(positive.get(i), allLength.get(positive.get(i)));
                    if (a < min) {
                        min = a;
                        best = positive.get(i);
                    }
                }
                MoveTo(best);
            } else {//所有方向都有路障
                int max = 0;
                for (int i = 0; i < avaliable.size(); i++) {
                    int b = getDistance(avaliable.get(i), allLength.get(avaliable.get(i)));
                    if (b <=max) {
                        max = b;
                        best = avaliable.get(i);
                    }
                }
                MoveTo(best);
            }
        }
    }
    private void lose() {
        Toast.makeText(getContext(), "you lose", Toast.LENGTH_SHORT).show();
    }

    private void win() {
        Toast.makeText(getContext(), "you win", Toast.LENGTH_SHORT).show();
    }

    private void redraw() {
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(Color.LTGRAY);//浅灰色
        Paint paint = new Paint();
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        for (int i = 0; i < ROW; i++) {
            //设置缩进
            int offSet = 0;
            if (i % 2 != 0) {
                offSet = WIDTH / 2;
            }
            for (int j = 0; j < COL; j++) {
                Dot one = getDot(j, i);
                switch (one.getStatus()) {
                    case Dot.STATUS_OFF:
                        paint.setColor(0XFFEEEEEE);// 前两位是透明度，后六位为三原色
                        break;
                    case Dot.STATUS_ON:
                        paint.setColor(0XFFFFAA00);
                        break;
                    case Dot.STATUS_IN:
                        paint.setColor(0XFFFF0000);
                        break;
                }
                //为每一个坐标点画圆
                canvas.drawOval(new RectF(one.getX()*WIDTH+offSet, one.getY()*WIDTH,
                        (one.getX()+1)*WIDTH+offSet, (one.getY()+1)*WIDTH), paint);
            }
        }

        getHolder().unlockCanvasAndPost(canvas);

    }

    Callback callback = new Callback() {

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            redraw();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            WIDTH = width / (COL + 1);
            redraw();
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };

    private void initGame() {
        //将所有点设为可通行状态
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                matrix[i][j].setStatus(Dot.STATUS_OFF);
            }
        }
        //将中心点设为猫的位置
        cat = new Dot(CAT_INITCOL, CAT_INITROW);
        getDot(CAT_INITCOL, CAT_INITROW).setStatus(Dot.STATUS_IN);

        //随机将场景内的点变为路障
        for (int i = 0; i < BLOCKS; ) {
            int x = (int) ((Math.random() * 1000) % COL);
            int y = (int) ((Math.random() * 1000) % ROW);
            if (getDot(x, y).getStatus() == Dot.STATUS_OFF) {
                getDot(x, y).setStatus(Dot.STATUS_ON);
                i++;
            }
        }

    }





    //触摸监听
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (MotionEvent.ACTION_UP == event.getAction()) {
//            Toast.makeText(getContext(), event.getX()+":"+ event.getY(),Toast.LENGTH_SHORT).show();
            //获取点击时的X,Y矩阵坐标
            int x, y;
            y = (int) (event.getY() / WIDTH);
            if (y % 2 == 0) {
                x = (int) (event.getX() / WIDTH);
            } else {
                x = (int) ((event.getX() - WIDTH / 2) / WIDTH);
            }
            //点击超出范围,重新游戏
            if (x + 1 > COL || y + 1 > ROW) {
                initGame();
//                getNeighbor(cat, k).setStatus(Dot.STATUS_IN);
//                k++;
//                for(int i=1;i<7;i++){
//                    Log.i(TAG,"distance for"+i+"@"+getDistance(cat,i));
//                }
            } else if (getDot(x, y).getStatus() == Dot.STATUS_OFF) {
                getDot(x, y).setStatus(Dot.STATUS_ON);
                moveCat();
            }
            redraw();
        }
        return true;
    }


}
