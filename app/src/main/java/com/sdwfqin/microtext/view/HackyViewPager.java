package com.sdwfqin.microtext.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2015/11/22 0022.
 */
public class HackyViewPager extends ViewPager {

    private boolean isLocked;

    public HackyViewPager(Context context) {
        super(context);
        isLocked = false;
    }

    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        isLocked = false;
    }

    /**
     * 触发拦截触摸事件
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (!isLocked) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    /**
     * 触发触摸事件
     *
     * @param event 手机屏幕触摸事件封装类的对象，其中封装了该事件的所有信息，
     *              例如触摸的位置、触摸的类型以及触摸的时间等。该对象会在用户触摸手机屏幕时被创建。
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !isLocked && super.onTouchEvent(event);
    }

    //更改锁状态
    public void toggleLock() {
        isLocked = !isLocked;
    }

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public boolean isLocked() {
        return isLocked;
    }

}