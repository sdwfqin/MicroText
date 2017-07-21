package com.sdwfqin.microtext.model.http;

import io.reactivex.Flowable;

public interface HttpHelper {

    Flowable<String> fetchEssay(String page);

}
