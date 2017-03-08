package com.sdwfqin.microtext.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.view.SwipeBackActivity;

import butterknife.ButterKnife;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by sdwfqin on 2016/7/23.
 */
public class AboutProjectActivity extends SwipeBackActivity {

    @BindView(R.id.toolbar_return_text)
    ImageView mToolbarReturnText;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about_project);
        ButterKnife.bind(this);

        mToolbarReturnText.setVisibility(View.VISIBLE);
        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText("项目介绍");

    }

    @OnClick(R.id.toolbar_return_text)
    public void onClick() {
        finish();
    }
}
