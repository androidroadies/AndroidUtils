package com.common.api.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mall implements Parcelable{

    @SerializedName("display_name")
    @Expose
    private String displayName;

    protected Mall(Parcel in) {
        displayName = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayName);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Mall> CREATOR = new Creator<Mall>() {
        @Override
        public Mall createFromParcel(Parcel in) {
            return new Mall(in);
        }

        @Override
        public Mall[] newArray(int size) {
            return new Mall[size];
        }
    };

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}