package com.common.api.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CustomFilter {

    @SerializedName("f_id")
    @Expose
    private String fId;
    @SerializedName("fvalue")
    @Expose
    private String fvalue;

    /**
     * No args constructor for use in serialization
     */
    public CustomFilter() {
    }

    /**
     * @param fId
     * @param fvalue
     */
    public CustomFilter(String fId, String fvalue) {
        super();
        this.fId = fId;
        this.fvalue = fvalue;
    }

    public String getFId() {
        return fId;
    }

    public void setFId(String fId) {
        this.fId = fId;
    }

    public String getFvalue() {
        return fvalue;
    }

    public void setFvalue(String fvalue) {
        this.fvalue = fvalue;
    }

}