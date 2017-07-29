package com.common.api.responsemodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FileUploadResponse {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("chat_media")
    @Expose
    private String chatMedia;
    @SerializedName("chat_media_thumb")
    @Expose
    private String chatMediaThumb;

    /**
     * No args constructor for use in serialization
     *
     */
    public FileUploadResponse() {
    }

    /**
     *
     * @param message
     * @param chatMediaThumb
     * @param status
     * @param chatMedia
     */
    public FileUploadResponse(Integer status, String message, String chatMedia, String chatMediaThumb) {
        super();
        this.status = status;
        this.message = message;
        this.chatMedia = chatMedia;
        this.chatMediaThumb = chatMediaThumb;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getChatMedia() {
        return chatMedia;
    }

    public void setChatMedia(String chatMedia) {
        this.chatMedia = chatMedia;
    }

    public String getChatMediaThumb() {
        return chatMediaThumb;
    }

    public void setChatMediaThumb(String chatMediaThumb) {
        this.chatMediaThumb = chatMediaThumb;
    }

}

