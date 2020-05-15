package com.ttydev.youtubeswiper.entities;

import java.net.URL;

public class Video {

    private String videoId;

    // 投稿チャンネル
    private Channel channel;

    // 動画タイトル
    private String title;

    // 動画説明欄
    private String description;

    // サムネイルurl
    private URL thumbnailsUrl;

    // サムネイル幅
    private int thumbnailsWidth;

    // サムネイル高さ
    private int thumbnailsHeight;

    // 投稿日時
    private String publishedAt;
}
