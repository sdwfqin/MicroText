package com.sdwfqin.microtext.ui.main;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.BaseActivity;
import com.sdwfqin.microtext.base.Constants;
import com.sdwfqin.microtext.contract.MainContract;
import com.sdwfqin.microtext.presenter.MainPresenter;
import com.sdwfqin.microtext.ui.about.AboutActivity;
import com.sdwfqin.microtext.ui.essay.fragment.EssayMainFragment;
import com.sdwfqin.microtext.ui.juzimi.JuZiMiFragment;
import com.tencent.bugly.Bugly;

import butterknife.BindView;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

/**
 * 微小文
 *
 * @author sdwfqin
 * @version 2.0.0
 * @since 2017-07-21
 * <p/>
 * 博客: www.sdwfqin.com  邮箱: zhangqin@sdwfqin.com
 * 项目地址：https://github.com/sdwfqin/MicroText
 */
@RuntimePermissions
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer)
    DrawerLayout mDrawer;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private long exitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 腾讯Bugly
        // true表示打开debug模式，false表示关闭调试模式
        Bugly.init(getApplicationContext(), "53e067220d", false);
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View view = navigationView.inflateHeaderView(R.layout.drawer_header);

        LinearLayout Blog = (LinearLayout) view.findViewById(R.id.dh_ll);
        Blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.sdwfqin.com"));
                startActivity(intent);
            }
        });

        navigationView.setNavigationItemSelectedListener(this);
        // 设置MenuItem默认选中项
        navigationView.getMenu().getItem(0).setChecked(true);

        // 获取权限
        MainActivityPermissionsDispatcher.getPermissionWithCheck(MainActivity.this);

        switchFragment(EssayMainFragment.newInstance());
    }

    @Override
    public void initDrawer(Toolbar toolbar) {
        if (toolbar != null) {
            // 监听Drawer拉出、隐藏
            mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawer, toolbar, R.string.open, R.string.close);
            mDrawer.addDrawerListener(mActionBarDrawerToggle);
            mActionBarDrawerToggle.syncState();
        }
    }

    @Override
    public void switchFragment(Fragment newFragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, newFragment).commit();
    }

    // 在需要获取权限的地方注释
    @NeedsPermission({Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    @Override
    public void getPermission() {
    }

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

    // onConfigurationChanged事件并不是只有屏幕方向改变才可以触发，
    // 其他的一些系统设置改变也可以触发，比如打开或者隐藏键盘。
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_home) {
            switchFragment(EssayMainFragment.newInstance());
        } else if (id == R.id.nav_meitu) {
            switchFragment(JuZiMiFragment.newInstance(Constants.CODE_MEITU));
        } else if (id == R.id.nav_shouxie) {
            switchFragment(JuZiMiFragment.newInstance(Constants.CODE_SHOUXIE));
        } else if (id == R.id.nav_duibai) {
            switchFragment(JuZiMiFragment.newInstance(Constants.CODE_DUIBAI));
        } else if (id == R.id.nav_share) {
            /** 分享 **/
            String shareContent = mContext.getResources().getString(
                    R.string.share_content);
            Intent intent = new Intent(Intent.ACTION_SEND); // 启动分享发送的属性
            intent.setType("text/plain"); // 分享发送的数据类型
            intent.putExtra(Intent.EXTRA_TEXT, shareContent); // 分享的内容
            startActivity(Intent.createChooser(intent, "选择分享"));// 目标应用选择对话框的标题
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(mContext, AboutActivity.class));
        }

        // 关闭侧栏
        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
