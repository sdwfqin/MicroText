package com.sdwfqin.microtext.ui.essay;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseFragment;
import com.sdwfqin.microtext.contract.EssayContract;
import com.sdwfqin.microtext.presenter.EssayPresenter;

import butterknife.BindView;

public class EssayFragment extends BaseFragment<EssayPresenter> implements EssayContract.View {

    @BindView(R.id.essay_recycler)
    RecyclerView essayRecycler;

    private int code;

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
    }
}
