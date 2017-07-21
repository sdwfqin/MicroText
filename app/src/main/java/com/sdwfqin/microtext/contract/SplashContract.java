package com.sdwfqin.microtext.contract;

import com.sdwfqin.microtext.base.BasePresenter;
import com.sdwfqin.microtext.base.BaseView;

/**
 * Created by sdwfqin on 2017/7/21.
 */
public interface SplashContract {

    interface View extends BaseView {

        void enterHome();

    }

    interface Presenter extends BasePresenter<View> {
        void initData();
    }

}
