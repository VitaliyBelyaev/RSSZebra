# RSSZebra
+ This is simple RSS reader application, that get RSS xml using hardcoded url (http://www.vesti.ru/vesti.rss) and parse result. 

+ Result presents as list of RSS items using RecyclerView, you can tap on item and application opens detailed information in new activity.

+ After loading and parsing result app stores it in local DB. 

+ I used Loaders to make "heavy" operations in background such as loading data from internet (okhttp) and parse RSS xml to list of RSSItem objects.
