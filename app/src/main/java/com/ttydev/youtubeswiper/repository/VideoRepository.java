package com.ttydev.youtubeswiper.repository;

import android.os.Debug;
import android.util.Log;

import com.ttydev.youtubeswiper.entities.Video;
import com.ttydev.youtubeswiper.repository.interfaces.IVideoRepository;

// TODO: sqlに格納する処理を実装する
public class VideoRepository implements IVideoRepository {
    @Override
    public Video[] ListByChannelId(String channelId) {
        Log.d("Video", "call VideoRepository.ListByChannelId()");
        return new Video[0];
    }

    @Override
    public void Save(Video video) {
        Log.d("Video", "call VideoRepository.Save()");
    }
}
