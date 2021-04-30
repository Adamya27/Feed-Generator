package com.example.FeedGenerator.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Feed {
    private String title;
    private String description;
    private String url;
    private List<FeedItemDao> item;
    private String pubDate;

    public Feed() {
    }

    public Feed(String title, String description, String url, List<FeedItemDao> item, String pubDate) {
        this.title = title;
        this.description = description;
        this.url = url;
        this.item = item;
        this.pubDate = pubDate;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<FeedItemDao> getItem() {
        return item;
    }

    public void setItem(List<FeedItemDao> item) {
        this.item = item;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }
}
