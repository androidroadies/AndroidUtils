package com.common.api.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActualPrice implements Parcelable{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("display_name")
    @Expose
    private String displayName;

    protected ActualPrice(Parcel in) {
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

    public static final Creator<ActualPrice> CREATOR = new Creator<ActualPrice>() {
        @Override
        public ActualPrice createFromParcel(Parcel in) {
            return new ActualPrice(in);
        }

        @Override
        public ActualPrice[] newArray(int size) {
            return new ActualPrice[size];
        }
    };

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

}