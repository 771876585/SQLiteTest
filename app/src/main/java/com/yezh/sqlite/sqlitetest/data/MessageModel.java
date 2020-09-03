package com.yezh.sqlite.sqlitetest.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by yezh on 2020/9/3 11:10.
 * Description：
 */
public class MessageModel implements Parcelable {

    private String from;
    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.from);
        dest.writeString(this.to);
    }

    public MessageModel() {
    }

    protected MessageModel(Parcel in) {
        this.from = in.readString();
        this.to = in.readString();
    }

    public static final Parcelable.Creator<MessageModel> CREATOR = new Parcelable.Creator<MessageModel>() {
        @Override
        public MessageModel createFromParcel(Parcel source) {
            return new MessageModel(source);
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };
}
