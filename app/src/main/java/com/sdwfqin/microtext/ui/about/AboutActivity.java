package com.sdwfqin.microtext.ui.about;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseActivity;
import com.sdwfqin.microtext.base.Constants;
import com.sdwfqin.microtext.contract.AboutContract;
import com.sdwfqin.microtext.presenter.AboutPresenter;

import butterknife.BindView;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity<AboutPresenter> implements AboutContract.View {

    @BindView(R.id.about_version)
    TextView mAboutVersion;
    @BindView(R.id.toolbar_return_text)
    ImageView mToolbarReturnText;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.about_update)
    RelativeLayout mAboutUpdate;
    @BindView(R.id.about_haoping)
    RelativeLayout mAboutHaoping;
    @BindView(R.id.about_me)
    RelativeLayout mAboutMe;
    @BindView(R.id.about_project)
    RelativeLayout mAboutProject;
    @BindView(R.id.about_cache_size)
    TextView aboutCacheSize;
    @BindView(R.id.about_cache)
    RelativeLayout aboutCache;
    @BindView(R.id.card_view)
    CardView cardView;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_about;
    }

    @Override
    protected void initEventAndData() {
        mToolbarReturnText.setVisibility(View.VISIBLE);
        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText("关于我们");
        mAboutVersion.setText("V " + AppUtils.getAppVersionName());
        aboutCacheSize.setText(FileUtils.getDirSize(Constants.PATH_CACHE));
    }

    @OnClick({R.id.toolbar_return_text, R.id.about_cache, R.id.about_update, R.id.about_haoping, R.id.about_me, R.id.about_project})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_return_text:
                finish();
                break;
            case R.id.about_update:
                startActivity(new Intent(this, AboutVersionActivity.class));
                break;
            case R.id.about_cache:
                FileUtils.deleteFilesInDir(Constants.PATH_CACHE);
                aboutCacheSize.setText(FileUtils.getDirSize(Constants.PATH_CACHE));
                break;
            case R.id.about_haoping:
                marketDownload(this, "com.sdwfqin.microtext");
                break;
            case R.id.about_me:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sdwfqin.com/about/")));
                break;
            case R.id.about_project:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/sdwfqin/MicroText")));
                break;
        }
    }

    @Override
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
