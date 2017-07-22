package com.sdwfqin.microtext.contract;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.sdwfqin.microtext.base.BasePresenter;
import com.sdwfqin.microtext.base.BaseView;

/**
 * Created by sdwfqin on 2017/6/9.
 */
public interface MainContract {

    interface View extends BaseView {

        void initDrawer(Toolbar toolbar);

        /**
         * 跳转Fragment
         * @param newFragment
         */
        void switchFragment(Fragment newFragment);

    }

    interface Presenter extends BasePresenter<View> {

    }

}
