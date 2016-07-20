## AndroidStudio创建项目并上传到github ##

`VCS  ->  Import into Version Control  ->  Share Project on GitHub`

## FloatingActionButton && Snackbar ##

``` java
FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //底部小窗
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_SHORT)
                        .setAction("执行动作", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Toast.makeText(MainActivity.this,"Snackbar 动作",Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
```

``` xml
<android.support.design.widget.FloatingActionButton
    android:id="@+id/fab"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom|end"
    android:layout_margin="@dimen/fab_margin"
    android:src="@android:drawable/ic_dialog_email"/>
```
