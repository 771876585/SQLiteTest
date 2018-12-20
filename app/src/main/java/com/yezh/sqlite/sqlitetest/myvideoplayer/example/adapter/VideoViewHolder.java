package com.yezh.sqlite.sqlitetest.myvideoplayer.example.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.yezh.sqlite.sqlitetest.R;
import com.yezh.sqlite.sqlitetest.myvideoplayer.NiceVideoPlayer;
import com.yezh.sqlite.sqlitetest.myvideoplayer.TxVideoPlayerController;
import com.yezh.sqlite.sqlitetest.myvideoplayer.example.bean.Video;


/**
 * Created by XiaoJianjun on 2017/5/21.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {

    public TxVideoPlayerController mController;
    public NiceVideoPlayer mVideoPlayer;

    public VideoViewHolder(View itemView) {
        super(itemView);
        mVideoPlayer = (NiceVideoPlayer) itemView.findViewById(R.id.nice_video_player);
        // 将列表中的每个视频设置为默认16:9的比例
        ViewGroup.LayoutParams params = mVideoPlayer.getLayoutParams();
        params.width = itemView.getResources().getDisplayMetrics().widthPixels; // 宽度为屏幕宽度
        params.height = (int) (params.width * 9f / 16f);    // 高度为宽度的9/16
        mVideoPlayer.setLayoutParams(params);
    }

    public void setController(TxVideoPlayerController controller) {
        mController = controller;
        mVideoPlayer.setController(mController);
    }

    public void bindData(Video video) {
        mController.setTitle(video.getTitle());
        mController.setLenght(video.getLength());
//        Glide.with(itemView.getContext())
//                .load(video.getImageUrl())
//                .placeholder(R.drawable.img_default)
//                .crossFade()
//                .into(mController.imageView());
        mVideoPlayer.setUp(video.getVideoUrl(), null);
    }
}