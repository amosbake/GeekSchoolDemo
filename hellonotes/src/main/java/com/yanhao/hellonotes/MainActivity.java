package com.yanhao.hellonotes;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    private SQLiteDatabase dbWriter;
    private Button textBtn, imgBtn, videotBtn;
    private ListView lv;
    private Intent intent;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;
    private MyAdapter adapter;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        lv = (ListView) findViewById(R.id.list);
        textBtn = (Button) findViewById(R.id.text);
        imgBtn = (Button) findViewById(R.id.img);
        videotBtn = (Button) findViewById(R.id.video);
        textBtn.setOnClickListener(this);
        imgBtn.setOnClickListener(this);
        videotBtn.setOnClickListener(this);
        notesDB = new NotesDB(this);
        dbReader = notesDB.getWritableDatabase();

    }


    @Override
    public void onClick(View v) {
        intent = new Intent(this, AddContentActivity.class);
        switch (v.getId()) {
            case R.id.text:
                intent.putExtra("flag", "1");
                startActivity(intent);
                break;
            case R.id.img:
                intent.putExtra("flag", "2");
                startActivity(intent);
                break;
            case R.id.video:
                intent.putExtra("flag", "3");
                startActivity(intent);
                break;
        }
    }

    public void selectDB() {
        cursor = dbReader.query(NotesDB.TABLE_NAME, null, null, null, null, null, null);
        adapter = new MyAdapter(this, cursor);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectDB();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        cursor.moveToPosition(position);
        Intent tempIntent=new Intent(this,SelectActivity.class);
        tempIntent.putExtra(NotesDB.ID,cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));
        tempIntent.putExtra(NotesDB.CONTENT,cursor.getString(cursor.getColumnIndex(NotesDB.CONTENT)));
        tempIntent.putExtra(NotesDB.TIME,cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));
        tempIntent.putExtra(NotesDB.PATH,cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));
        tempIntent.putExtra(NotesDB.VIDEO,cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));
        startActivity(tempIntent);
    }
}
