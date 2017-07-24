package com.sdwfqin.microtext.presenter;

import com.sdwfqin.microtext.R;
import com.sdwfqin.microtext.base.RxPresenter;
import com.sdwfqin.microtext.contract.AboutVersionContract;
import com.sdwfqin.microtext.model.DataManager;
import com.sdwfqin.microtext.model.bean.VersionBean;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by sdwfqin on 2017/7/24.
 */
public class AboutVersionPresenter extends RxPresenter<AboutVersionContract.View> implements AboutVersionContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public AboutVersionPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void initData(String[] code, String[] des) {
        List<VersionBean> beanList = new ArrayList<VersionBean>();
        for (int i = code.length - 1; i >= 0; i--) {
            VersionBean versionBean = new VersionBean();
            versionBean.setCode(code[i]);
            versionBean.setDes(des[i]);
            beanList.add(versionBean);
        }
    }
}
