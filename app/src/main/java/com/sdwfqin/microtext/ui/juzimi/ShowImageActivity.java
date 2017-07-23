package com.sdwfqin.microtext.ui.juzimi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.model.bean.JuZiMiBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShowImageActivity extends AppCompatActivity {

    @BindView(R.id.showimage_viewpager)
    ViewPager mViewPager;

    private List<JuZiMiBean> mMainList;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        ButterKnife.bind(this);

        mMainList = (List<JuZiMiBean>) this.getIntent().getSerializableExtra("mainList");
        position = this.getIntent().getIntExtra("position", 0);

        mViewPager.setAdapter(new ShowImagePagerAdapter());
        mViewPager.setCurrentItem(position);
    }

    private class ShowImagePagerAdapter extends FragmentStatePagerAdapter {

        public ShowImagePagerAdapter() {
            super(getSupportFragmentManager());
        }

        @Override
        public int getCount() {
            return mMainList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return ShowImageFragment.newInstance(mMainList.get(position), position);
        }

    }
}
