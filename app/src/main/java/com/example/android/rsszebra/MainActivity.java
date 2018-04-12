package com.example.android.rsszebra;

import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

import com.example.android.rsszebra.data.RSSItem;

import static com.example.android.rsszebra.data.RSSFeedContract.RSSItemEntry.*;

public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<RSSItem>>,
        RSSFeedAdapter.RSSFeedAdapterOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private RSSFeedAdapter rssFeedAdapter;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeLayout;
    public final static String URL_KEY = "url";
    private static boolean PREFERENCE_NEED_UPDATE = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("OnListener", "String value:on create");
        mRecyclerView = findViewById(R.id.rv_rss_list);
        mSwipeLayout = findViewById(R.id.swipeRefreshLayout);
        rssFeedAdapter = new RSSFeedAdapter(this);

        mRecyclerView.setAdapter(rssFeedAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        Bundle args = getPreferredRSSFeed();
        getLoaderManager().initLoader(13, args, this);

        mSwipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh() {
                refreshFeed();
            }
        });

        android.preference.PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (PREFERENCE_NEED_UPDATE) {
            Bundle args = getPreferredRSSFeed();
            getLoaderManager().restartLoader(13, args, this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    public void refreshFeed() {
        Bundle args = getPreferredRSSFeed();
        getLoaderManager().restartLoader(13, args, this);
    }

    @Override
    public Loader<ArrayList<RSSItem>> onCreateLoader(int id, Bundle args) {
        mSwipeLayout.setRefreshing(true);
        String url = args.getString(URL_KEY);
        Log.i("MainActivity", "OnCreateLoader");
        return new RSSFeedLoader(this, url);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<RSSItem>> loader, ArrayList<RSSItem> items) {
        mSwipeLayout.setRefreshing(false);
        ContentResolver contentResolver = getContentResolver();

        for (RSSItem item : items) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ITEM_LINK, item.getLink());
            values.put(COLUMN_ITEM_TITLE, item.getTitle());
            values.put(COLUMN_ITEM_PUB_DATE, item.getPubDate());
            values.put(COLUMN_ITEM_DESCRIPTION, item.getDescription());
            values.put(COLUMN_ITEM_FULL_TEXT, item.getFullText());
            values.put(COLUMN_ITEM_IMAGE, item.getImageLink());

            contentResolver.insert(CONTENT_URI, values);
        }
        rssFeedAdapter.setRSSItems(items);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<RSSItem>> loader) {

    }

    @Override
    public void onClick(RSSItem rssItem) {
        Intent i = new Intent();
        i.putExtra(Intent.EXTRA_TEXT, rssItem.getLink());
        i.setClass(getApplicationContext(), DetailsActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rss_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PREFERENCE_NEED_UPDATE = true;
        // todo Сделать нормальный апдейт лоадера из преференсов
    }

    public Bundle getPreferredRSSFeed() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String key = getString(R.string.pref_url_key);
        String value = sharedPreferences.getString(key, getString(R.string.pref_default_url));
        Bundle args = new Bundle();
        args.putString(URL_KEY, value);
        Log.i("IMPORTANT", "value:" + value);
        return args;
    }
}
