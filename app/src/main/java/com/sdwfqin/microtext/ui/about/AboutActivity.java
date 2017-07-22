package com.sdwfqin.microtext.ui.about;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.util.SystemUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);

        mToolbarReturnText.setVisibility(View.VISIBLE);
        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText("关于我们");
        mAboutVersion.setText("V " + SystemUtil.getVersionName(this));

    }

    @OnClick({R.id.toolbar_return_text,R.id.about_update, R.id.about_haoping, R.id.about_me, R.id.about_project})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_return_text:
                finish();
                break;
            case R.id.about_update:
                //startActivity(new Intent(this,AboutVersionActivity.class));
                break;
            case R.id.about_haoping:
                marketDownload(this,"com.sdwfqin.microtext");
                break;
            case R.id.about_me:
                //startActivity(new Intent(this,AboutMeActivity.class));
                break;
            case R.id.about_project:
                //startActivity(new Intent(this,AboutProjectActivity.class));
                break;
        }
    }

    /**
     * 前往市场
     * @param activity
     * @param packageName
     */
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
