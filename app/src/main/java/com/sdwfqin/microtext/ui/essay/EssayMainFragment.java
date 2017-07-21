package com.sdwfqin.microtext.ui.essay;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseFragment;
import com.sdwfqin.microtext.base.Constants;
import com.sdwfqin.microtext.contract.EssayMainContract;
import com.sdwfqin.microtext.presenter.EssayMainPresenter;
import com.sdwfqin.microtext.ui.essay.adapter.EssayMainAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class EssayMainFragment extends BaseFragment<EssayMainPresenter> implements EssayMainContract.View {

    @BindView(R.id.essaymain_tab)
    TabLayout mTabLayout;
    @BindView(R.id.essaymain_viewpager)
    ViewPager mViewPager;

    String[] tabTitle = new String[]{"名家作品", "经典文章", "爱情文章", "失恋",
            "心情随笔", "人生哲理", "长篇故事"};
    List<Fragment> fragments = new ArrayList<Fragment>();
    private EssayMainAdapter mHomeAdapter;

    public static EssayMainFragment newInstance() {
        EssayMainFragment fragment = new EssayMainFragment();
        return fragment;
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_essay_main;
    }

    @Override
    protected void initEventAndData() {
        fragments.add(EssayFragment.newInstance(Constants.CODE_MINGJIA));
        fragments.add(EssayFragment.newInstance(Constants.CODE_JDMW));
        fragments.add(EssayFragment.newInstance(Constants.CODE_LOVE));
        fragments.add(EssayFragment.newInstance(Constants.CODE_SHILIAN));
        fragments.add(EssayFragment.newInstance(Constants.CODE_XINQING));
        fragments.add(EssayFragment.newInstance(Constants.CODE_ZHELI));
        fragments.add(EssayFragment.newInstance(Constants.CODE_STORY));

        mHomeAdapter = new EssayMainAdapter(getChildFragmentManager(),fragments);
        mViewPager.setAdapter(mHomeAdapter);

        //TabLayout配合ViewPager有时会出现不显示Tab文字的Bug,需要按如下顺序
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[0]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[1]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[2]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[3]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[4]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[5]));
        mTabLayout.addTab(mTabLayout.newTab().setText(tabTitle[6]));

        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.getTabAt(0).setText(tabTitle[0]);
        mTabLayout.getTabAt(1).setText(tabTitle[1]);
        mTabLayout.getTabAt(2).setText(tabTitle[2]);
        mTabLayout.getTabAt(3).setText(tabTitle[3]);
        mTabLayout.getTabAt(4).setText(tabTitle[4]);
        mTabLayout.getTabAt(5).setText(tabTitle[5]);
        mTabLayout.getTabAt(6).setText(tabTitle[6]);

    }
}
