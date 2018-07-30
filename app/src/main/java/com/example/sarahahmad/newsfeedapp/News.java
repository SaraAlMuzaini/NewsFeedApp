package com.example.sarahahmad.newsfeedapp;

/**
 * Created by sarahahmad on 08/10/2017 AD.
 */

public class News {
    String mTitle;
    String mSection;
    String mAuthor;
    String mPublishedDate;
    String mUrl;

    public News(String title, String section, String author, String publishedDate, String url) {
        this.mTitle = title;
        this.mSection = section;
        this.mAuthor = author;
        this.mPublishedDate = publishedDate;
        this.mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public String getUrl() {
        return mUrl;
    }
}
