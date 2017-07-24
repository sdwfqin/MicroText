package com.sdwfqin.microtext.contract;

import android.app.Activity;

import com.sdwfqin.microtext.base.BasePresenter;
import com.sdwfqin.microtext.base.BaseView;
import com.sdwfqin.microtext.model.bean.VersionBean;

import java.util.List;

/**
 * Created by sdwfqin on 2017/7/23.
 */
public interface AboutContract {

    interface View extends BaseView {
        /**
         * 前往市场
         *
         * @param activity
         * @param packageName
         */
        void marketDownload(Activity activity, String packageName);
    }

    interface Presenter extends BasePresenter<View> {
    }

}
