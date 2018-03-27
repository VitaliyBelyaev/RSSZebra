package com.example.android.rsszebra;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import static com.example.android.rsszebra.data.RSSFeedContract.RSSItemEntry.*;

public class DetailsActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private TextView mPubDateTextView;
    private TextView mDescriptionTextView;
    private ImageView mImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mTitleTextView = findViewById(R.id.tv_details_title);
        mPubDateTextView = findViewById(R.id.tv_details_date);
        mDescriptionTextView = findViewById(R.id.tv_details_description);
        mImageView = findViewById(R.id.iv_details_image);

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

            String imagePath = cursor.getString(imageIndex);
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

            mImageView.setImageBitmap(null);
            mTitleTextView.setText(title);
            mPubDateTextView.setText(pubDate);
            mDescriptionTextView.setText(text);

            cursor.close();
        }


    }
}
