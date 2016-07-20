package com.sdwfqin.microtext.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.utils.AppConfig;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by sdwfqin on 2016/7/19.
 */
public class HomeFragment extends Fragment {

    @InjectView(R.id.home_magic_indicator)
    MagicIndicator mHomeMagicIndicator;
    @InjectView(R.id.home_view_pager)
    ViewPager mHomeViewPager;
    private View mView;

    private List<String> mDataList = new ArrayList<String>();

    //加载标签数据
    {
        for (int i = 0; i < AppConfig.sHomeTitleList.length; i++) {
            mDataList.add(AppConfig.sHomeTitleList[i]);
        }
    }

    private PagerAdapter mAdapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return mDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            return initRecyclerData(container,position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.inject(this, mView);

        initView();
        initData();
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private PullLoadMoreRecyclerView initRecyclerData(ViewGroup container, int position) {
        PullLoadMoreRecyclerView mPullLoadMoreRecyclerView = new PullLoadMoreRecyclerView(container.getContext());
        mPullLoadMoreRecyclerView.setLinearLayout();

        //绑定的适配器
        RecyclerViewAdapter mRecyclerViewAdapter = new RecyclerViewAdapter();
        mPullLoadMoreRecyclerView.setFooterViewText(R.string.order_load_text);
        //设置刷新颜色
        mPullLoadMoreRecyclerView.setColorSchemeResources(android.R.color.holo_orange_dark, android.R.color.holo_blue_dark);
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);

        //调用下拉刷新和加载更多
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });

        //刷新结束
        //mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
        //快速top
        //mPullLoadMoreRecyclerView.scrollToTop();
        //刷新items
        //mRecyclerViewAdapter.notifyDataSetChanged();

        container.addView(mPullLoadMoreRecyclerView);
        return mPullLoadMoreRecyclerView;
    }

    private void initData() {
    }

    private void initView() {

        mHomeViewPager.setAdapter(mAdapter);

        // 天天快报式
        final CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setEnablePivotScroll(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setNormalColor(Color.parseColor("#333333"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#e94220"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mHomeViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                WrapPagerIndicator indicator = new WrapPagerIndicator(context);
                indicator.setFillColor(Color.parseColor("#ebe4e3"));
                return indicator;
            }
        });
        mHomeMagicIndicator.setNavigator(commonNavigator);

        mHomeViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mHomeMagicIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                mHomeMagicIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                mHomeMagicIndicator.onPageScrollStateChanged(state);
            }
        });
    }


    /**
     * item适配器
     */
    class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_items, parent, false);
            return new ViewHolder(view);
        }


        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 10;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            public ViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            public void onClick(View view) {

            }
        }
    }
}
