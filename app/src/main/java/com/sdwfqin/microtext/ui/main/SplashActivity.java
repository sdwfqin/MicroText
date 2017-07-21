package com.sdwfqin.microtext.ui.main;

import android.content.Intent;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseActivity;
import com.sdwfqin.microtext.contract.SplashContract;
import com.sdwfqin.microtext.presenter.SplashPresenter;

/**
 * 启动页
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initEventAndData() {
        mPresenter.initData();
    }

    @Override
    public void enterHome() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
