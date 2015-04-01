package com.yanhao.mysidemenu;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by yons on 2015/4/1.
 */
public class MainUI extends RelativeLayout{
    private static final int TEST_DIS=20;//滑动临界值
    private static final String TAG="MainUI";

    private Context context;
    private FrameLayout leftMenu;
    private FrameLayout rightMenu;
    private FrameLayout middleMenu;

    private boolean isTestCompete;
    private boolean isLeftRightEvent;
    private boolean isUpDownEvent;
    private Point point=new Point();

    public MainUI(Context context) {
        super(context);
        initView(context);
    }

    public MainUI(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }
    private void initView(Context context){
        this.context=context;
        leftMenu=new FrameLayout(context);
        rightMenu=new FrameLayout(context);
        middleMenu=new FrameLayout(context);

        leftMenu.setBackgroundColor(Color.RED);
        rightMenu.setBackgroundColor(Color.RED);
        middleMenu.setBackgroundColor(Color.GREEN);

        addView(leftMenu);
        addView(rightMenu);
        addView(middleMenu);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //设置左中右三个菜单的宽度,高度
        middleMenu.measure(widthMeasureSpec, heightMeasureSpec);
        int realWidth=MeasureSpec.getSize(widthMeasureSpec);
        int tempWidth=MeasureSpec.makeMeasureSpec((int)(realWidth*0.8f),MeasureSpec.EXACTLY);
        leftMenu.measure(tempWidth,heightMeasureSpec);
        rightMenu.measure(tempWidth,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        middleMenu.layout(l,t,r,b);
        leftMenu.layout(l-leftMenu.getMeasuredWidth(),t,r,b);
        rightMenu.layout(l+middleMenu.getMeasuredWidth(),t,r+rightMenu.getMeasuredWidth(),b);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (!isTestCompete) {
            getEventType(ev);
            return true;
        }
        if (isLeftRightEvent){
            switch (ev.getActionMasked()) {
                case MotionEvent.ACTION_MOVE:
                    int curScrollX=getScrollX();
                    int dis_x=(int)(ev.getX()-point.x);
                    int expectX=curScrollX-dis_x;
                    int finalX=0;
                    if (expectX<0){
                        finalX=Math.max(expectX,-leftMenu.getMeasuredWidth());
                    }else{
                        finalX=Math.min(expectX,rightMenu.getMeasuredWidth());
                    }
                    scrollTo(finalX,0);
                    point.x= (int) ev.getX();
                    break;
                case MotionEvent.ACTION_UP:
                    isLeftRightEvent=false;
                    isTestCompete=false;
                    break;
                case MotionEvent.ACTION_CANCEL:
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private void getEventType(MotionEvent ev) {
        /**
         * action码的位掩码部分就是action本身
         */
        switch (ev.getActionMasked()){
            case MotionEvent.ACTION_DOWN:
                point.x= (int) ev.getX();
                point.y= (int) ev.getY();
                super.dispatchTouchEvent(ev);
                break;
            case MotionEvent.ACTION_MOVE:
                int dx= (int) Math.abs(ev.getX()-point.x);
                int dy=(int) Math.abs(ev.getY()-point.y);
                if (dx>=TEST_DIS&&dx>dy){//左右滑动
                    Log.i(TAG,"左右滑动");
                    isLeftRightEvent=true;
                    isUpDownEvent=false;
                    point.x= (int) ev.getX();
                    point.y= (int) ev.getY();
                }else if(dy>=TEST_DIS&&dy>dx){//上下滑动
                    Log.i(TAG,"上下滑动");
                    isLeftRightEvent=false;
                    isUpDownEvent=true;
                    point.x= (int) ev.getX();
                    point.y= (int) ev.getY();
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                super.dispatchTouchEvent(ev);
                isLeftRightEvent = false;
                isTestCompete = false;
                break;
        }
    }
}
