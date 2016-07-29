// (c)2016 Flipboard Inc, All Rights Reserved.

package com.myexercuse.myexercise.data.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class GankEntity implements Parcelable {

     public long id;
     public String url;
     public String type;
     public String desc;
     public String who;
     public boolean used;
     public Date createdAt;
     public Date updatedAt;
     public Date publishedAt;

     @Override
     public int describeContents() {
          return 0;
     }

     @Override
     public void writeToParcel(Parcel dest, int flags) {
          dest.writeLong(this.id);
          dest.writeString(this.url);
          dest.writeString(this.type);
          dest.writeString(this.desc);
          dest.writeString(this.who);
          dest.writeByte(this.used ? (byte) 1 : (byte) 0);
          dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
          dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
          dest.writeLong(this.publishedAt != null ? this.publishedAt.getTime() : -1);
     }

     public GankEntity() {
     }

     protected GankEntity(Parcel in) {
          this.id = in.readLong();
          this.url = in.readString();
          this.type = in.readString();
          this.desc = in.readString();
          this.who = in.readString();
          this.used = in.readByte() != 0;
          long tmpCreatedAt = in.readLong();
          this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
          long tmpUpdatedAt = in.readLong();
          this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
          long tmpPublishedAt = in.readLong();
          this.publishedAt = tmpPublishedAt == -1 ? null : new Date(tmpPublishedAt);
     }

     public static final Parcelable.Creator<GankEntity> CREATOR = new Parcelable.Creator<GankEntity>() {
          @Override
          public GankEntity createFromParcel(Parcel source) {
               return new GankEntity(source);
          }

          @Override
          public GankEntity[] newArray(int size) {
               return new GankEntity[size];
          }
     };
}
