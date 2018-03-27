package com.example.android.rsszebra.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import static com.example.android.rsszebra.data.RSSFeedContract.RSSItemEntry.*;

/**
 * Created by vitaliybv on 3/26/18.
 */

public class RSSItemProvider extends ContentProvider {

    private RSSFeedDbHelper mDbHelper;
    public static final String LOG_TAG = RSSItemProvider.class.getSimpleName();
    private static final int ITEMS = 13;
    private static final int ITEM_ID = 5;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(RSSFeedContract.CONTENT_AUTHORITY, RSSFeedContract.PATH_RSS_ITEMS, ITEMS);
        sUriMatcher.addURI(RSSFeedContract.CONTENT_AUTHORITY, RSSFeedContract.PATH_RSS_ITEMS + "/*", ITEM_ID);
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new RSSFeedDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ITEM_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};

                cursor = db.query(TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues initialValues) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                SQLiteDatabase db = mDbHelper.getWritableDatabase();

                StringBuilder sql = new StringBuilder();
                sql.append("INSERT");
                sql.append(" INTO ");
                sql.append(TABLE_NAME);
                sql.append('(');

                Object[] bindArgs = null;

                int size = initialValues.size();
                bindArgs = new Object[size];
                int i = 0;
                for (String colName : initialValues.keySet()) {
                    sql.append((i > 0) ? "," : "");
                    sql.append(colName);
                    bindArgs[i++] = initialValues.get(colName);
                }
                sql.append(')');
                sql.append(" VALUES (");
                for (i = 0; i < size; i++) {
                    sql.append((i > 0) ? ",?" : "?");
                }
                sql.append(')');

                String id = initialValues.get(_ID).toString();
                db.execSQL(sql.toString(), bindArgs);


                getContext().getContentResolver().notifyChange(uri, null);
                return Uri.withAppendedPath(uri, id);

            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int deletedRows;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);

                break;
            case ITEM_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};

                deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }


        if (deletedRows == 0) {
            Log.e(LOG_TAG, "Failed to delete rows" + uri);
            return 0;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return deletedRows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int updatedRows;
        int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                updatedRows = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            case ITEM_ID:
                selection = _ID + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};

                updatedRows = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Updation is not supported for " + uri);
        }

        if (updatedRows == 0) {
            Log.e(LOG_TAG, "Failed to update" + uri);
            return 0;
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return updatedRows;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case ITEMS:
                return CONTENT_LIST_TYPE;
            case ITEM_ID:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }
}
