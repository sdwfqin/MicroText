package com.sdwfqin.microtext.presenter;

import com.sdwfqin.microtext.base.RxPresenter;
import com.sdwfqin.microtext.contract.EssayMainContract;
import com.sdwfqin.microtext.model.DataManager;

import javax.inject.Inject;

/**
 * Created by sdwfqin on 2017/7/21.
 */
public class EssayMainPresenter extends RxPresenter<EssayMainContract.View> implements EssayMainContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public EssayMainPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }
}
