package com.sdwfqin.microtext.ui.juzimi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseFragment;
import com.sdwfqin.microtext.base.Constants;
import com.sdwfqin.microtext.contract.JuZiMiContract;
import com.sdwfqin.microtext.model.bean.JuZiMiBean;
import com.sdwfqin.microtext.presenter.JuZiMiPresenter;
import com.sdwfqin.microtext.ui.main.MainActivity;

import java.util.List;

import butterknife.BindView;

public class JuZiMiFragment extends BaseFragment<JuZiMiPresenter> implements JuZiMiContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.juzimi_recycler)
    RecyclerView juzimiRecycler;
    @BindView(R.id.juzimi_srl)
    SwipeRefreshLayout juzimiSrl;

    private int code;
    private boolean hasTitle = true;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private JuZiMiAdapter juZiMiAdapter;
    private String url;
    private int pageId = 1;

    public static JuZiMiFragment newInstance(int param) {
        JuZiMiFragment fragment = new JuZiMiFragment();
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
            url = Constants.JUZIMI_URL[code];
            if (code == Constants.CODE_MEITU) {
                hasTitle = true;
            } else {
                hasTitle = false;
            }
        }
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_ju_zi_mi;
    }

    @Override
    protected void initEventAndData() {
        ((MainActivity) getActivity()).initDrawer(mToolbar);

        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(Constants.JUZIMI_TEXT[code]);

        juzimiSrl.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light,
                android.R.color.holo_orange_light, android.R.color.holo_green_light);

        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        juzimiRecycler.setLayoutManager(staggeredGridLayoutManager);

        juZiMiAdapter = new JuZiMiAdapter(R.layout.items_juzimi, null);
        juzimiRecycler.setAdapter(juZiMiAdapter);

        // 上拉加载
        juZiMiAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

            }
        }, juzimiRecycler);

        juzimiSrl.setOnRefreshListener(this);
    }

    @Override
    public void onResume() {
        mPresenter.initData(url, pageId++, hasTitle);
        super.onResume();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void setData(List<JuZiMiBean> data) {
        juZiMiAdapter.addData(data);
    }
}
