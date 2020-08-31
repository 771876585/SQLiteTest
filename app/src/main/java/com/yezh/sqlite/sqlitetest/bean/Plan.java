package com.yezh.sqlite.sqlitetest.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yezh on 2020/8/31 14:47.
 * Description：
 */
public class Plan {
    private String time;
    private String content;
    private List<String> actionList = new ArrayList<>();

    public Plan(String time, String content) {
        this.time = time;
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<String> getActionList() {
        return actionList;
    }

    public void setActionList(List<String> actionList) {
        this.actionList = actionList;
    }
}
