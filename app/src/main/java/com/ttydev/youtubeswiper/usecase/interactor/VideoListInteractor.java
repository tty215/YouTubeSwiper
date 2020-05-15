package com.ttydev.youtubeswiper.usecase.interactor;

import com.ttydev.youtubeswiper.entities.Video;
import com.ttydev.youtubeswiper.presenter.interfaces.IVideoListPresenter;
import com.ttydev.youtubeswiper.repository.interfaces.IVideoRepository;
import com.ttydev.youtubeswiper.usecase.inputport.VideoListInputPort;
import com.ttydev.youtubeswiper.usecase.interfaces.IVideoListUseCase;
import com.ttydev.youtubeswiper.usecase.outputport.VideoListOutputPort;

import java.util.List;

public class VideoListInteractor implements IVideoListUseCase {

    private IVideoRepository videoRepository;
    private IVideoListPresenter presenter;

    public VideoListInteractor(IVideoRepository videoRepository, IVideoListPresenter presenter) {
        this.videoRepository = videoRepository;
        this.presenter = presenter;
    }

    @Override
    public void Handle(VideoListInputPort input) {
        String channelId = input.channelId;

        // TODO: APIアクセスして動画一覧を取得する？
        Video[] videos = new Video[0];

        VideoListOutputPort output = new VideoListOutputPort(videos);

        presenter.Complete(output);
    }
}
