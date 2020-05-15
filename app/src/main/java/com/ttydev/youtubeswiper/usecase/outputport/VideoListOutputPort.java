package com.ttydev.youtubeswiper.usecase.outputport;

import com.ttydev.youtubeswiper.entities.Video;

public class VideoListOutputPort {

    private Video[] videos;

    public VideoListOutputPort(Video[] videos) {
        this.videos = videos;
    }
}
