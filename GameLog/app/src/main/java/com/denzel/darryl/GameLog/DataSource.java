package com.denzel.darryl.GameLog;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by darryl on 02-Mar-16.
 */
public class DataSource {
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] logitemAllColumns = { MySQLiteHelper.COLUMN_LOGITEM_ID, MySQLiteHelper.COLUMN_LOGITEM_TITLE,
            MySQLiteHelper.COLUMN_DESCRIPTION, MySQLiteHelper.COLUMN_STEAMSITE };

    public DataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
        database = dbHelper.getWritableDatabase();
        dbHelper.close();
    }

    // Opens the database to use it
    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    // Closes the database when you no longer need it
    public void close() {
        dbHelper.close();
    }

    public long createLogitem(String logitem, String description, String steamSite) {
        // If the database is not open yet, open it
        if (!database.isOpen())
            open();

        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_LOGITEM_TITLE, logitem);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, description);
        values.put(MySQLiteHelper.COLUMN_STEAMSITE, steamSite);

        long insertId = database.insert(MySQLiteHelper.TABLE_LOGITEMS, null, values);

        // If the database is open, close it
        if (database.isOpen())
            close();

        return insertId;
    }

    public void deleteLogitem(LogItem logItem) {
        if (!database.isOpen())
            open();

        database.delete(MySQLiteHelper.TABLE_LOGITEMS, MySQLiteHelper.COLUMN_LOGITEM_ID +
                " =?", new String[]{Long.toString(logItem.getId())});

        if (database.isOpen())
            close();

    }

    public void updateLogitem(LogItem logItem) {
        if (!database.isOpen())
            open();

        ContentValues args = new ContentValues();
        args.put(MySQLiteHelper.COLUMN_LOGITEM_TITLE, logItem.getLogitem());
        args.put(MySQLiteHelper.COLUMN_DESCRIPTION, logItem.getDescription());
        args.put(MySQLiteHelper.COLUMN_STEAMSITE, logItem.getSteamSite());

        database.update(MySQLiteHelper.TABLE_LOGITEMS, args, MySQLiteHelper.COLUMN_LOGITEM_ID + "=?",
                new String[]{Long.toString(logItem.getId())});
        if (database.isOpen())
            close();
    }

    public List<LogItem> getAllLogitem() {
        if (!database.isOpen())
            open();

        List<LogItem> logItems = new ArrayList<LogItem>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_LOGITEMS, logitemAllColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            LogItem logItem = cursorToLogitem(cursor);
            logItems.add(logItem);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();

        if (database.isOpen())
            close();

        return logItems;
    }
    private LogItem cursorToLogitem(Cursor cursor) {
        try {
            LogItem logItem = new LogItem();
            logItem.setId(cursor.getLong(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_LOGITEM_ID)));
            logItem.setLogitem(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_LOGITEM_TITLE)));
            logItem.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_DESCRIPTION)));
            logItem.setSteamSite(cursor.getString(cursor.getColumnIndexOrThrow(MySQLiteHelper.COLUMN_STEAMSITE)));

            return logItem;
        } catch(CursorIndexOutOfBoundsException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    public LogItem getLogitem(long columnId) {
        if (!database.isOpen())
            open();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_LOGITEMS, logitemAllColumns, MySQLiteHelper.COLUMN_LOGITEM_ID + "=?", new String[] { Long.toString(columnId)}, null, null, null);

        cursor.moveToFirst();
        LogItem logItem = cursorToLogitem(cursor);
        cursor.close();

        if (database.isOpen())
            close();

        return logItem;
    }
}
