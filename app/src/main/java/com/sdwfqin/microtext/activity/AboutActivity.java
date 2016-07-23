package com.sdwfqin.microtext.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by sdwfqin on 2016/7/23.
 */
public class AboutActivity extends BaseActivity {
    @InjectView(R.id.about_version)
    TextView mAboutVersion;
    @InjectView(R.id.toolbar_return_text)
    ImageView mToolbarReturnText;
    @InjectView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.about_update)
    RelativeLayout mAboutUpdate;
    @InjectView(R.id.about_haoping)
    RelativeLayout mAboutHaoping;
    @InjectView(R.id.about_me)
    RelativeLayout mAboutMe;
    @InjectView(R.id.about_project)
    RelativeLayout mAboutProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);

        mToolbarReturnText.setVisibility(View.VISIBLE);
        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText("关于我们");
        mAboutVersion.setText("Version：" + getVersionName(this));

    }

    @OnClick({R.id.toolbar_return_text,R.id.about_update, R.id.about_haoping, R.id.about_me, R.id.about_project})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_return_text:
                finish();
                break;
            case R.id.about_update:
                break;
            case R.id.about_haoping:
                marketDownload(this,"com.sdwfqin.microtext");
                break;
            case R.id.about_me:
                startActivity(new Intent(this,AboutMeActivity.class));
                break;
            case R.id.about_project:
                startActivity(new Intent(this,AboutProjectActivity.class));
                break;
        }
    }

    // 获取版本号
    public String getVersionName(Activity activity) {
        PackageManager manager = activity.getPackageManager();
        String packageName = activity.getPackageName();
        try {
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return "1.0.0";
        }
    }

    // 前往市场
    public void marketDownload(Activity activity, String packageName) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setData(Uri.parse("market://details?id=" + packageName));
            activity.startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(activity, "未找到安卓市场", Toast.LENGTH_SHORT).show();
        }
    }
}
