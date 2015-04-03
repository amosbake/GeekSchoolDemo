package com.yanhao.smartclock;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.TabHost;


public class MainActivity extends ActionBarActivity {
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost= (TabHost) findViewById(R.id.tabHost);
        //使用自己定义的tabhost时,需要setup
        tabHost.setup();

        tabHost.addTab(tabHost.newTabSpec("tabTime").setIndicator("时钟").setContent(R.id.tab_time));
        tabHost.addTab(tabHost.newTabSpec("tabAlarm").setIndicator("闹钟").setContent(R.id.tab_alarm));
        tabHost.addTab(tabHost.newTabSpec("tabTimer").setIndicator("计时器").setContent(R.id.tab_timer));
        tabHost.addTab(tabHost.newTabSpec("tabWatch").setIndicator("秒表").setContent(R.id.tab_watch));
    }


}
