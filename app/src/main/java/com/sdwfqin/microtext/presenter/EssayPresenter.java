package com.sdwfqin.microtext.presenter;

import com.sdwfqin.microtext.base.RxPresenter;
import com.sdwfqin.microtext.contract.EssayContract;
import com.sdwfqin.microtext.model.DataManager;
import com.sdwfqin.microtext.model.bean.EssayBean;
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
 * Created by sdwfqin on 2017/7/21.
 */
public class EssayPresenter extends RxPresenter<EssayContract.View> implements EssayContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public EssayPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void initData(String page, int pageId) {
        mView.showProgress();
        addSubscribe(mDataManager.fetchEssay(page + pageId)
                .compose(RxUtil.<String>rxSchedulerHelper())
                .map(new Function<String, List<EssayBean>>() {
                    @Override
                    public List<EssayBean> apply(@NonNull String s) throws Exception {

                        List<EssayBean> beanList = new ArrayList<EssayBean>();

                        Document mDocument = Jsoup.parse(s);

                        Elements es = mDocument.getElementsByClass("info");
                        for (Element e : es) {

                            EssayBean mHomeModel = new EssayBean();
                            mHomeModel.setTitle(e.getElementsByClass("tit").text().toString());
                            mHomeModel.setContent(e.getElementsByTag("p").text().toString());
                            mHomeModel.setUrl(e.getElementsByClass("tit").attr("href").toString());

                            beanList.add(mHomeModel);
                        }
                        return beanList;
                    }
                }).subscribe(new Consumer<List<EssayBean>>() {
                    @Override
                    public void accept(@NonNull List<EssayBean> beanList) throws Exception {
                        mView.setData(beanList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showErrorMsg("网络错误！");
                    }
                }));
        mView.hideProgress();
    }

    @Override
    public void refreshData(String page, int pageId) {
        addSubscribe(mDataManager.fetchEssay(page + pageId)
                .compose(RxUtil.<String>rxSchedulerHelper())
                .map(new Function<String, List<EssayBean>>() {
                    @Override
                    public List<EssayBean> apply(@NonNull String s) throws Exception {

                        List<EssayBean> beanList = new ArrayList<EssayBean>();

                        Document mDocument = Jsoup.parse(s);

                        Elements es = mDocument.getElementsByClass("info");
                        for (Element e : es) {

                            EssayBean mHomeModel = new EssayBean();
                            mHomeModel.setTitle(e.getElementsByClass("tit").text().toString());
                            mHomeModel.setContent(e.getElementsByTag("p").text().toString());
                            mHomeModel.setUrl(e.getElementsByClass("tit").attr("href").toString());

                            beanList.add(mHomeModel);
                        }
                        return beanList;
                    }
                }).subscribe(new Consumer<List<EssayBean>>() {
                    @Override
                    public void accept(@NonNull List<EssayBean> beanList) throws Exception {
                        mView.refreshData(beanList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showErrorMsg("网络错误！");
                    }
                }));
        mView.hideProgress();
    }

    @Override
    public void loadData(String page, int pageId) {
        addSubscribe(mDataManager.fetchEssay(page + pageId)
                .compose(RxUtil.<String>rxSchedulerHelper())
                .map(new Function<String, List<EssayBean>>() {
                    @Override
                    public List<EssayBean> apply(@NonNull String s) throws Exception {

                        List<EssayBean> beanList = new ArrayList<EssayBean>();

                        Document mDocument = Jsoup.parse(s);

                        Elements es = mDocument.getElementsByClass("info");
                        for (Element e : es) {

                            EssayBean mHomeModel = new EssayBean();
                            mHomeModel.setTitle(e.getElementsByClass("tit").text().toString());
                            mHomeModel.setContent(e.getElementsByTag("p").text().toString());
                            mHomeModel.setUrl(e.getElementsByClass("tit").attr("href").toString());

                            beanList.add(mHomeModel);
                        }
                        return beanList;
                    }
                }).subscribe(new Consumer<List<EssayBean>>() {
                    @Override
                    public void accept(@NonNull List<EssayBean> beanList) throws Exception {
                        mView.LoadData(beanList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showErrorMsg("网络错误！");
                    }
                }));
    }
}
