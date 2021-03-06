package com.sdwfqin.microtext.base;

import android.app.Application;

import com.blankj.utilcode.util.Utils;
import com.sdwfqin.microtext.di.component.AppComponent;
import com.sdwfqin.microtext.di.component.DaggerAppComponent;
import com.sdwfqin.microtext.di.module.AppModule;
import com.sdwfqin.microtext.di.module.HttpModule;

/**
 * Created by sdwfqin on 2017/6/9.
 */
public class App extends Application {

    private static AppComponent appComponent;
    private static App instance;

    public static synchronized App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();

        // 腾讯Bugly，已改为在MainActivity中初始化，原因：避免在Splash页面弹出应用更新提示窗
        // true表示打开debug模式，false表示关闭调试模式
        // Bugly.init(getApplicationContext(), "53e067220d", false);
        // 初始化工具类
        Utils.init(this);
    }

    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }
}
