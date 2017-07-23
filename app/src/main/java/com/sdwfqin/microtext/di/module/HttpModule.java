package com.sdwfqin.microtext.di.module;

import com.blankj.utilcode.util.NetworkUtils;
import com.sdwfqin.microtext.BuildConfig;
import com.sdwfqin.microtext.base.Constants;
import com.sdwfqin.microtext.di.qualifier.EssayUrl;
import com.sdwfqin.microtext.di.qualifier.JuZiMiUrl;
import com.sdwfqin.microtext.model.http.api.EssayApi;
import com.sdwfqin.microtext.model.http.api.JuZiMiApi;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

@Module
public class HttpModule {

    @Singleton
    @Provides
    Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }


    @Singleton
    @Provides
    OkHttpClient.Builder provideOkHttpBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    @EssayUrl
    Retrofit provideEssayRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, EssayApi.HOST);
    }

    @Singleton
    @Provides
    @JuZiMiUrl
    Retrofit provideJuZiMiRetrofit(Retrofit.Builder builder, OkHttpClient client) {
        return createRetrofit(builder, client, JuZiMiApi.HOST);
    }

    @Singleton
    @Provides
    EssayApi provideEssayService(@EssayUrl Retrofit retrofit) {
        return retrofit.create(EssayApi.class);
    }

    @Singleton
    @Provides
    JuZiMiApi provideJuZiMiService(@JuZiMiUrl Retrofit retrofit) {
        return retrofit.create(JuZiMiApi.class);
    }

    @Singleton
    @Provides
    OkHttpClient provideClient(OkHttpClient.Builder builder) {
        if (BuildConfig.DEBUG) {
            // OkHttp日志拦截器
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            /**
             *  共包含四个级别：NONE、BASIC、HEADER、BODYNONE
             *  不记录BASIC
             *  请求/响应行HEADER
             *  请求/响应行 + 头
             *  BODY 请求/响应行 + 头 + 体
             */
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        File cacheFile = new File(Constants.PATH_CACHE);
        // 最大50MB
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // 判断网络条件，如果有网络的话就直接获取网络上面的数据，如果没有网络就去缓存里面取数据
                if (!NetworkUtils.isAvailableByPing()) {
                    request = request.newBuilder()
                            // 设置缓存策略
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetworkUtils.isAvailableByPing()) {
                    // 有网络时, 不缓存, 最大保存时长为0
                    int maxAge = 0;
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            // 清除头信息，因为服务器如果不支持，会返回一些干扰信息
                            .removeHeader("Pragma")
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 28;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            // 清除头信息，因为服务器如果不支持，会返回一些干扰信息
                            .removeHeader("Pragma")
                            .build();
                }
                return response;
            }
        };

        // 网络拦截器
        builder.addNetworkInterceptor(cacheInterceptor);
        // 应用拦截器
        builder.addInterceptor(cacheInterceptor);
        // 设置文件缓存
        builder.cache(cache);
        // 连接超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        // 读取超时
        builder.readTimeout(20, TimeUnit.SECONDS);
        // 写入超时
        builder.writeTimeout(20, TimeUnit.SECONDS);
        // 错误重连
        builder.retryOnConnectionFailure(true);
        return builder.build();
    }

    private Retrofit createRetrofit(Retrofit.Builder builder, OkHttpClient client, String url) {
        return builder
                .baseUrl(url)
                // 设置OkHttpclient
                .client(client)
                // RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                // 字符串
                .addConverterFactory(ScalarsConverterFactory.create())
                // Gson
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
