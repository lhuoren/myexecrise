package com.myexercuse.myexercise.data;

import android.os.Parcel;
import android.os.Parcelable;

public class NewsData implements Parcelable {

    /**
     * docid
     */
    public String docid;
    /**
     * 标题
     */
    public String title;
    /**
     * 小内容
     */
    public String digest;
    /**
     * 图片地址
     */
    public String imgsrc;
    /**
     * 来源
     */
    public String source;
    /**
     * 时间
     */
    public String ptime;
    /**
     * TAG
     */
    public String tag;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.docid);
        dest.writeString(this.title);
        dest.writeString(this.digest);
        dest.writeString(this.imgsrc);
        dest.writeString(this.source);
        dest.writeString(this.ptime);
        dest.writeString(this.tag);
    }

    public NewsData() {
    }

    protected NewsData(Parcel in) {
        this.docid = in.readString();
        this.title = in.readString();
        this.digest = in.readString();
        this.imgsrc = in.readString();
        this.source = in.readString();
        this.ptime = in.readString();
        this.tag = in.readString();
    }

    public static final Parcelable.Creator<NewsData> CREATOR = new Parcelable.Creator<NewsData>() {
        @Override
        public NewsData createFromParcel(Parcel source) {
            return new NewsData(source);
        }

        @Override
        public NewsData[] newArray(int size) {
            return new NewsData[size];
        }
    };
}
