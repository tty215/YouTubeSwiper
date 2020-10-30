package com.ttydev.youtubeswiper.entities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Channel {

    private String channelId;

    // チャンネル名
    private String title;

    // チャンネル説明
    private String description;

    // サムネイル画像URL
    private String thumbnail;

    // チャンネル開設日
    private Date publishedAt;

    public Channel(String channelId, String title, String description, String thumbnail, String publishedAt) {
        this.channelId = channelId;
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss'Z'");
        try {
            this.publishedAt = sdFormat.parse(publishedAt);
        } catch (ParseException e) {
            this.publishedAt = new Date();
        }
    }


    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Date getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Date publishedAt) {
        this.publishedAt = publishedAt;
    }
}
