package com.yanhao.hellonotes;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.VideoView;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AddContentActivity extends ActionBarActivity implements View.OnClickListener{
    private String val;
    private Button saveBtn,cancelBtn;
    private EditText editText;
    private ImageView c_img;
    private VideoView c_video;
    private NotesDB notesDB;
    private SQLiteDatabase dbWrite;
    private File phoneFile,videoFile;
    private static final int REQUEST_PHOTO=1;
    private static final int REQUEST_Viedo=2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_content);
        val=getIntent().getStringExtra("flag");

        initView();

    }

    private void initView() {
        saveBtn= (Button) findViewById(R.id.save);
        cancelBtn= (Button) findViewById(R.id.cancel);
        editText= (EditText) findViewById(R.id.et_text);
        c_img= (ImageView) findViewById(R.id.c_img);
        c_video= (VideoView) findViewById(R.id.c_video);

        saveBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);

        notesDB=new NotesDB(this);
        dbWrite=notesDB.getWritableDatabase();


        if ("1".equals(val)){//文字
            c_img.setVisibility(View.GONE);
            c_video.setVisibility(View.GONE);
        }
        if ("2".equals(val)){//图片
            c_img.setVisibility(View.VISIBLE);
            c_video.setVisibility(View.GONE);
            Intent iimg=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            phoneFile=new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+File.separator+new Date().getTime()+".jpg");
            iimg.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(phoneFile));
            startActivityForResult(iimg, REQUEST_PHOTO);
        }
        if ("3".equals(val)){//视频
            c_img.setVisibility(View.GONE);
            c_video.setVisibility(View.VISIBLE);
            Intent iVideo=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            videoFile=new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+File.separator+new Date().getTime()+".mp4");
            iVideo.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(videoFile));
            startActivityForResult(iVideo,REQUEST_Viedo);
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_PHOTO){
            Bitmap bitmap= BitmapFactory.decodeFile(phoneFile.getAbsolutePath());
            c_img.setImageBitmap(bitmap);
        }
        if (requestCode==REQUEST_Viedo){
            c_video.setVideoURI(Uri.fromFile(videoFile));
            c_video.start();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save:
                addDB();
                finish();
                break;
            case R.id.cancel:
                finish();
                break;
        }
    }
    public void addDB(){
        ContentValues cv=new ContentValues();
        cv.put(NotesDB.CONTENT,editText.getText().toString());
        cv.put(NotesDB.TIME,getTime());
        if(phoneFile!=null){
            cv.put(NotesDB.PATH,phoneFile.getAbsolutePath());
        }
        if(videoFile!=null){
            cv.put(NotesDB.VIDEO,videoFile.getAbsolutePath());
        }
        dbWrite.insert(NotesDB.TABLE_NAME,null,cv);
    }
    public String getTime(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date date=new Date();
        return sdf.format(date);

    }
}
