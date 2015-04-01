package com.yanhao.myphonedirectory;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
    private ListView listView;
    private PhoneInfoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView= (ListView) findViewById(R.id.listView);
        adapter=new PhoneInfoAdapter(GetPhoneNumber.getNumber(this),this);
        listView.setAdapter(adapter);
    }


}
