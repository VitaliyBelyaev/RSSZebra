package com.example.android.rsszebra.data;

import java.util.Date;

/**
 * Created by vitaliybv on 3/24/18.
 */

public class RSSItem {

    private String title;
    private String description;
    private Date pubDate;
    private String fullText;
    private String image;

    public RSSItem(){

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

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "RSSItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", pubDate=" + pubDate +
                ", fullText='" + fullText + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
