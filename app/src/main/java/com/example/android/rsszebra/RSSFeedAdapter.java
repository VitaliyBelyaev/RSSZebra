package com.example.android.rsszebra;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.rsszebra.data.RSSItem;

import java.util.ArrayList;

/**
 * Created by vitaliybv on 3/20/18.
 */

public class RSSFeedAdapter extends RecyclerView.Adapter<RSSFeedAdapter.ItemViewHolder> {

    private ArrayList<RSSItem> items;
    private final RSSFeedAdapterOnClickHandler mClickHandler;

    public interface RSSFeedAdapterOnClickHandler {
        void onClick(RSSItem rssItem);
    }

    RSSFeedAdapter(RSSFeedAdapterOnClickHandler mClickHandler) {
        this.mClickHandler = mClickHandler;
    }

    public void setRSSItems(ArrayList<RSSItem> items){
        this.items = items;
        notifyDataSetChanged();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private View rssFeedView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            this.rssFeedView = itemView;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            RSSItem rssItem = items.get(adapterPosition);
            mClickHandler.onClick(rssItem);
        }
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
        RSSItem rssItem = items.get(position);
        ((TextView) holder.rssFeedView.findViewById(R.id.tv_title)).setText(rssItem.getTitle());
        ((TextView) holder.rssFeedView.findViewById(R.id.tv_pub_date)).setText(rssItem.getPubDate());
    }

    @Override
    public int getItemCount() {
        if (items == null) return 0;
        return items.size();
    }

}
