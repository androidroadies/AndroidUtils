package com.common.api.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OfferPrice implements Parcelable{

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("display_name")
    @Expose
    private String displayName;

    protected OfferPrice(Parcel in) {
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

    public static final Creator<OfferPrice> CREATOR = new Creator<OfferPrice>() {
        @Override
        public OfferPrice createFromParcel(Parcel in) {
            return new OfferPrice(in);
        }

        @Override
        public OfferPrice[] newArray(int size) {
            return new OfferPrice[size];
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