package com.sdwfqin.microtext.model;

import com.sdwfqin.microtext.model.http.HttpHelper;

import io.reactivex.Flowable;

public class DataManager implements HttpHelper {

    HttpHelper mHttpHelper;

    public DataManager(HttpHelper httpHelper) {
        mHttpHelper = httpHelper;
    }

    @Override
    public Flowable<String> fetchEssay(String page) {
        return mHttpHelper.fetchEssay(page);
    }

    @Override
    public Flowable<String> fetchEssayContent(String page) {
        return mHttpHelper.fetchEssayContent(page);
    }

    @Override
    public Flowable<String> fetchMeiTu(String page, int pageId) {
        return mHttpHelper.fetchMeiTu(page, pageId);
    }
}
