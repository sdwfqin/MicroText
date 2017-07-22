package com.sdwfqin.microtext.model.http.api;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sdwfqin on 2017/6/9.
 */

public interface EssayApi {

    String HOST = "http://www.lookmw.cn/";

    @GET("{page}")
    Flowable<String> getEssay(@Path("page") String page);

    @GET("{page}")
    Flowable<String> getEssayContent(@Path("page") String page);
}
