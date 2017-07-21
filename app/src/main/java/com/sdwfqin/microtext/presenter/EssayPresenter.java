package com.sdwfqin.microtext.presenter;

import com.sdwfqin.microtext.base.RxPresenter;
import com.sdwfqin.microtext.contract.EssayContract;
import com.sdwfqin.microtext.model.DataManager;

import javax.inject.Inject;

/**
 * Created by sdwfqin on 2017/7/21.
 */
public class EssayPresenter extends RxPresenter<EssayContract.View> implements EssayContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public EssayPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }
}
