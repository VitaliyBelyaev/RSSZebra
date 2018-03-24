package com.example.android.rsszebra;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mTitleTextView = findViewById(R.id.tv_deatails_title);

        Intent intent = getIntent();
        if(intent.hasExtra(Intent.EXTRA_TEXT)){
            String titleOfItem = intent.getStringExtra(Intent.EXTRA_TEXT);
            mTitleTextView.setText(titleOfItem);
        }



    }
}
