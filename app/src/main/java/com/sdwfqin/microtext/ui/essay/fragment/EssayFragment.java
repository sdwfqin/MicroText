package com.sdwfqin.microtext.ui.essay.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseFragment;
import com.sdwfqin.microtext.base.Constants;
import com.sdwfqin.microtext.contract.EssayContract;
import com.sdwfqin.microtext.model.bean.EssayBean;
import com.sdwfqin.microtext.presenter.EssayPresenter;
import com.sdwfqin.microtext.ui.essay.activity.EssayContentActvity;
import com.sdwfqin.microtext.ui.essay.adapter.EssayAdapter;

import java.util.List;

import butterknife.BindView;

public class EssayFragment extends BaseFragment<EssayPresenter> implements EssayContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.essay_recycler)
    RecyclerView essayRecycler;
    @BindView(R.id.essay_srl)
    SwipeRefreshLayout essaySrl;
    private static final String TAG = "EssayFragment";

    private int code;
    private String url;
    private int pageId = 1;
    private LinearLayoutManager linearLayoutManager;
    private EssayAdapter essayAdapter;

    public static EssayFragment newInstance(int param) {
        EssayFragment fragment = new EssayFragment();
        Bundle args = new Bundle();
        args.putInt("code", param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_essay;
    }

    @Override
    protected void initEventAndData() {
        if (getArguments() != null) {
            code = getArguments().getInt("code");
            url = Constants.ESSAY_URL[code];
        }

        Log.e(TAG, "initEventAndData: " + code);

        essaySrl.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);

        linearLayoutManager = new LinearLayoutManager(mContext);
        essayRecycler.setLayoutManager(linearLayoutManager);

        DividerItemDecoration decoration = new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL);
        essayRecycler.addItemDecoration(decoration);

        essayAdapter = new EssayAdapter(R.layout.items_essay, null);
        essayRecycler.setAdapter(essayAdapter);

        essayAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                EssayBean essayBean = (EssayBean) adapter.getData().get(position);
                Intent intent = new Intent(mContext, EssayContentActvity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", essayBean.getUrl());
                bundle.putString("title", essayBean.getTitle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        // 上拉加载
        essayAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadData(url, pageId++);
            }
        }, essayRecycler);
        // 下拉刷新监听器
        essaySrl.setOnRefreshListener(this);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || isLoad) {
            return;
        }
        // 加载数据
        mPresenter.initData(url, pageId++);
        isLoad = true;
    }

    @Override
    public void showProgress() {
        essaySrl.setRefreshing(true);
    }

    @Override
    public void hideProgress() {
        essaySrl.setRefreshing(false);
    }

    @Override
    public void setData(List<EssayBean> data) {
        essayAdapter.addData(data);
    }

    @Override
    public void refreshData(List<EssayBean> data) {
        essayAdapter.setNewData(data);
    }

    @Override
    public void LoadData(List<EssayBean> data) {
        essayAdapter.addData(data);
        // 本次加载完成
        essayAdapter.loadMoreComplete();
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshData(url, 1);
        pageId = 2;
    }
}
