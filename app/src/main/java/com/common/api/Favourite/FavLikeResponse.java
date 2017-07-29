package com.common.api.Favourite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavLikeResponse {

    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("total_like")
    @Expose
    private int totalLike;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("dev_message")
    @Expose
    private String devMessage;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDevMessage() {
        return devMessage;
    }

    public void setDevMessage(String devMessage) {
        this.devMessage = devMessage;
    }

    public int getTotalLike() {
        return totalLike;
    }

    public void setTotalLike(int totalLike) {
        this.totalLike = totalLike;
    }
}