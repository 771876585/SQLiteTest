package com.yezh.sqlite.sqlitetest.myvideoplayer.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yezh.sqlite.sqlitetest.R;
import com.yezh.sqlite.sqlitetest.myvideoplayer.NiceVideoPlayer;
import com.yezh.sqlite.sqlitetest.myvideoplayer.NiceVideoPlayerManager;
import com.yezh.sqlite.sqlitetest.myvideoplayer.TxVideoPlayerController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yezh on 2018/10/11 17:49.
 */

public class FullWindowActivity extends AppCompatActivity {
    private NiceVideoPlayer mNiceVideoPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_window_play);
        init();
    }

    private void init() {
        mNiceVideoPlayer = (NiceVideoPlayer) findViewById(R.id.nice_video_player);
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_NATIVE); // IjkPlayer or MediaPlayer
        String videoUrl = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
//        videoUrl = Environment.getExternalStorageDirectory().getPath().concat("/办公室小野.mp4");
        List<String> catalogueList = new ArrayList<>();
        catalogueList.add("目录1");
        catalogueList.add("目录2");
        catalogueList.add("目录3");
        catalogueList.add("目录4");
        catalogueList.add("目录5");
        catalogueList.add("目录6");
        catalogueList.add("目录7");

        mNiceVideoPlayer.setUp(videoUrl, null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("办公室小野开番外了，居然在办公室开澡堂！老板还点赞？");
        controller.setLenght(98000);
        controller.setCatalogueList(catalogueList);
//        Glide.with(this)
//                .load("http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-30-43.jpg")
//                .placeholder(R.drawable.img_default)
//                .crossFade()
//                .into(controller.imageView());
        mNiceVideoPlayer.setController(controller);
        mNiceVideoPlayer.enterFullScreen();
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

//    @Override
//    public void onBackPressed() {
//        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
//        super.onBackPressed();
//    }
}
