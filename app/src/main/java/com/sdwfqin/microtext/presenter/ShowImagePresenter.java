package com.sdwfqin.microtext.presenter;

import com.sdwfqin.microtext.base.RxPresenter;
import com.sdwfqin.microtext.contract.EssayMainContract;
import com.sdwfqin.microtext.contract.ShowImageContract;
import com.sdwfqin.microtext.model.DataManager;

import javax.inject.Inject;

/**
 * Created by sdwfqin on 2017/7/21.
 */
public class ShowImagePresenter extends RxPresenter<ShowImageContract.View> implements ShowImageContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public ShowImagePresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }
}
