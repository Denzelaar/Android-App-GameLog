package com.denzel.darryl.GameLog;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    // Database info
    private static final String DATABASE_NAME = "gamelog.db";
    private static final int DATABASE_VERSION = 2;

    // Logitems
    public static final String TABLE_LOGITEMS = "logitems";
    public static final String COLUMN_LOGITEM_ID = "logitem_id";
    public static final String COLUMN_LOGITEM_TITLE = "logitem";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_STEAMSITE = "steamsite";


    // Creating the table
    private static final String DATABASE_CREATE_LOGITEMS =
            "CREATE TABLE " + TABLE_LOGITEMS +
                    "(" +
                    COLUMN_LOGITEM_ID + " integer primary key autoincrement, " +
                    COLUMN_LOGITEM_TITLE + " text not null, " + COLUMN_DESCRIPTION + " text not null, " + COLUMN_STEAMSITE + " text not null" +
                    ");";


    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Execute the sql to create the table logitems
        database.execSQL(DATABASE_CREATE_LOGITEMS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // With update version gets upgraded, deletes old table and creates new one

        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGITEMS);
        onCreate(db);
    }
}