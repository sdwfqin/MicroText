package com.sdwfqin.microtext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.ui.base.BaseActivity;

/**
 * Created by sdwfqin on 2016/7/21.
 */
public class SplashActivity extends BaseActivity {


    private static final long DELAY = 3000;
    private static final int CODE_ENTER_HOME = 1;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CODE_ENTER_HOME:
                    enterHome();
                    break;

                default:
                    break;
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 全屏，去掉通知栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 隐藏ActionBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_splash);

        new Thread(new Runnable() {
            public void run() {

                // 获取开始执行时间
                final long startTime = System.currentTimeMillis();
                // 创建消息对象
                Message msg = new Message();

                long nowTime = System.currentTimeMillis();
                long spendTime = nowTime - startTime;
                if (spendTime < DELAY) {
                    try {
                        Thread.sleep(DELAY - spendTime);
                    } catch (InterruptedException e) {
                        // 线程中断
                        e.printStackTrace();
                    }
                }
                msg.what = CODE_ENTER_HOME;
                // 发送数据到主线程
                handler.sendMessage(msg);

            }
        }).start();
    }

    // 跳转到主页
    public void enterHome() {
        startActivity(new Intent(this, MainActivity.class));

        finish();
    }
}
