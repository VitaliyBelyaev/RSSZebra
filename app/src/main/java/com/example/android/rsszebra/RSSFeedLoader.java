package com.example.android.rsszebra;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.example.android.rsszebra.data.RSSItem;
import com.example.android.rsszebra.data.XMLParser;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vitaliybv on 3/22/18.
 */

public class RSSFeedLoader extends AsyncTaskLoader<ArrayList<RSSItem>> {

    private String url;
    private OkHttpClient client;
    private XMLParser xmlParser;

    public RSSFeedLoader(Context context, String url) {
        super(context);
        this.url = url;
        client = new OkHttpClient();
        xmlParser = new XMLParser();
    }


    @Override
    protected void onStartLoading() {
        Log.d("RSSFeedLoader","in onStartLoading");
        forceLoad();
    }


    @Override
    public ArrayList<RSSItem> loadInBackground() {
        Log.d("RSSFeedLoader","in loadInBackground");
        if (url == null) {
            return null;
        }

        try {
            String response = run(url);
            return xmlParser.parseXML(response);
        } catch (XmlPullParserException x) {
            x.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
