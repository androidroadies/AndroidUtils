package com.common.api.commonmodel;

/**
 * Created by ln-138 on 3/4/17.
 */

public class Notification {
    private String id;
    private String title;
    private String subTitle;

    public Notification(String title, String subTitle) {
        this.title = title;
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }
}
