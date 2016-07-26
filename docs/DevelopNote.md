# AndroidStudio创建项目并上传到github ##

`VCS  ->  Import into Version Control  ->  Share Project on GitHub`

# 大约是建立时的一些比较新的API

1. Snackbar
2. FloatingActionButton
3. AppBarLayout
4. toolbar
5. DrawerLayout
6. CoordinatorLayout
7. NavigationView
8. NestedScrolling
9. CollapsingToolbarLayout 可以折叠的Toolbar

# 安卓6.0权限问题

https://developer.android.com/training/permissions/requesting.html

# 限定屏幕方向

``` xml
AndroidManifest.xml文件的<activity/>标签
android:screenOrientation="landscape"是限制此页面横屏显示，
android:screenOrientation="portrait"是限制此页面数竖屏显示。
```

# WebView

``` java
// 处理乱码与图片显示
Elements es = mDocument.getElementsByClass("content");

StringBuffer sb = new StringBuffer().append(es.toString());
// sb.insert(sb.toString().indexOf("src=") + 5, AppConfig.sHomeUrl);

mEssayHead.setText(info);

// 设置默认为utf-8
mEssayWeb.getSettings().setDefaultTextEncodingName("UTF-8");
// mEssayWeb.loadData(es.toString(), "text/html; charset=UTF-8", null);
mEssayWeb.loadDataWithBaseURL(AppConfig.sHomeUrl, sb.toString(), "text/html", "UTF-8", null);
```

# 颜色

1. 半透明黑色 #89000000
