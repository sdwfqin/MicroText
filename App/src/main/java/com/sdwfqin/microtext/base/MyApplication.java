package com.sdwfqin.microtext.base;

import android.app.Application;

import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;

/**
 * Created by sdwfqin on 2016/7/22.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // 消息推送
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
        //开启推送
        mPushAgent.enable();
    }
}
