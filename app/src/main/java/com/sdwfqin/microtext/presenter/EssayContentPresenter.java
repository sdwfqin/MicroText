package com.sdwfqin.microtext.presenter;

import android.util.Log;

import com.sdwfqin.microtext.base.RxPresenter;
import com.sdwfqin.microtext.contract.EssayContentContract;
import com.sdwfqin.microtext.contract.MainContract;
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
 * Created by sdwfqin on 2017/6/9.
 */
public class EssayContentPresenter extends RxPresenter<EssayContentContract.View> implements EssayContentContract.Presenter {

    private DataManager mDataManager;
    private static final String TAG = "EssayContentPresenter";

    @Inject
    public EssayContentPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void initData(String url) {
        addSubscribe(mDataManager.fetchEssayContent(url)
                .compose(RxUtil.<String>rxSchedulerHelper())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(@NonNull String s) throws Exception {

                        Document mDocument = Jsoup.parse(s);

                        Elements es = mDocument.getElementsByClass("atcMain");
                        String tmp = "";
                        for (Element e : es) {
                            tmp = e.getElementsByTag("article").toString();
                        }

                        return tmp;
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@NonNull String s) throws Exception {
                        mView.setData(s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        mView.showErrorMsg("网络错误！");
                    }
                }));
    }
}
