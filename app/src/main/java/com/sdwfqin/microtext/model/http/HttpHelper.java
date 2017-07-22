package com.sdwfqin.microtext.model.http;

import io.reactivex.Flowable;

public interface HttpHelper {

    Flowable<String> fetchEssay(String page);

    Flowable<String> fetchEssayContent(String page);

    Flowable<String> fetchMeiTu(String page, int pageId);

}
