package com.example.android.rsszebra;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

import com.example.android.rsszebra.data.RSSFeedContract;
import com.example.android.rsszebra.data.RSSItem;

import static com.example.android.rsszebra.data.RSSFeedContract.RSSItemEntry.*;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<RSSItem>>,
                   RSSFeedAdapter.RSSFeedAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    public final static String URL_STRING = "http://www.vesti.ru/vesti.rss";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_rss_list);
        mSwipeLayout = findViewById(R.id.swipeRefreshLayout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        getLoaderManager().initLoader(13,null,this);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
               refreshFeed();
            }
        });
    }


    public void refreshFeed(){
        getLoaderManager().restartLoader(13,null, this);
    }

    @Override
    public Loader<ArrayList<RSSItem>> onCreateLoader(int id, Bundle args) {
        mSwipeLayout.setRefreshing(true);
        return new RSSFeedLoader(this, URL_STRING);

    }

    @Override
    public void onLoadFinished(Loader<ArrayList<RSSItem>> loader, ArrayList<RSSItem> items) {
        mSwipeLayout.setRefreshing(false);
        ContentResolver contentResolver = getContentResolver();

        for(RSSItem item:items){
            ContentValues values = new ContentValues();
            values.put(COLUMN_ITEM_LINK , item.getLink());
            values.put(COLUMN_ITEM_TITLE, item.getTitle());
            values.put(COLUMN_ITEM_PUB_DATE, item.getPubDate());
            values.put(COLUMN_ITEM_DESCRIPTION, item.getDescription());
            values.put(COLUMN_ITEM_FULL_TEXT, item.getFullText());
            values.put(COLUMN_ITEM_IMAGE, item.getImageLink());



            contentResolver.insert(CONTENT_URI, values);
        }
        mRecyclerView.setAdapter(new RSSFeedAdapter(items, this));

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<RSSItem>> loader) {

    }

    @Override
    public void onClick(RSSItem rssItem) {
        Log.i("Intent link", "link:" + rssItem.getLink());
        Intent i = new Intent();
        i.putExtra(Intent.EXTRA_TEXT, rssItem.getLink());
        i.setClass(getApplicationContext(), DetailsActivity.class);
        startActivity(i);
    }
}
