package com.example.android.rsszebra.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by vitaliybv on 3/24/18.
 */

public class RSSFeedContract {



    private RSSFeedContract(){
        }

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + RSSItemEntry.TABLE_NAME +";" ;

    public static final String CONTENT_AUTHORITY = "com.example.android.rsszebra";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_RSS_ITEMS = "rss_items";

    public static final class RSSItemEntry implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_RSS_ITEMS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RSS_ITEMS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RSS_ITEMS;

        public static final String TABLE_NAME = "rss_items";
        public static final String COLUMN_ITEM_LINK = "link";
        public static final String COLUMN_ITEM_TITLE = "title";
        public static final String COLUMN_ITEM_PUB_DATE = "pub_date";
        public static final String COLUMN_ITEM_DESCRIPTION = "description";
        public static final String COLUMN_ITEM_FULL_TEXT = "full_text";
        public static final String COLUMN_ITEM_IMAGE = "image_link";
    }
}
