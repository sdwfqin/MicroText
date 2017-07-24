package com.sdwfqin.microtext.presenter;

import com.sdwfqin.microtext.base.RxPresenter;
import com.sdwfqin.microtext.contract.AboutContract;
import com.sdwfqin.microtext.contract.AboutVersionContract;
import com.sdwfqin.microtext.model.DataManager;
import com.sdwfqin.microtext.model.bean.VersionBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by sdwfqin on 2017/7/23.
 */
public class AboutPresenter extends RxPresenter<AboutContract.View> implements AboutContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public AboutPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

}
