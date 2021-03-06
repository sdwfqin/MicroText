package com.sdwfqin.microtext.presenter;

import com.sdwfqin.microtext.base.RxPresenter;
import com.sdwfqin.microtext.contract.JuZiMiContract;
import com.sdwfqin.microtext.model.DataManager;
import com.sdwfqin.microtext.model.bean.JuZiMiBean;
import com.sdwfqin.microtext.util.RxUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by sdwfqin on 2017/7/23.
 */
public class JuZiMiPresenter extends RxPresenter<JuZiMiContract.View> implements JuZiMiContract.Presenter {

    private DataManager mDataManager;
    private static final String TAG = "JuZiMiPresenter";

    @Inject
    public JuZiMiPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void initData(String page, int pageId, final boolean hasTitle) {
        mView.showProgress();
        addSubscribe(mDataManager.fetchMeiTu(page, pageId)
                .compose(RxUtil.<String>rxSchedulerHelper())
                .map(new Function<String, List<JuZiMiBean>>() {
                    @Override
                    public List<JuZiMiBean> apply(@NonNull String s) throws Exception {
                        return dataProcessing(s, hasTitle);
                    }
                }).subscribe(new Consumer<List<JuZiMiBean>>() {
                    @Override
                    public void accept(@NonNull List<JuZiMiBean> beanList) throws Exception {
                        mView.setData(beanList);
                        mView.hideProgress();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showErrorMsg("网络错误！");
                        mView.hideProgress();
                    }
                }));
    }

    @Override
    public void refreshData(String page, int pageId, final boolean hasTitle) {
        mView.showProgress();
        addSubscribe(mDataManager.fetchMeiTu(page, pageId)
                .compose(RxUtil.<String>rxSchedulerHelper())
                .map(new Function<String, List<JuZiMiBean>>() {
                    @Override
                    public List<JuZiMiBean> apply(@NonNull String s) throws Exception {
                        return dataProcessing(s, hasTitle);
                    }
                }).subscribe(new Consumer<List<JuZiMiBean>>() {
                    @Override
                    public void accept(@NonNull List<JuZiMiBean> beanList) throws Exception {
                        mView.refreshData(beanList);
                        mView.hideProgress();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showErrorMsg("网络错误！");
                        mView.hideProgress();
                    }
                }));
    }

    @Override
    public void loadData(String page, int pageId, final boolean hasTitle) {
        addSubscribe(mDataManager.fetchMeiTu(page, pageId)
                .compose(RxUtil.<String>rxSchedulerHelper())
                .map(new Function<String, List<JuZiMiBean>>() {
                    @Override
                    public List<JuZiMiBean> apply(@NonNull String s) throws Exception {
                        return dataProcessing(s, hasTitle);
                    }
                }).subscribe(new Consumer<List<JuZiMiBean>>() {
                    @Override
                    public void accept(@NonNull List<JuZiMiBean> beanList) throws Exception {
                        mView.LoadData(beanList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showErrorMsg("网络错误！");
                    }
                }));
    }

    /**
     * 解析数据
     * @param s
     * @param hasTitle
     * @return
     */
    private List<JuZiMiBean> dataProcessing(String s, boolean hasTitle) {
        List<JuZiMiBean> beanList = new ArrayList<JuZiMiBean>();

        Document mDocument = Jsoup.parse(s);

        List<String> titleData = null;
        if (hasTitle) {
            titleData = new ArrayList<>();
            Elements es = mDocument.getElementsByClass("xlistju");
            for (Element e : es) {
                titleData.add(e.text());
            }
        }
        List<String> hrefData = new ArrayList<>();
        Elements es1 = mDocument.getElementsByClass("chromeimg");
        for (Element e : es1) {
            hrefData.add("http:" + e.attr("src"));
        }

        for (int i = 0; i < hrefData.size(); i++) {
            JuZiMiBean mJuZiMiBean = new JuZiMiBean();
            if (hasTitle) {
                mJuZiMiBean.setTitle(titleData.get(i));
            }
            mJuZiMiBean.setIamgeUrl(hrefData.get(i));
            beanList.add(mJuZiMiBean);
        }

        return beanList;
    }
}
