package com.example.android.rsszebra.data;

import java.util.Date;

/**
 * Created by vitaliybv on 3/24/18.
 */

public class RSSItem {

    private String link;
    private String title;
    private String description;
    private String pubDate;
    private String fullText;
    private String imageLink;

    public RSSItem(){

    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    @Override
    public String toString() {
        return "RSSItem{" +
                "link='" + link + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate=" + pubDate +
                ", fullText='" + fullText + '\'' +
                ", imageLink='" + imageLink + '\'' +
                '}';
    }
}
