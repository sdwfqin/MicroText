package com.sdwfqin.microtext.contract;

import com.sdwfqin.microtext.base.BasePresenter;
import com.sdwfqin.microtext.base.BaseView;
import com.sdwfqin.microtext.model.bean.EssayBean;

import java.util.List;

/**
 * Created by sdwfqin on 2017/6/9.
 */
public interface EssayContract {

    interface View extends BaseView {

        void showProgress();

        void hideProgress();

        void setData(List<EssayBean> data);

        void refreshData(List<EssayBean> data);

        void LoadData(List<EssayBean> data);
    }

    interface Presenter extends BasePresenter<View> {

        void initData(String page, int pageId);

        void refreshData(String page, int pageId);

        void loadData(String page, int pageId);
    }

}
