package com.sdwfqin.microtext.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.fragment.HomeFragment;
import com.sdwfqin.microtext.fragment.SecondFragment;
import com.sdwfqin.microtext.base.BaseActivity;
import com.umeng.fb.FeedbackAgent;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 微小文
 *
 * @author sdwfqin
 * @version 1.3.0
 * @since 2016-12-05
 * <p/>
 * 博客: www.sdwfqin.com  邮箱: zhangqin@sdwfqin.com
 * 项目地址：https://github.com/sdwfqin/MicroText
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.drawer)
    DrawerLayout mDrawer;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private Context mContext = MainActivity.this;
    private FeedbackAgent agent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(R.string.app_name);

        // 监听Drawer拉出、隐藏
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.open, R.string.close);
        mDrawer.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 设置MenuItem默认选中项
        navigationView.getMenu().getItem(0).setChecked(true);

        switchFragment(HomeFragment.newInstance());

        // 友盟用户反馈
        agent = new FeedbackAgent(mContext);
        agent.sync();
        agent.closeAudioFeedback();

    }

    // 跳转Fragment
    private void switchFragment(Fragment newFragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, newFragment).commit();
    }

    long exitTime = 0;

    // 返回键
    @Override
    public void onBackPressed() {
        //如果侧栏开启首先关闭侧边栏
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (System.currentTimeMillis() - exitTime > 2000) {
                Toast.makeText(getApplicationContext(), R.string.main_exit, Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                super.onBackPressed();
            }
        }
    }

    // 菜单键
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            mDrawer.openDrawer(GravityCompat.START);
        }
        return false;
    }

    //onConfigurationChanged事件并不是只有屏幕方向改变才可以触发，
    // 其他的一些系统设置改变也可以触发，比如打开或者隐藏键盘。
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    // NavigationItem点击
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            switchFragment(HomeFragment.newInstance());
            mToolbarTitle.setText(R.string.app_name);
        } else if (id == R.id.nav_meitu) {
            switchFragment(SecondFragment.newInstance("http://www.juzimi.com/meitumeiju?page="));
            mToolbarTitle.setText(R.string.nav_meitu_text);
        } else if (id == R.id.nav_shouxie) {
            switchFragment(SecondFragment.newInstance("http://www.juzimi.com/meitumeiju/shouxiemeiju?page="));
            mToolbarTitle.setText(R.string.nav_shouxie_text);
        } else if (id == R.id.nav_duibai) {
            switchFragment(SecondFragment.newInstance("http://www.juzimi.com/meitumeiju/jingdianduibai?page="));
            mToolbarTitle.setText(R.string.nav_duibai_text);
        } else if (id == R.id.nav_share) {
            /** 分享 **/
            String shareContent = mContext.getResources().getString(
                    R.string.share_content);
            Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
            intent.setType("text/plain"); // 分享发送的数据类型
            intent.putExtra(Intent.EXTRA_TEXT, shareContent); // 分享的内容
            startActivity(Intent.createChooser(intent, "选择分享"));// 目标应用选择对话框的标题
        } else if (id == R.id.nav_feedback) {
            agent.startFeedbackActivity();
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(mContext, AboutActivity.class));
        }

        // 关闭侧栏
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
