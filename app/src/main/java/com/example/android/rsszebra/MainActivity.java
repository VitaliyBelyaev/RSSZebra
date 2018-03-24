package com.example.android.rsszebra;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.example.android.rsszebra.data.RSSItem;

import java.util.ArrayList;

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
        mRecyclerView.setAdapter(new RSSFeedAdapter(items, this));

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<RSSItem>> loader) {

    }

    @Override
    public void onClick(RSSItem rssItem) {
        Intent intentToStartDetailsActivity = new Intent(this, DetailsActivity.class);
        intentToStartDetailsActivity.putExtra(Intent.EXTRA_TEXT, rssItem.getDescription());
        startActivity(intentToStartDetailsActivity);
    }
}
