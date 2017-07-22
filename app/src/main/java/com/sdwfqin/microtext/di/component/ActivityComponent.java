package com.sdwfqin.microtext.di.component;

import android.app.Activity;

import com.sdwfqin.microtext.ui.essay.activity.EssayContentActvity;
import com.sdwfqin.microtext.ui.main.MainActivity;
import com.sdwfqin.microtext.di.module.ActivityModule;
import com.sdwfqin.microtext.di.scope.ActivityScope;
import com.sdwfqin.microtext.ui.main.SplashActivity;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();

    void inject(SplashActivity splashActivity);
    void inject(MainActivity mainActivity);
    void inject(EssayContentActvity essayContentActvity);
}
