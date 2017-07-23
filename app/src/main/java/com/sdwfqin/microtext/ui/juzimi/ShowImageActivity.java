package com.sdwfqin.microtext.ui.juzimi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.model.bean.JuZiMiBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowImageActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_return_text)
    ImageView toolbarReturnText;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.showimage_viewpager)
    ViewPager mViewPager;

    private List<JuZiMiBean> mMainList;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);
        ButterKnife.bind(this);

        inflateMenu();

        toolbarReturnText.setVisibility(View.VISIBLE);
        toolbarTitle.setVisibility(View.VISIBLE);
        toolbarTitle.setText("图片详情");

        mMainList = (List<JuZiMiBean>) this.getIntent().getSerializableExtra("mainList");
        position = this.getIntent().getIntExtra("position", 0);

        mViewPager.setAdapter(new ShowImagePagerAdapter());
        mViewPager.setCurrentItem(position);
    }

    /**
     * 初始化菜单
     */
    private void inflateMenu() {
        Toolbar toolbar = this.toolbar;
        toolbar.inflateMenu(R.menu.menu_showimage);
    }

    @OnClick(R.id.toolbar_return_text)
    public void onViewClicked() {
        finish();
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
