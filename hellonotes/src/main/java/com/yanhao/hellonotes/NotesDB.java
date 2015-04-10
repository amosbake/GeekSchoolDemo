package com.yanhao.hellonotes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by yons on 2015/4/3.
 */
public class NotesDB extends SQLiteOpenHelper {
    public static final String TABLE_NAME="notes";
    public static final String CONTENT="content";
    public static final String PATH="path";
    public static final String VIDEO="video";
    public static final String ID="_id";
    public static final String TIME="time";

    public NotesDB(Context context) {
        super(context, "notes.db", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(" +
               ID+" integer primary key autoincrement,"+
                CONTENT+" text not null,"+
                PATH+" text ,"+
                VIDEO+" text ,"+
                TIME+" text not null"+")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+TABLE_NAME);
        onCreate(db);
    }
}
