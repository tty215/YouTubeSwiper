package com.ttydev.youtubeswiper.repository.interfaces;

import com.ttydev.youtubeswiper.entities.Video;

public interface IVideoRepository {
    Video[] ListByChannelId(String channelId);
    void Save(Video video);
}
