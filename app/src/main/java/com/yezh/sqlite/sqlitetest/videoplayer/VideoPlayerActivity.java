package com.yezh.sqlite.sqlitetest.videoplayer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.yezh.sqlite.sqlitetest.R;

public class VideoPlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        final VideoPlayer videoPlayer = (VideoPlayer)findViewById(R.id.video_player);
        Button button = (Button)findViewById(R.id.btn_video);

        String videoUrl = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
        videoPlayer.setUp(videoUrl, null);

        MyVideoPlayerController controller = new MyVideoPlayerController(this);
        videoPlayer.setController(controller);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                videoPlayer.enterFullScreen();
                if (videoPlayer.isIdle()) {
                    Toast.makeText(VideoPlayerActivity.this, "要点击播放后才能进入小窗口", Toast.LENGTH_SHORT).show();
                } else {
                    videoPlayer.enterTinyWindow();
                }
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        VideoPlayerManager.instance().releaseVideoPlayer();
    }
}
