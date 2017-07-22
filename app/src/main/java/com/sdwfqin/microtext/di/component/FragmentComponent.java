package com.sdwfqin.microtext.di.component;

import android.app.Activity;

import com.sdwfqin.microtext.di.module.FragmentModule;
import com.sdwfqin.microtext.di.scope.FragmentScope;
import com.sdwfqin.microtext.ui.essay.fragment.EssayMainFragment;
import com.sdwfqin.microtext.ui.essay.fragment.EssayFragment;
import com.sdwfqin.microtext.ui.juzimi.JuZiMiFragment;

import dagger.Component;

@FragmentScope
@Component(dependencies = AppComponent.class, modules = FragmentModule.class)
public interface FragmentComponent {

    Activity getActivity();

    void inject(EssayMainFragment homeFragment);

    void inject(EssayFragment essayFragment);

    void inject(JuZiMiFragment juZiMiFragment);
}
