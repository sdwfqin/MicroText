package com.sdwfqin.microtext.ui.essay.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseActivity;
import com.sdwfqin.microtext.contract.EssayContentContract;
import com.sdwfqin.microtext.model.http.api.EssayApi;
import com.sdwfqin.microtext.presenter.EssayContentPresenter;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import butterknife.BindView;
import butterknife.OnClick;

public class EssayContentActvity extends BaseActivity<EssayContentPresenter> implements EssayContentContract.View {

    @BindView(R.id.toolbar_return_text)
    ImageView toolbarReturnText;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.essaycontent_progress)
    RelativeLayout mEssayProgress;
    @BindView(R.id.essaycontent_web)
    WebView mEssayWeb;
    @BindView(R.id.essaycontent_content)
    LinearLayout mEssayContent;

    private String mUrl;
    private String mTitle;
    private static final String TAG = "EssayContentActvity";

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_essay_content_actvity;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        super.onViewCreated(savedInstanceState);
        Intent intent = getIntent();
        // 从intent中将封装好的数据取出来
        // 先取Bandle对象
        Bundle bundle = intent.getExtras();
        mUrl = bundle.getString("url");
        mTitle = bundle.getString("title");
        Log.e(TAG, "onCreate: " + mUrl);
    }

    @Override
    protected void initEventAndData() {
        toolbarReturnText.setVisibility(View.VISIBLE);
        toolbarTitle.setVisibility(View.VISIBLE);
        toolbarTitle.setText(mTitle);

        mPresenter.initData(mUrl);
    }

    @Override
    public void setData(String data) {
        //设置默认为utf-8
        mEssayWeb.getSettings().setDefaultTextEncodingName("UTF-8");
        mEssayWeb.loadDataWithBaseURL(EssayApi.HOST, data, "text/html", "UTF-8", null);
        // 监听WebView加载进度
        mEssayWeb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView webView, int i) {
                super.onProgressChanged(webView, i);
                if (i == 100) {
                    mEssayProgress.setVisibility(View.GONE);
                    mEssayContent.setVisibility(View.VISIBLE);
                }
            }
        });
        // 禁用页面中的超链接
        mEssayWeb.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                return true;
            }
        });
    }

    @OnClick(R.id.toolbar_return_text)
    public void onViewClicked() {
        finish();
    }
}
