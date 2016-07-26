package com.sdwfqin.microtext.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.sdwfqin.microtext.Model.SecondModel;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.adapter.RecyclerViewAdapter;
import com.sdwfqin.microtext.utils.ShowToastUtils;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by sdwfqin on 2016/7/19.
 */
public class SecondFragment extends Fragment {
    @InjectView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private int mPage = 1;
    private View mView;
    private String url;
    private boolean hasTitle = true;
    private List<SecondModel> mDataList = new ArrayList<SecondModel>();
    RecyclerViewAdapter mRecyclerViewAdapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString("url");
            if (url.equals("http://www.juzimi.com/meitumeiju?page=")) {
                hasTitle = true;
            } else {
                hasTitle = false;
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.second_fragment, container, false);
        ButterKnife.inject(this, mView);

        initData();
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private void initData() {
        mPullLoadMoreRecyclerView.setStaggeredGridLayout(2);//参数为列数
        mPullLoadMoreRecyclerView.setFooterViewText(R.string.order_load_text);
        //设置刷新颜色
        mPullLoadMoreRecyclerView.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_dark);
        mRecyclerViewAdapter = new RecyclerViewAdapter(getActivity(), mDataList,hasTitle);
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);
        mPullLoadMoreRecyclerView.setRefreshing(true);
        mDataList.clear();
        initItemData();
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                mDataList.clear();
                mPage = 1;
                initItemData();
            }

            @Override
            public void onLoadMore() {
                mPage = mPage + 1;
                initItemData();

            }
        });
    }

    private void initItemData() {

        AsyncHttpClient ahc = new AsyncHttpClient();
        ahc.get(url + mPage, new MyResponseHandler());
    }

    class MyResponseHandler extends AsyncHttpResponseHandler {
        // 请求服务器成功时，此方法调用
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

            try {
                String doc = new String(responseBody, "UTF-8");

                Document mDocument = Jsoup.parse(doc);
                List<String> titleData = null;
                if (hasTitle) {
                    titleData = new ArrayList<>();
                    Elements es = mDocument.getElementsByClass("xlistju");
                    for (Element e : es) {
                        titleData.add(e.text());
                    }
                }
                List<String> hrefData = new ArrayList<>();
                Elements es1 = mDocument.getElementsByClass("chromeimg");
                for (Element e : es1) {
                    hrefData.add(e.attr("src"));
                }

                for (int i = 0; i < hrefData.size(); i++) {
                    SecondModel mSecondModel = new SecondModel();
                    if (hasTitle) {
                        mSecondModel.setTitle(titleData.get(i));
                    }
                    mSecondModel.setIamgeUrl(hrefData.get(i));
                    mDataList.add(mSecondModel);
                }

                mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                mRecyclerViewAdapter.notifyDataSetChanged();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                ShowToastUtils.showToast(getActivity(), "数据异常");
            }
        }

        // 请求失败此方法调用
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
            ShowToastUtils.showToast(getActivity(), "请求失败");
        }
    }

}
