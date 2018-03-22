package com.example.android.rsszebra;

import android.app.LoaderManager;
import android.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSReader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<RSSFeed> {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    public final static String URL_STRING = "http://www.androidcentral.com/feed";
    private RSSReader rssReader;
    private RSSFeedListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.rv_rss_list);
        mSwipeLayout = findViewById(R.id.swipeRefreshLayout);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        rssReader = new RSSReader();

        getLoaderManager().initLoader(13,null,this);


        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                //TODO Заставить лоадер работать на рефреше
               // getLoaderManager().restartLoader(13,null, MainActivity.class);
            }
        });
    }

    @Override
    public Loader<RSSFeed> onCreateLoader(int id, Bundle args) {
        mSwipeLayout.setRefreshing(true);
        return new RSSFeedLoader(this, URL_STRING, rssReader);

    }

    @Override
    public void onLoadFinished(Loader<RSSFeed> loader, RSSFeed rssFeed) {
        mSwipeLayout.setRefreshing(false);
        mRecyclerView.setAdapter(new RSSFeedListAdapter(rssFeed));

    }

    @Override
    public void onLoaderReset(Loader<RSSFeed> loader) {
        adapter.setRssFeed(null);

    }
}
