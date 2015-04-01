package com.yanhao.geekschool;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private EditText inputTime;
    private Button getTime, startTime, stopTime;
    private TextView timeShow;
    private int i = 0;
    private Timer timer = null;
    private TimerTask task = null;
    private Handler mHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        inputTime = (EditText) findViewById(R.id.inputTime);
        getTime = (Button) findViewById(R.id.getTime);
        startTime = (Button) findViewById(R.id.startTime);
        stopTime = (Button) findViewById(R.id.stopTime);
        timeShow = (TextView) findViewById(R.id.time_show);

        getTime.setOnClickListener(this);
        startTime.setOnClickListener(this);
        stopTime.setOnClickListener(this);

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                timeShow.setText(String.valueOf(msg.arg1) + " S");
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.getTime:
                if (!TextUtils.isEmpty(inputTime.getText())) {
                    timeShow.setText(inputTime.getText().toString() + " S");
                    i = Integer.parseInt(inputTime.getText().toString());
                } else {
                    Toast.makeText(this, "请输入时间", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.startTime:
                startTime();
                break;
            case R.id.stopTime:
                stopTime();
                break;
        }
    }

    private void stopTime() {
        task.cancel();
    }

    /**
     * 用线程控制时间的渐减显示
     */
    private void startTime() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                if (i > 0) {
                    i--;
                    Message message = mHandler.obtainMessage();
                    message.arg1 = i;
                    mHandler.sendMessage(message);
                }
            }
        };
        timer.schedule(task,1*1000L,1*1000L);
    }
}
