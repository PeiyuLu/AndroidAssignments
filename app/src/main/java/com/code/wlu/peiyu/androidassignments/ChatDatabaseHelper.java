package com.code.wlu.peiyu.androidassignments;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Messages.db";
    private static final int VERSION_NUM = 3 ;
    public static final String TABLE_NAME = "message";
    public static final String KEY_ID = "ID" ;
    public static final String KEY_MESSAGE = "message" ;

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //create "create table" SQL sentence
        //String sql = "CREATE TABLE";
        String sql = "CREATE TABLE " + TABLE_NAME + " ("+ KEY_ID +" integer primary key autoincrement," + KEY_MESSAGE + " varchar(256))";
        sqLiteDatabase.execSQL(sql);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
//        Log.i(“ChatDatabaseHelper“, “Calling onUpgrade, oldVersion=” + oldVer + “ newVersion=” + newVer);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + i + " newVersion=" + i1);

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
        //recreate database
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);
//        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + i + " newVersion=" + i1);

    }
}
