package com.yanhao.hellonotes;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;


public class SelectActivity extends ActionBarActivity implements View.OnClickListener {
    private static final String TAG = "SelectActivity";
    private Button s_delete, s_cancel;
    private TextView s_content;
    private ImageView s_img;
    private VideoView s_video;
    private NotesDB notesDB;
    private SQLiteDatabase dbWriter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        initView();
        checkData();
    }

    private void checkData() {
        Intent intent = getIntent();
        if (TextUtils.isEmpty(intent.getStringExtra(NotesDB.PATH))) {
            s_img.setVisibility(View.GONE);
        } else {
            s_img.setVisibility(View.VISIBLE);
            Bitmap bitmap = getImageThumbnail(intent.getStringExtra(NotesDB.PATH), 300, 500);
            s_img.setImageBitmap(bitmap);
        }
        if (TextUtils.isEmpty(intent.getStringExtra(NotesDB.VIDEO))) {
            s_video.setVisibility(View.GONE);
        } else {
            s_video.setVisibility(View.VISIBLE);
            s_video.setVideoURI(Uri.parse(intent.getStringExtra(NotesDB.VIDEO)));

        }
        s_content.setText(intent.getStringExtra(NotesDB.CONTENT));


    }

    private void initView() {
        s_delete = (Button) findViewById(R.id.s_delete);
        s_cancel = (Button) findViewById(R.id.s_return);
        s_content = (TextView) findViewById(R.id.s_tv);
        s_img = (ImageView) findViewById(R.id.s_img);
        s_video = (VideoView) findViewById(R.id.s_video);
        notesDB = new NotesDB(this);
        dbWriter = notesDB.getWritableDatabase();
        s_delete.setOnClickListener(this);
        s_cancel.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.s_delete:
                deleteData();
                finish();
                break;
            case R.id.s_return:
                finish();
                break;
        }
    }

    public void deleteData() {
        dbWriter.delete(NotesDB.TABLE_NAME, NotesDB.ID + "=?", new String[]{String.valueOf(getIntent().getIntExtra(NotesDB.ID, 0))});
    }

    private Bitmap getViedoThumbnail(String uriVideo, int width, int height) {
        Bitmap bitmap = null;
        bitmap = ThumbnailUtils.createVideoThumbnail(uriVideo, MediaStore.Images.Thumbnails.MICRO_KIND);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    public Bitmap getImageThumbnail(String uri, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        bitmap = BitmapFactory.decodeFile(uri, options);
        int beWidth = options.outWidth / width;
        int beHeight = options.outHeight / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        options.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(uri, options);
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        s_video.start();
    }
}
