package com.common.api.commonmodel;

/**
 * Created by ln-404 on 6/7/17.
 */

public class InfoPagerItem {
    private String title;
    private String description;
    private int imageResId;

    public InfoPagerItem(String title, String description, int imageResId) {
        this.title = title;
        this.description = description;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResId() {
        return imageResId;
    }
}
