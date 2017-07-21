package com.sdwfqin.microtext.ui.essay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseFragment;
import com.sdwfqin.microtext.base.Constants;
import com.sdwfqin.microtext.contract.EssayContract;
import com.sdwfqin.microtext.model.bean.EssayBean;
import com.sdwfqin.microtext.presenter.EssayPresenter;
import com.sdwfqin.microtext.ui.essay.adapter.EssayAdapter;

import java.util.List;

import butterknife.BindView;

public class EssayFragment extends BaseFragment<EssayPresenter> implements EssayContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.essay_recycler)
    RecyclerView essayRecycler;
    @BindView(R.id.essay_srl)
    SwipeRefreshLayout essaySrl;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            code = getArguments().getInt("code");
            url = Constants.sHomeCode[code];
        }
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
                Toast.makeText(mActivity, essayBean.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });

        // 上拉加载
        essayAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                mPresenter.loadData(url, pageId++);
                essayAdapter.loadMoreComplete();
            }
        }, essayRecycler);
        // 下拉刷新监听器
        essaySrl.setOnRefreshListener(this);
    }

    @Override
    public void onResume() {
        mPresenter.initData(url, pageId++);
        super.onResume();
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
    }

    @Override
    public void onRefresh() {
        mPresenter.refreshData(url, 1);
    }
}
