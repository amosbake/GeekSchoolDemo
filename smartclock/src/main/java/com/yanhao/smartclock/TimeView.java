package com.yanhao.smartclock;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by yons on 2015/4/2.
 */
public class TimeView extends LinearLayout{
    private TextView tv_time;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            refreshTime();
            //当前布局不可见 则停止计时
            if (getVisibility()== View.VISIBLE){
                refreshTime();
            }
        }
    };
    public TimeView(Context context) {
        super(context);
    }

    public TimeView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tv_time= (TextView) findViewById(R.id.tv_time);
        tv_time.setText("Hello!");
        handler.sendEmptyMessage(0);
    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (visibility==View.VISIBLE){
            refreshTime();
        }else {
            handler.removeMessages(0);
        }
    }

    private void refreshTime(){

    }
}
