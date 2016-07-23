package com.sdwfqin.microtext.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseActivity;
import com.sdwfqin.microtext.utils.AppConfig;
import com.sdwfqin.microtext.utils.ShowToastUtils;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by sdwfqin on 2016/7/20.
 */
public class EssayContentActvity extends BaseActivity {

    @InjectView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.toolbar_return_text)
    ImageView mToolbarReturnText;

    private final static String TAG = "MicroText";
    @InjectView(R.id.essay_web)
    WebView mEssayWeb;
    @InjectView(R.id.essay_head)
    TextView mEssayHead;
    private String url;
    private AlertDialog ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_essay_content);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        // 从intent中将封装好的数据取出来
        // 先取Bandle对象
        Bundle bundle = intent.getExtras();
        url = bundle.getString("url");
        String title = bundle.getString("title");

        mToolbarReturnText.setVisibility(View.VISIBLE);
        mToolbarTitle.setVisibility(View.VISIBLE);
        if (title != null) {
            mToolbarTitle.setText(title);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        View view = View.inflate(this, R.layout.dialog_load, null);
        builder.setView(view);

        AsyncHttpClient ahc = new AsyncHttpClient();
        ahc.get(AppConfig.sHomeUrl + url, new MyResponseHandler());

        ad = builder.create();
        ad.show();
    }

    @OnClick({R.id.toolbar_return_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_return_text:
                finish();
                break;
        }
    }

    class MyResponseHandler extends AsyncHttpResponseHandler {
        // 请求服务器成功时，此方法调用
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String doc = new String(responseBody, "GBK");
                URLEncoder.encode(doc, "UTF-8");

                Document mDocument = Jsoup.parse(doc);

                String info = mDocument.getElementsByClass("info").text().toString();
                info = info.substring(0, info.length() - 6);

                Elements es = mDocument.getElementsByClass("content");

                StringBuffer sb = new StringBuffer().append(es.toString());
//                sb.insert(sb.toString().indexOf("src=") + 5, AppConfig.sHomeUrl);

                mEssayHead.setText(info);

                //设置默认为utf-8
                mEssayWeb.getSettings().setDefaultTextEncodingName("UTF-8");
                // mEssayWeb.loadData(es.toString(), "text/html; charset=UTF-8", null);
                mEssayWeb.loadDataWithBaseURL(AppConfig.sHomeUrl, sb.toString(), "text/html", "UTF-8", null);
                ad.dismiss();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                ShowToastUtils.showToast(EssayContentActvity.this, "数据异常");
            }
        }

        // 请求失败此方法调用
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            ShowToastUtils.showToast(EssayContentActvity.this, "请求失败");
        }
    }
}
