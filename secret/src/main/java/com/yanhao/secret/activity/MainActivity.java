package com.yanhao.secret.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.yanhao.secret.Config;
import com.yanhao.secret.R;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        tokenOption();
//        testToLogin();
        testToTimeLine();
        finish();
    }

    /**
     * Register  to enter TimeLineActivity
     * Unregister  to enter LoginActivity
     */
    private void tokenOption() {
        String token = Config.getCacheToken(this);
        if (null != token) {
            Intent intent = new Intent(this, TimeLineActivity.class);
            intent.putExtra(Config.KEY_TOKEN, token);
            startActivity(intent);
        } else {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }

    /**
     * test for entering LoginActivity*
     */
    private void testToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * test for entering TimeLineActivity*
     */
    private void testToTimeLine() {
        Intent intent = new Intent(this, TimeLineActivity.class);
        startActivity(intent);
    }


}
