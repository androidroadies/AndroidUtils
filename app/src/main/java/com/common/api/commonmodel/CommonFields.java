package com.common.api.commonmodel;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommonFields implements Parcelable {

    @SerializedName("mall")
    @Expose
    private Mall mall;
    @SerializedName("store")
    @Expose
    private Store store;
    @SerializedName("quantity")
    @Expose
    private Quantity quantity;
    @SerializedName("actual_price")
    @Expose
    private ActualPrice actualPrice;
    @SerializedName("offer_price")
    @Expose
    private OfferPrice offerPrice;

    protected CommonFields(Parcel in) {
        mall = in.readParcelable(Mall.class.getClassLoader());
        store = in.readParcelable(Store.class.getClassLoader());
        quantity = in.readParcelable(Quantity.class.getClassLoader());
        actualPrice = in.readParcelable(ActualPrice.class.getClassLoader());
        offerPrice = in.readParcelable(OfferPrice.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mall, flags);
        dest.writeParcelable(store, flags);
        dest.writeParcelable(quantity, flags);
        dest.writeParcelable(actualPrice, flags);
        dest.writeParcelable(offerPrice, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CommonFields> CREATOR = new Creator<CommonFields>() {
        @Override
        public CommonFields createFromParcel(Parcel in) {
            return new CommonFields(in);
        }

        @Override
        public CommonFields[] newArray(int size) {
            return new CommonFields[size];
        }
    };

    public Mall getMall() {
        return mall;
    }

    public void setMall(Mall mall) {
        this.mall = mall;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    public ActualPrice getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(ActualPrice actualPrice) {
        this.actualPrice = actualPrice;
    }

    public OfferPrice getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(OfferPrice offerPrice) {
        this.offerPrice = offerPrice;
    }
}