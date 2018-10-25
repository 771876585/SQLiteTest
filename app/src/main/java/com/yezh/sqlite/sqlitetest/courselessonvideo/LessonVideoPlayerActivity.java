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
        String videoUrl = "http://mgcdn.migucloud.com/vi0/ftp/miguread/CLOUD1000096492/56/video48748_56_keep.MP4?duration=2700&owner=198&quality=9&timestamp=20170216100957&title=video48748_56.mp4&vid=0V4Aq7LFFbeEx9plLc0m5&para1=yyy&para2=xxx";
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
