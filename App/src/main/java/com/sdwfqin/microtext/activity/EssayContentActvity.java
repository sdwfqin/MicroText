package com.sdwfqin.microtext.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.utils.AppConfig;
import com.sdwfqin.microtext.utils.ShowToastUtils;
import com.sdwfqin.microtext.view.SwipeBackActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sdwfqin on 2016/7/20.
 */
public class EssayContentActvity extends SwipeBackActivity {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_return_text)
    ImageView mToolbarReturnText;
    @BindView(R.id.essay_web)
    WebView mEssayWeb;
    @BindView(R.id.essay_progress)
    RelativeLayout mEssayProgress;
    @BindView(R.id.essay_content)
    ScrollView mEssayContent;

    private String url;
    private static final String TAG = "EssayContentActvity";
//    private AlertDialog ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_essay_content);
        ButterKnife.bind(this);

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

        initData();

    }

    // 加载数据
    private void initData() {
        OkHttpUtils
                .get()
                .url(AppConfig.sHomeUrl + url)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        String str = new String(response.body().bytes(), "UTF-8");
                        return str;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ShowToastUtils.showToast(EssayContentActvity.this, "请求失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {

                        setData(response);

                    }
                });
    }

    // 设置数据
    public void setData(String response) {
        Document mDocument = Jsoup.parse(response);

        Elements es = mDocument.getElementsByClass("atcMain");
        String tmp = "";
        for (Element e : es) {
            tmp = e.getElementsByTag("article").toString();
        }

        StringBuffer sb = new StringBuffer().append(tmp);

        //设置默认为utf-8
        mEssayWeb.getSettings().setDefaultTextEncodingName("UTF-8");
        mEssayWeb.loadDataWithBaseURL(AppConfig.sHomeUrl, sb.toString(), "text/html", "UTF-8", null);
        // 监听WebView加载进度
        mEssayWeb.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if(newProgress == 100){
                    mEssayProgress.setVisibility(View.GONE);
                    mEssayContent.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.toolbar_return_text})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toolbar_return_text:
                finish();
                break;
        }
    }


}
