package com.sdwfqin.microtext.base;

import android.app.Application;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;

/**
 * Created by sdwfqin on 2016/7/22.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 用户反馈
        FeedbackAPI.initAnnoy(this, "23416375");
    }
}
