package com.example.android.rsszebra.data;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by vitaliybv on 3/24/18.
 */

public class XMLParser {

    private ArrayList<RSSItem> items;
    private RSSItem currentItem;

    public XMLParser(){
        items = new ArrayList<>();
        currentItem = new RSSItem();
    }

    public ArrayList<RSSItem> parseXML(String xml) throws XmlPullParserException, IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

        factory.setNamespaceAware(false);
        XmlPullParser xmlPullParser = factory.newPullParser();

        xmlPullParser.setInput(new StringReader(xml));
        boolean insideItem = false;
        int eventType = xmlPullParser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG) {

                if (xmlPullParser.getName().equalsIgnoreCase("item")) {

                    insideItem = true;

                } else if (xmlPullParser.getName().equalsIgnoreCase("title")) {

                    if (insideItem) {
                        String title = xmlPullParser.nextText();
                        currentItem.setTitle(title);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("description")) {

                    if (insideItem) {
                        String description = xmlPullParser.nextText();
                        currentItem.setDescription(description);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("full-text")) {

                    if (insideItem) {
                        String fullText = xmlPullParser.nextText();
                        currentItem.setFullText(fullText);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("pubDate")) {
                    @SuppressWarnings("deprecation")
                    Date pubDate = new Date(xmlPullParser.nextText());
                    currentItem.setPubDate(pubDate);
                }

            } else if (eventType == XmlPullParser.END_TAG && xmlPullParser.getName().equalsIgnoreCase("item")) {
                insideItem = false;
                items.add(currentItem);
                currentItem = new RSSItem();
            }
            eventType = xmlPullParser.next();
        }

        return items;
    }
}
