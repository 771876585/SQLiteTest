package com.yezh.sqlite.sqlitetest.courselessonvideo;

/**
 * Created by yezh on 2018/10/12 14:30.
 */

public class VideoInfo {
    private String lessonId;//课时id
    private String lessonName;//课时名称
    private String orderNum;//课时序号
    private String totalTime;//课时总时长
    private String playSchedule;//课时已观看进度
    private String videoUrl;//课时播放地址

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getPlaySchedule() {
        return playSchedule;
    }

    public void setPlaySchedule(String playSchedule) {
        this.playSchedule = playSchedule;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
}
