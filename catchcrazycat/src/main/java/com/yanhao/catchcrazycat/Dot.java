package com.yanhao.catchcrazycat;

/**
 * Created by yons on 2015/3/31.
 * 棋盘上的点
 */
public class Dot {
    int x,y;
    int status;
    public static final int STATUS_ON=1;//不可点击,走动
    public static final int STATUS_OFF=0;//可点击走动
    public static final int STATUS_IN=9;//猫所在位置

    public Dot(int x, int y) {
        this.x = x;
        this.y = y;
        status=STATUS_OFF;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
    public void setXY(int x,int y) {
        this.x = x;
        this.y = y;
    }
}
