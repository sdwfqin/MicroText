package com.sdwfqin.microtext.di.component;

import com.sdwfqin.microtext.base.App;
import com.sdwfqin.microtext.di.module.AppModule;
import com.sdwfqin.microtext.di.module.HttpModule;
import com.sdwfqin.microtext.model.DataManager;
import com.sdwfqin.microtext.model.http.RetrofitHelper;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    App getContext();  // 提供App的Context

    DataManager getDataManager(); //数据中心

    RetrofitHelper retrofitHelper();  //提供http的帮助类
}
