package com.sdwfqin.microtext.entity;

/**
 * Created by sdwfqin on 2016/7/20.
 */
public class HomeItem {

    private String mTitle;
    private String mContent;
    private String mInfo;
    private String mUrl;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getInfo() {
        return mInfo;
    }

    public void setInfo(String info) {
        mInfo = info;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return "HomeItem{" +
                "mTitle='" + mTitle + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mInfo='" + mInfo + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}
