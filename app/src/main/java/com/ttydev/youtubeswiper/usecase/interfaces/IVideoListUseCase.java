package com.ttydev.youtubeswiper.usecase.interfaces;

import com.ttydev.youtubeswiper.usecase.inputport.VideoListInputPort;

public interface IVideoListUseCase {
    void Handle(VideoListInputPort input);
}
