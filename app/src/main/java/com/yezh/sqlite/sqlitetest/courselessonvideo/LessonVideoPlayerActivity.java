package com.yezh.sqlite.sqlitetest.courselessonvideo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yezh.sqlite.sqlitetest.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yezh on 2018/10/11 17:49.
 */

public class LessonVideoPlayerActivity extends AppCompatActivity {

    private LessonVideoPlayer mVideoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_video_player);
        init();
    }

    private void init() {
        mVideoPlayer = (LessonVideoPlayer) findViewById(R.id.lesson_video_player);
        String videoUrl = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
        List<VideoInfo> catalogueList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            VideoInfo videoInfo = new VideoInfo();
            videoInfo.setLessonId(i + "");
            videoInfo.setLessonName("视频目录播放课程" + i);
            videoInfo.setOrderNum(i+"");
            catalogueList.add(videoInfo);
        }

        mVideoPlayer.setUp(videoUrl, null);
        LessonVideoPlayerController controller = new LessonVideoPlayerController(this);
        controller.setTitle("视频课程标题");
        controller.setCatalogueList(catalogueList);
        mVideoPlayer.setController(controller);
        mVideoPlayer.enterFullScreen();
    }

    @Override
    protected void onStop() {
        super.onStop();
        LessonVideoPlayerManager.instance().releaseVideoPlayer();
    }

}
