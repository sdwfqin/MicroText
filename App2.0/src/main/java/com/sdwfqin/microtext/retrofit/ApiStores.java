package com.sdwfqin.microtext.retrofit;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiStores {
    //baseUrl
    String API_SERVER_URL = "http://www.lookmw.cn";

    //原创
    @GET("{homePage}{pageId}.html")
    Observable<String> loadYc(@Path("homePage") String homePage ,@Path("pageId") String pageId);
}
