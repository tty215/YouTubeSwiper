package com.ttydev.youtubeswiper.controller;

import com.ttydev.youtubeswiper.entities.Video;
import com.ttydev.youtubeswiper.usecase.inputport.VideoListInputPort;
import com.ttydev.youtubeswiper.usecase.interfaces.IVideoListUseCase;

public class VideoController {

    private IVideoListUseCase videoListUseCase;

    public VideoController(IVideoListUseCase videoListUseCase) {
        this.videoListUseCase = videoListUseCase;
    }

    public void ListVideo(String channelId) {
        VideoListInputPort input = new VideoListInputPort(channelId);
        videoListUseCase.Handle(input);
    }
}
