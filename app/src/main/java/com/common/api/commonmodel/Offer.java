package com.common.api.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ln-138 on 6/4/17.
 */

public class Offer implements Parcelable {

    private int redId;
    private String title;
    private boolean isWatchList;
    private boolean isFavourite;
    private int type;

    public Offer(int redId) {
        this.redId = redId;
    }

    public Offer(int redId, String title) {
        this.redId = redId;
        this.title = title;
    }

    public Offer(int redId, String title, int type) {
        this.redId = redId;
        this.title = title;
        this.type = type;
    }

    public Offer(int redId, String title, boolean isWatchList, int type) {
        this.redId = redId;
        this.title = title;
        this.isWatchList = isWatchList;
        this.type = type;
    }

    public Offer(int redId, String title, boolean isWatchList, boolean isFavourite, int type) {
        this.redId = redId;
        this.title = title;
        this.isWatchList = isWatchList;
        this.isFavourite = isFavourite;
        this.type = type;
    }

    protected Offer(Parcel in) {
        redId = in.readInt();
        title = in.readString();
        isWatchList = in.readByte() != 0;
        isFavourite = in.readByte() != 0;
        type = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(redId);
        dest.writeString(title);
        dest.writeByte((byte) (isWatchList ? 1 : 0));
        dest.writeByte((byte) (isFavourite ? 1 : 0));
        dest.writeInt(type);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    public int getRedId() {
        return redId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public boolean isWatchList() {
        return isWatchList;
    }

    public void setWatchList(boolean watchList) {
        isWatchList = watchList;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }
}
