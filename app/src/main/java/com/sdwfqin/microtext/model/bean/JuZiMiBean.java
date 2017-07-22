package com.sdwfqin.microtext.model.bean;

import java.io.Serializable;

/**
 * Created by sdwfqin on 2016/7/23.
 */
public class JuZiMiBean implements Serializable {

    private String mTitle;
    private String mImageUrl;

    @Override
    public String toString() {
        return "JuZiMiBean{" +
                "mTitle='" + mTitle + '\'' +
                ", mImageUrl='" + mImageUrl + '\'' +
                '}';
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getIamgeUrl() {
        return mImageUrl;
    }

    public void setIamgeUrl(String iamgeUrl) {
        mImageUrl = iamgeUrl;
    }
}
