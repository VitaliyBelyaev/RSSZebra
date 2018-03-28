package com.example.android.rsszebra;

import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.android.rsszebra.data.RSSFeedContract.RSSItemEntry.*;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Bitmap> {

    private TextView mTitleTextView;
    private TextView mPubDateTextView;
    private TextView mDescriptionTextView;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    private String imageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mTitleTextView = findViewById(R.id.tv_details_title);
        mPubDateTextView = findViewById(R.id.tv_details_date);
        mDescriptionTextView = findViewById(R.id.tv_details_description);
        mImageView = findViewById(R.id.iv_details_image);
        mProgressBar = findViewById(R.id.details_progress_bar);

        String uriAsString = getIntent().getStringExtra(Intent.EXTRA_TEXT);

        String[] projection =
                {
                        COLUMN_ITEM_LINK,
                        COLUMN_ITEM_IMAGE,
                        COLUMN_ITEM_TITLE,
                        COLUMN_ITEM_DESCRIPTION,
                        COLUMN_ITEM_FULL_TEXT,
                        COLUMN_ITEM_PUB_DATE
                };

        String selection = COLUMN_ITEM_LINK + "=?";
        String[] selectionArgs = new String[]{uriAsString};


        Cursor cursor = getContentResolver().query(CONTENT_URI, projection, selection,
                selectionArgs,
                null);

        if (cursor.moveToFirst()) {
            int imageIndex = cursor.getColumnIndexOrThrow(COLUMN_ITEM_IMAGE);
            int titleIndex = cursor.getColumnIndexOrThrow(COLUMN_ITEM_TITLE);
            int pubDateIndex = cursor.getColumnIndexOrThrow(COLUMN_ITEM_PUB_DATE);
            int descriptionIndex = cursor.getColumnIndexOrThrow(COLUMN_ITEM_DESCRIPTION);
            int fullTextIndex = cursor.getColumnIndexOrThrow(COLUMN_ITEM_FULL_TEXT);

            imageUrl = cursor.getString(imageIndex);
            if (imageUrl != null){
                getLoaderManager().initLoader(13,null,this);
            }
            String title = cursor.getString(titleIndex);
            String pubDate = cursor.getString(pubDateIndex);
            String description = cursor.getString(descriptionIndex);
            String fullText = cursor.getString(fullTextIndex);



            String text;
            if (fullText == null || fullText.isEmpty()) {
                text = description;
            } else {
                text = fullText;
            }


            mTitleTextView.setText(title);
            mPubDateTextView.setText(pubDate);
            mDescriptionTextView.setText(text);

            cursor.close();
        }
    }

    @Override
    public Loader<Bitmap> onCreateLoader(int id, Bundle args) {
        mImageView.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        return new ImageLoader(this, imageUrl);
    }

    @Override
    public void onLoadFinished(Loader<Bitmap> imageLoader, Bitmap image) {
        mProgressBar.setVisibility(View.GONE);
        mImageView.setVisibility(View.VISIBLE);
        mImageView.setImageBitmap(image);
    }

    @Override
    public void onLoaderReset(Loader<Bitmap> loader) {
        mImageView.setImageBitmap(null);
    }

    static class ImageLoader extends AsyncTaskLoader<Bitmap> {

        private String imageUrl;
        private OkHttpClient client;

        public ImageLoader(Context context, String imageUrl) {
            super(context);
            this.imageUrl = imageUrl;
            client = new OkHttpClient();
        }

        @Override
        protected void onStartLoading() {
            forceLoad();
        }


        @Override
        public Bitmap loadInBackground() {
            try {
                Request request = new Request.Builder()
                        .url(imageUrl)
                        .build();

                Response response = client.newCall(request).execute();
                InputStream inputStream = response.body().byteStream();
                return BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
