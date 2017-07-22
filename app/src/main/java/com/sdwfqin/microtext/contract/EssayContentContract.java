package com.sdwfqin.microtext.contract;

import com.sdwfqin.microtext.base.BasePresenter;
import com.sdwfqin.microtext.base.BaseView;
import com.sdwfqin.microtext.model.bean.EssayBean;

import java.util.List;

/**
 * Created by sdwfqin on 2017/7/22.
 */
public interface EssayContentContract {

    interface View extends BaseView {
        void setData(String data);
    }

    interface Presenter extends BasePresenter<View> {
        void initData(String url);
    }

}
