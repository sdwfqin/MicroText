package com.sdwfqin.microtext.activity;

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
import com.sdwfqin.microtext.base.BaseActivity;
import com.sdwfqin.microtext.fragment.DuiBaiFragment;
import com.sdwfqin.microtext.fragment.HomeFragment;
import com.sdwfqin.microtext.fragment.MeiTuFragment;
import com.sdwfqin.microtext.fragment.ShouXieFragment;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 微文
 *
 * @author sdwfqin
 * @version 1.0.0
 * @since 2016-07-18
 * @deprecated 博客: www.sdwfqin.com  邮箱: zhangqin@sdwfqin.com
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @InjectView(R.id.toolbar_title)
    TextView mToolbarTitle;
    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle.setVisibility(View.VISIBLE);
        mToolbarTitle.setText(R.string.app_name);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        // 监听Drawer拉出、隐藏
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 设置MenuItem默认选中项
        navigationView.getMenu().getItem(0).setChecked(true);

        switchFragment(new HomeFragment());

    }

    // 跳转Fragment
    private void switchFragment(Fragment newFragment) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        Bundle args = new Bundle();
//        args.putString("url", url);
//        newFragment.setArguments(args);
        fragmentTransaction.replace(R.id.main_fragment, newFragment).commit();
    }

    long exitTime = 0;

    // 返回键
    @Override
    public void onBackPressed() {
        //如果侧栏开启首先关闭侧边栏
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
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
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            mDrawerLayout.openDrawer(GravityCompat.START);
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
            switchFragment(new HomeFragment());
        } else if (id == R.id.nav_meitu) {
            switchFragment(new MeiTuFragment());
        } else if (id == R.id.nav_shouxie) {
            switchFragment(new ShouXieFragment());
        } else if (id == R.id.nav_duibai) {
            switchFragment(new DuiBaiFragment());
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_feedback) {

        } else if (id == R.id.nav_about) {

        }

        // 关闭侧栏
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
