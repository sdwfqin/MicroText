package com.sdwfqin.microtext.contract;

import com.sdwfqin.microtext.base.BasePresenter;
import com.sdwfqin.microtext.base.BaseView;
import com.sdwfqin.microtext.model.bean.JuZiMiBean;

import java.util.List;

/**
 * Created by sdwfqin on 2017/7/22.
 */
public interface JuZiMiContract {

    interface View extends BaseView {
        void showProgress();

        void hideProgress();

        void setData(List<JuZiMiBean> data);
    }

    interface Presenter extends BasePresenter<View> {
        void initData(String page, int pageId, boolean hasTitle);
    }

}
