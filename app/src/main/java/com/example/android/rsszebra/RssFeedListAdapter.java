package com.example.android.rsszebra;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSItem;

import java.util.List;

/**
 * Created by vitaliybv on 3/20/18.
 */

public class RssFeedListAdapter extends RecyclerView.Adapter<RssFeedListAdapter.ItemViewHolder> {

    private RSSFeed mRSSFeed;

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private View rssFeedView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.rssFeedView = itemView;
        }

    }

    RssFeedListAdapter(RSSFeed mRSSFeed) {
        this.mRSSFeed= mRSSFeed;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.rss_feed_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
       List<RSSItem> items =  mRSSFeed.getItems();

    }

    @Override
    public int getItemCount() {
        return mRSSFeed.getItems().size();
    }


}
