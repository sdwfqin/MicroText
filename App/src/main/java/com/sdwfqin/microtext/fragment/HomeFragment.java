package com.sdwfqin.microtext.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.activity.EssayContentActvity;
import com.sdwfqin.microtext.model.HomeModel;
import com.sdwfqin.microtext.utils.AppConfig;
import com.sdwfqin.microtext.utils.ShowToastUtils;
import com.sdwfqin.microtext.view.LazyViewPager;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.WrapPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by sdwfqin on 2016/7/19.
 */
public class HomeFragment extends Fragment {

    @BindView(R.id.home_magic_indicator)
    MagicIndicator mHomeMagicIndicator;
    @BindView(R.id.home_view_pager)
    LazyViewPager mHomeViewPager;
    private View mView;

    private final static String TAG = "MicroText";

    private List<String> mIndicatorDataList = new ArrayList<String>();
    private List<HomeModel> mDataList = new ArrayList<HomeModel>();
    int i = 1;
    private PullLoadMoreRecyclerView mPullLoadMoreRecyclerView;
    private RecyclerViewAdapter mRecyclerViewAdapter;
    private FloatingActionButton mMFloatingActionButton;

    //加载标签数据
    {
        for (int i = 0; i < AppConfig.sHomeTitleList.length; i++) {
            mIndicatorDataList.add(AppConfig.sHomeTitleList[i]);
        }
    }

    private PagerAdapter mAdapter = new PagerAdapter() {

        @Override
        public int getCount() {
            return mIndicatorDataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            return initRecyclerData(container, position);
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
        ButterKnife.bind(this, mView);

        initView();

        mMFloatingActionButton = (FloatingActionButton) mView.findViewById(R.id.fab);
        mMFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //底部小窗
                mPullLoadMoreRecyclerView.scrollToTop();
            }
        });
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private PullLoadMoreRecyclerView initRecyclerData(ViewGroup container, final int position) {


        mPullLoadMoreRecyclerView = new PullLoadMoreRecyclerView(container.getContext());
        mPullLoadMoreRecyclerView.setLinearLayout();

        //绑定的适配器
        mRecyclerViewAdapter = new RecyclerViewAdapter();
        mPullLoadMoreRecyclerView.setFooterViewText(R.string.order_load_text);
        //设置刷新颜色
        mPullLoadMoreRecyclerView.setColorSchemeResources(android.R.color.holo_red_light, android.R.color.holo_blue_dark);
        mPullLoadMoreRecyclerView.setAdapter(mRecyclerViewAdapter);

        mPullLoadMoreRecyclerView.setRefreshing(true);

        //加载数据
        mDataList.clear();
        i = 1;
        initItemData(position);

        //调用下拉刷新和加载更多
        mPullLoadMoreRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                i = 1;
                mDataList.clear();
                initItemData(position);
            }

            @Override
            public void onLoadMore() {
                ++i;
                initItemData(position);
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

    private void initItemData(int position) {

        final String Url = AppConfig.sHomeUrl + AppConfig.sHomeCode[position] + i + ".html";

        Log.e(TAG, "Url===" + Url);
        OkHttpUtils
                .get()
                .url(Url)
                .build()
                .execute(new Callback<String>() {
                    @Override
                    public String parseNetworkResponse(Response response, int id) throws Exception {
                        String str = new String(response.body().bytes(),"GBK");
                        return str;
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                        ShowToastUtils.showToast(getActivity(), "请求失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Document mDocument = Jsoup.parse(response);

                        Elements es = mDocument.getElementsByClass("info");
                        for (Element e : es) {

                            HomeModel mHomeModel = new HomeModel();
                            mHomeModel.setTitle(e.getElementsByClass("tit").text().toString());
                            mHomeModel.setContent(e.getElementsByTag("p").text().toString());
                            mHomeModel.setUrl(e.getElementsByClass("tit").attr("href").toString());

                            mDataList.add(mHomeModel);
                        }
                        mPullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                        mRecyclerViewAdapter.notifyDataSetChanged();
                    }
                });
    }

    private void initView() {

        mHomeViewPager.setAdapter(mAdapter);

        // 天天快报式
        final CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setEnablePivotScroll(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mIndicatorDataList == null ? 0 : mIndicatorDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mIndicatorDataList.get(index));
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

        mHomeViewPager.setOnPageChangeListener(new LazyViewPager.OnPageChangeListener() {
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
            holder.bindData(mDataList.get(position));
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView home_items_title;
            TextView home_items_content;
//            TextView home_items_info;

            private HomeModel mHomeModel;

            public ViewHolder(View itemView) {
                super(itemView);
                itemView.setOnClickListener(this);

                home_items_title = (TextView) itemView.findViewById(R.id.home_items_title);
                home_items_content = (TextView) itemView.findViewById(R.id.home_items_content);
//                home_items_info = (TextView) itemView.findViewById(R.id.home_items_info);
            }

            public void bindData(HomeModel homeModel) {

                mHomeModel = homeModel;
                home_items_title.setText(mHomeModel.getTitle());
                home_items_content.setText(mHomeModel.getContent());
//                home_items_info.setText(mHomeModel.getInfo());

            }

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EssayContentActvity.class);
                Bundle bundle = new Bundle();
                bundle.putString("url", mHomeModel.getUrl());
                bundle.putString("title", mHomeModel.getTitle());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }
}