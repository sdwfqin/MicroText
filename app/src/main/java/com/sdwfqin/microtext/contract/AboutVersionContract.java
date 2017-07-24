package com.sdwfqin.microtext.contract;

import com.sdwfqin.microtext.base.BasePresenter;
import com.sdwfqin.microtext.base.BaseView;
import com.sdwfqin.microtext.model.bean.VersionBean;

import java.util.List;

/**
 * Created by sdwfqin on 2017/7/24.
 */
public interface AboutVersionContract {

    interface View extends BaseView {
        void setData(List<VersionBean> beanList);
    }

    interface Presenter extends BasePresenter<View> {
        void initData(String[] code, String[] des);
    }

}
