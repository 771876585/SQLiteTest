package com.yezh.sqlite.sqlitetest.courselessonvideo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import com.yezh.sqlite.sqlitetest.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 播放器控制器实现.
 */
public class LessonVideoPlayerController
        extends ALessonVideoPlayerController
        implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener{

    private Context mContext;

    private LinearLayout mTop;
    private ImageView mBack;
    private TextView mTitle;
    private LinearLayout mBatteryTime;
    private ImageView mBattery;
    private TextView mTime;

    private LinearLayout mBottom;
    private ImageView mRestartPause;
    private TextView mPosition;
    private TextView mDuration;
    private SeekBar mSeek;
    private TextView catalogue;

    private LinearLayout ll_catalogue;
    private ListView myListView;

    private LinearLayout mLoading;
    private TextView mLoadText;

    private LinearLayout mChangePositon;
    private TextView mChangePositionCurrent;
    private ProgressBar mChangePositionProgress;

    private LinearLayout mChangeBrightness;
    private ProgressBar mChangeBrightnessProgress;

    private LinearLayout mChangeVolume;
    private ProgressBar mChangeVolumeProgress;

    private LinearLayout mError;
    private TextView mRetry;

    private LinearLayout mCompleted;
    private TextView mReplay;

    private boolean topBottomVisible;
    private CountDownTimer mDismissTopBottomCountDownTimer;

    private boolean hasRegisterBatteryReceiver; // 是否已经注册了电池广播

    public LessonVideoPlayerController(Context context) {
        super(context);
        mContext = context;
        init();
    }

    private void init() {
        LayoutInflater.from(mContext).inflate(R.layout.lesson_video_palyer_controller, this, true);


        mTop = (LinearLayout) findViewById(R.id.top);
        mBack = (ImageView) findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.title);
        mBatteryTime = (LinearLayout) findViewById(R.id.battery_time);
        mBattery = (ImageView) findViewById(R.id.battery);
        mTime = (TextView) findViewById(R.id.time);

        mBottom = (LinearLayout) findViewById(R.id.bottom);
        mRestartPause = (ImageView) findViewById(R.id.restart_or_pause);
        mPosition = (TextView) findViewById(R.id.position);
        mDuration = (TextView) findViewById(R.id.duration);
        mSeek = (SeekBar) findViewById(R.id.seek);
        catalogue = (TextView)findViewById(R.id.catalogue);

        ll_catalogue = (LinearLayout)findViewById(R.id.ll_catalogue);
        myListView = (ListView)findViewById(R.id.myListView);

        mLoading = (LinearLayout) findViewById(R.id.loading);
        mLoadText = (TextView) findViewById(R.id.load_text);

        mChangePositon = (LinearLayout) findViewById(R.id.change_position);
        mChangePositionCurrent = (TextView) findViewById(R.id.change_position_current);
        mChangePositionProgress = (ProgressBar) findViewById(R.id.change_position_progress);

        mChangeBrightness = (LinearLayout) findViewById(R.id.change_brightness);
        mChangeBrightnessProgress = (ProgressBar) findViewById(R.id.change_brightness_progress);

        mChangeVolume = (LinearLayout) findViewById(R.id.change_volume);
        mChangeVolumeProgress = (ProgressBar) findViewById(R.id.change_volume_progress);

        mError = (LinearLayout) findViewById(R.id.error);
        mRetry = (TextView) findViewById(R.id.retry);

        mCompleted = (LinearLayout) findViewById(R.id.completed);
        mReplay = (TextView) findViewById(R.id.replay);

        mBack.setOnClickListener(this);
        mRestartPause.setOnClickListener(this);
        mRetry.setOnClickListener(this);
        mReplay.setOnClickListener(this);
        mSeek.setOnSeekBarChangeListener(this);
        catalogue.setOnClickListener(this);
        ll_catalogue.setOnClickListener(this);
        this.setOnClickListener(this);
    }

    @Override
    public void setTitle(String title) {
        mTitle.setText(title);
    }

    @Override
    public void setCatalogueList(List<VideoInfo> catalogueList) {
        CatalogueAdapter mAdapter = new CatalogueAdapter(mContext, catalogueList);
        myListView.setAdapter(mAdapter);

    }

    @Override
    public void setLessonVideoPlayer(ILessonVideoPlayer mVideoPlayer) {
        super.setLessonVideoPlayer(mVideoPlayer);
        // 给播放器配置视频链接地址
//        mVideoPlayer.setUp(clarities.get(defaultClarityIndex).videoUrl, null);
    }

    @Override
    protected void onPlayStateChanged(int playState) {
        switch (playState) {
            case LessonVideoPlayer.STATE_PREPARING:
                mLoading.setVisibility(View.VISIBLE);
                mLoadText.setText("");
                mError.setVisibility(View.GONE);
                mCompleted.setVisibility(View.GONE);
                mTop.setVisibility(VISIBLE);
                mBottom.setVisibility(VISIBLE);
                break;
            case LessonVideoPlayer.STATE_PREPARED:
                startUpdateProgressTimer();
                mTop.setVisibility(VISIBLE);
                mBottom.setVisibility(VISIBLE);
                break;
            case LessonVideoPlayer.STATE_PLAYING:
                mLoading.setVisibility(View.GONE);
                mRestartPause.setImageResource(R.drawable.ic_player_pause);
                startDismissTopBottomTimer();
                break;
            case LessonVideoPlayer.STATE_PAUSED:
                mLoading.setVisibility(View.GONE);
                mRestartPause.setImageResource(R.drawable.ic_player_start);
                cancelDismissTopBottomTimer();
                break;
            case LessonVideoPlayer.STATE_BUFFERING_PLAYING:
                mLoading.setVisibility(View.VISIBLE);
                mRestartPause.setImageResource(R.drawable.ic_player_pause);
                mLoadText.setText("");
                startDismissTopBottomTimer();
                break;
            case LessonVideoPlayer.STATE_BUFFERING_PAUSED:
                mLoading.setVisibility(View.VISIBLE);
                mRestartPause.setImageResource(R.drawable.ic_player_start);
                mLoadText.setText("");
                cancelDismissTopBottomTimer();
                break;
            case LessonVideoPlayer.STATE_ERROR:
                cancelUpdateProgressTimer();
                setTopBottomVisible(false);
                mTop.setVisibility(View.VISIBLE);
                mError.setVisibility(View.VISIBLE);
                break;
            case LessonVideoPlayer.STATE_COMPLETED:
                cancelUpdateProgressTimer();
                setTopBottomVisible(false);
                mCompleted.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    protected void onPlayModeChanged() {
        mBack.setVisibility(View.VISIBLE);
        catalogue.setVisibility(VISIBLE);
        mBatteryTime.setVisibility(View.VISIBLE);
        if (!hasRegisterBatteryReceiver) {
            mContext.registerReceiver(mBatterReceiver,
                    new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            hasRegisterBatteryReceiver = true;
        }
        //切换全屏之后，开始加载播放
        mVideoPlayer.start();
    }

    /**
     * 电池状态即电量变化广播接收器
     */
    private BroadcastReceiver mBatterReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,
                    BatteryManager.BATTERY_STATUS_UNKNOWN);
            if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                // 充电中
                mBattery.setImageResource(R.drawable.battery_charging);
            } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
                // 充电完成
                mBattery.setImageResource(R.drawable.battery_full);
            } else {
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
                int percentage = (int) (((float) level / scale) * 100);
                if (percentage <= 10) {
                    mBattery.setImageResource(R.drawable.battery_10);
                } else if (percentage <= 20) {
                    mBattery.setImageResource(R.drawable.battery_20);
                } else if (percentage <= 50) {
                    mBattery.setImageResource(R.drawable.battery_50);
                } else if (percentage <= 80) {
                    mBattery.setImageResource(R.drawable.battery_80);
                } else if (percentage <= 100) {
                    mBattery.setImageResource(R.drawable.battery_100);
                }
            }
        }
    };

    @Override
    protected void reset() {
        topBottomVisible = false;
        cancelUpdateProgressTimer();
        cancelDismissTopBottomTimer();
        mSeek.setProgress(0);
        mSeek.setSecondaryProgress(0);

        mBottom.setVisibility(View.GONE);

        mTop.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.GONE);

        mLoading.setVisibility(View.GONE);
        mError.setVisibility(View.GONE);
        mCompleted.setVisibility(View.GONE);
    }

    @Override
    protected void unregisterReceiver() {
        //关闭广播
        if (hasRegisterBatteryReceiver) {
            mContext.unregisterReceiver(mBatterReceiver);
            hasRegisterBatteryReceiver = false;
        }
    }

    /**
     * 尽量不要在onClick中直接处理控件的隐藏、显示及各种UI逻辑。
     * UI相关的逻辑都尽量到{@link #onPlayStateChanged}和{@link #onPlayModeChanged}中处理.
     */
    @Override
    public void onClick(View v) {
        if (v == mBack) {
            //需要关闭播放器，防止内存溢出
            LessonVideoPlayerManager.instance().releaseVideoPlayer();
            ((Activity)mContext).finish();
        } else if (v == mRestartPause) {
            if (mVideoPlayer.isPlaying() || mVideoPlayer.isBufferingPlaying()) {
                mVideoPlayer.pause();
            } else if (mVideoPlayer.isPaused() || mVideoPlayer.isBufferingPaused()) {
                mVideoPlayer.restart();
            }
        } else if (v == mRetry) {
            mVideoPlayer.restart();
        } else if (v == mReplay) {
            mRetry.performClick();
        } else if(v == catalogue){
            ll_catalogue.setVisibility(VISIBLE);
        } else if(v == ll_catalogue){
            ll_catalogue.setVisibility(GONE);
        }
        else if (v == this) {
            //单击的时候显示标题
            //双击暂停可以在这里处理
            if(isFastdoubleClick()){
                if(mVideoPlayer.isPaused()){
                    mVideoPlayer.restart();
                    setTopBottomVisible(false);
                }else{
                    mVideoPlayer.pause();
                    setTopBottomVisible(true);
                }
            }else{
                if (mVideoPlayer.isPlaying()
                        || mVideoPlayer.isPaused()
                        || mVideoPlayer.isBufferingPlaying()
                        || mVideoPlayer.isBufferingPaused()) {
                    setTopBottomVisible(!topBottomVisible);
                }
            }

        }
    }

    //判断是否双击
    private long lastclickTime;
    private boolean isFastdoubleClick(){
        long time = System.currentTimeMillis();
        long timed = time - lastclickTime;
        if(0 < timed && timed < 500){
            return true;
        }
        lastclickTime = time;
        return false;
    }

    /**
     * 设置top、bottom的显示和隐藏
     *
     * @param visible true显示，false隐藏.
     */
    private void setTopBottomVisible(boolean visible) {
        mTop.setVisibility(visible ? View.VISIBLE : View.GONE);
        mBottom.setVisibility(visible ? View.VISIBLE : View.GONE);
        topBottomVisible = visible;
        if (visible) {
            if (!mVideoPlayer.isPaused() && !mVideoPlayer.isBufferingPaused()) {
                startDismissTopBottomTimer();
            }
        } else {
            cancelDismissTopBottomTimer();
        }
    }

    /**
     * 开启top、bottom自动消失的timer
     */
    private void startDismissTopBottomTimer() {
        cancelDismissTopBottomTimer();
        if (mDismissTopBottomCountDownTimer == null) {
            mDismissTopBottomCountDownTimer = new CountDownTimer(3000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    setTopBottomVisible(false);
                }
            };
        }
        mDismissTopBottomCountDownTimer.start();
    }

    /**
     * 取消top、bottom自动消失的timer
     */
    private void cancelDismissTopBottomTimer() {
        if (mDismissTopBottomCountDownTimer != null) {
            mDismissTopBottomCountDownTimer.cancel();
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if (mVideoPlayer.isBufferingPaused() || mVideoPlayer.isPaused()) {
            mVideoPlayer.restart();
        }
        long position = (long) (mVideoPlayer.getDuration() * seekBar.getProgress() / 100f);
        mVideoPlayer.seekTo(position);
        startDismissTopBottomTimer();
    }

    @Override
    protected void updateProgress() {
        long position = mVideoPlayer.getCurrentPosition();
        long duration = mVideoPlayer.getDuration();
        int bufferPercentage = mVideoPlayer.getBufferPercentage();
        mSeek.setSecondaryProgress(bufferPercentage);
        int progress = (int) (100f * position / duration);
        mSeek.setProgress(progress);
        mPosition.setText(LessonUtil.formatTime(position));
        mDuration.setText(LessonUtil.formatTime(duration));
        // 更新时间
        mTime.setText(new SimpleDateFormat("HH:mm", Locale.CHINA).format(new Date()));
    }

    @Override
    protected void showChangePosition(long duration, int newPositionProgress) {
        mChangePositon.setVisibility(View.VISIBLE);
        long newPosition = (long) (duration * newPositionProgress / 100f);
        mChangePositionCurrent.setText(LessonUtil.formatTime(newPosition));
        mChangePositionProgress.setProgress(newPositionProgress);
        mSeek.setProgress(newPositionProgress);
        mPosition.setText(LessonUtil.formatTime(newPosition));
    }

    @Override
    protected void hideChangePosition() {
        mChangePositon.setVisibility(View.GONE);
    }

    @Override
    protected void showChangeVolume(int newVolumeProgress) {
        mChangeVolume.setVisibility(View.VISIBLE);
        mChangeVolumeProgress.setProgress(newVolumeProgress);
    }

    @Override
    protected void hideChangeVolume() {
        mChangeVolume.setVisibility(View.GONE);
    }

    @Override
    protected void showChangeBrightness(int newBrightnessProgress) {
        mChangeBrightness.setVisibility(View.VISIBLE);
        mChangeBrightnessProgress.setProgress(newBrightnessProgress);
    }

    @Override
    protected void hideChangeBrightness() {
        mChangeBrightness.setVisibility(View.GONE);
    }
}
