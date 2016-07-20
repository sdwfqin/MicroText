package com.sdwfqin.microtext.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.utils.AppConfig;

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

    //加载数据
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
            TextView textView = new TextView(container.getContext());
            textView.setText(mDataList.get(position));
            textView.setGravity(Gravity.CENTER);
            container.addView(textView);
            return textView;
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

    private void initData() {
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
