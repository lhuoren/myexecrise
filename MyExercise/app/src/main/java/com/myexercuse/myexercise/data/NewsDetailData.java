package com.myexercuse.myexercise.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Description : 新闻详情实体类
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/19
 */
public class NewsDetailData implements Parcelable {
    /**
     * docid
     */
    public String docid;
    /**
     * title
     */
    public String title;
    /**
     * source
     */
    public String source;
    /**
     * body
     */
    public String body;
    /**
     * ptime
     */
    public String ptime;
    /**
     * cover
     */
    public String cover;
    /**
     * 图片列表
     */
    public List<String> imgList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.docid);
        dest.writeString(this.title);
        dest.writeString(this.source);
        dest.writeString(this.body);
        dest.writeString(this.ptime);
        dest.writeString(this.cover);
        dest.writeStringList(this.imgList);
    }

    public NewsDetailData() {
    }

    protected NewsDetailData(Parcel in) {
        this.docid = in.readString();
        this.title = in.readString();
        this.source = in.readString();
        this.body = in.readString();
        this.ptime = in.readString();
        this.cover = in.readString();
        this.imgList = in.createStringArrayList();
    }

    public static final Parcelable.Creator<NewsDetailData> CREATOR = new Parcelable.Creator<NewsDetailData>() {
        @Override
        public NewsDetailData createFromParcel(Parcel source) {
            return new NewsDetailData(source);
        }

        @Override
        public NewsDetailData[] newArray(int size) {
            return new NewsDetailData[size];
        }
    };
}
