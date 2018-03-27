package com.example.android.rsszebra.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by vitaliybv on 3/24/18.
 */

public class RSSFeedDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "rsszebra.db";
    private Context context;

    public RSSFeedDbHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + RSSFeedContract.RSSItemEntry.TABLE_NAME + " (" +
                        RSSFeedContract.RSSItemEntry.COLUMN_ITEM_LINK + " TEXT PRIMARY KEY NOT NULL," +
                        RSSFeedContract.RSSItemEntry.COLUMN_ITEM_TITLE+ " TEXT NOT NULL," +
                        RSSFeedContract.RSSItemEntry.COLUMN_ITEM_PUB_DATE+ " TEXT NOT NULL," +
                        RSSFeedContract.RSSItemEntry.COLUMN_ITEM_DESCRIPTION+ " TEXT," +
                        RSSFeedContract.RSSItemEntry.COLUMN_ITEM_FULL_TEXT+ " TEXT," +
                        RSSFeedContract.RSSItemEntry.COLUMN_ITEM_IMAGE + " TEXT) WITHOUT ROWID;";

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(RSSFeedContract.SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
