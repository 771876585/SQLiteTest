package com.yezh.sqlite.sqlitetest.courselessonvideo;

/**
 * 视频播放器管理器.
 */
public class LessonVideoPlayerManager {

    private LessonVideoPlayer mVideoPlayer;

    private LessonVideoPlayerManager() {
    }

    private static LessonVideoPlayerManager sInstance;

    public static synchronized LessonVideoPlayerManager instance() {
        if (sInstance == null) {
            sInstance = new LessonVideoPlayerManager();
        }
        return sInstance;
    }

    public LessonVideoPlayer getCurrentVideoPlayer() {
        return mVideoPlayer;
    }

    public void setCurrentVideoPlayer(LessonVideoPlayer videoPlayer) {
        if (mVideoPlayer != videoPlayer) {
            releaseVideoPlayer();
            mVideoPlayer = videoPlayer;
        }
    }

    public void suspendVideoPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying())) {
            mVideoPlayer.pause();
        }
    }

    public void resumeVideoPlayer() {
        if (mVideoPlayer != null && (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused())) {
            mVideoPlayer.restart();
        }
    }

    public void releaseVideoPlayer() {
        if (mVideoPlayer != null) {
            mVideoPlayer.release();
            mVideoPlayer = null;
        }
    }
}
