package com.example.android.rsszebra.data;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;


import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vitaliybv on 3/24/18.
 */

public class XMLParser {

    private ArrayList<RSSItem> items;
    private RSSItem currentItem;

    public XMLParser() {
        items = new ArrayList<>();
        currentItem = new RSSItem();
    }

    public ArrayList<RSSItem> parseXML(String xml) throws XmlPullParserException, IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

        factory.setNamespaceAware(false);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput(new StringReader(xml));
        boolean insideItem = false;
        int eventType = xpp.getEventType();

        while (eventType != xpp.END_DOCUMENT) {

            if (eventType == xpp.START_TAG) {

                if (xpp.getName().equalsIgnoreCase("item")) {

                    insideItem = true;

                } else if (xpp.getName().equalsIgnoreCase("title")) {

                    if (insideItem) {
                        String title = xpp.nextText();
                        currentItem.setTitle(title);
                    }

                } else if (xpp.getName().equalsIgnoreCase("title")) {

                    if (insideItem) {
                        String link = xpp.nextText();
                        currentItem.setLink(link);
                    }

                } else if (xpp.getName().equalsIgnoreCase("description")) {

                    if (insideItem) {
                        String description = xpp.nextText();
                        currentItem.setDescription(description);
                    }

                } else if (xpp.getName().equalsIgnoreCase("yandex:full-text")) {

                    if (insideItem) {
                        String fullText = xpp.nextText();
                        currentItem.setFullText(fullText);
                    }

                } else if (xpp.getName().equalsIgnoreCase("enclosure")) {

                    if (insideItem) {
                        String type = xpp.getAttributeValue(null, "type");
                        if (type != null && type.equalsIgnoreCase("image/jpeg")) {
                            String imageUrl = xpp.getAttributeValue(null, "url");
                            currentItem.setImageLink(imageUrl);
                        }
                    }
                } else if (xpp.getName().equalsIgnoreCase("pubDate")) {

                    try {
                        SimpleDateFormat inputSdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                        SimpleDateFormat outputSdf = new SimpleDateFormat("dd MMM yyyy, HH:mm",Locale.getDefault());
                        Date date = inputSdf.parse(xpp.nextText());
                        String pubDate = outputSdf.format(date);
                        currentItem.setPubDate(pubDate);
                    } catch (ParseException pe) {
                        pe.printStackTrace();
                    }
                }
            } else if (eventType == xpp.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                insideItem = false;
                items.add(currentItem);
                Log.d("XML_PARSER", currentItem.toString());
                currentItem = new RSSItem();
            }

            if (xpp.getEventType() == xpp.END_TAG && xpp.getName().equalsIgnoreCase("title")) {
                xpp.nextTag();
                if (xpp.getEventType() == xpp.START_TAG && xpp.getName().equalsIgnoreCase("link")) {
                    if (insideItem) {
                        String link = xpp.nextText();
                        currentItem.setLink(link);
                    }
                }
            }
            eventType = xpp.next();
        }

        return items;
    }
}