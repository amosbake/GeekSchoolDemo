package com.yanhao.hellonotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity implements View.OnClickListener{
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;
    private Button textBtn, imgBtn, videotBtn;
    private ListView lv;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        lv= (ListView) findViewById(R.id.list);
        textBtn= (Button) findViewById(R.id.text);
        imgBtn= (Button) findViewById(R.id.img);
        videotBtn= (Button) findViewById(R.id.video);
        textBtn.setOnClickListener(this);
        imgBtn.setOnClickListener(this);
        videotBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        intent=new Intent(this,AddContentActivity.class);
        switch (v.getId()){
            case R.id.text:
                intent.putExtra("flag","1");
                startActivity(intent);
                break;
            case R.id.img:
                intent.putExtra("flag","2");
                startActivity(intent);
                break;
            case R.id.video:
                intent.putExtra("flag","3");
                startActivity(intent);
                break;
        }
    }
}
