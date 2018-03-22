package com.example.android.rsszebra;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;

/**
 * Created by vitaliybv on 3/22/18.
 */

public class RSSFeedLoader extends AsyncTaskLoader<RSSFeed> {

    private String url;
    private RSSReader rssReader;

    public RSSFeedLoader(Context context, String url, RSSReader rssReader) {
        super(context);
        this.url = url;
        this.rssReader = rssReader;
    }


    @Override
    protected void onStartLoading() {

        forceLoad();
    }


    @Override
    public RSSFeed loadInBackground() {
        if (url == null) {
            return null;
        }

        try {
            return rssReader.load(url);
        } catch (RSSReaderException e) {
            e.printStackTrace();
        }
        return null;
    }
}
