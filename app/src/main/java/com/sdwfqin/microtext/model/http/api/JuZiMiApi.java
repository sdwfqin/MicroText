package com.sdwfqin.microtext.model.http.api;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by sdwfqin on 2017/7/22.
 */

public interface JuZiMiApi {

    String HOST = "http://www.juzimi.com/";

    @GET("meitumeiju/{page}")
    @Headers("Content-Type: application/x-www-form-urlencoded;charset=GBK")
    Flowable<String> getMeiTu(@Path("page") String page, @Query("page") int pageId);
}
