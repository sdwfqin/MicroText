package com.sdwfqin.microtext.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by sdwfqin on 2016/7/23.
 */
public class AboutMeActivity extends BaseActivity {

    @InjectView(R.id.toolbar_return_text)
    ImageView mToolbarReturnText;
    @InjectView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_me);
        ButterKnife.inject(this);

        mToolbarReturnText.setVisibility(View.VISIBLE);
        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText("作者简介");

    }

    @OnClick(R.id.toolbar_return_text)
    public void onClick() {
        finish();
    }
}
