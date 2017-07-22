package com.sdwfqin.microtext.model.http;

import com.sdwfqin.microtext.model.http.api.EssayApi;
import com.sdwfqin.microtext.model.http.api.JuZiMiApi;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class RetrofitHelper implements HttpHelper {

    private EssayApi mEssayService;
    private JuZiMiApi mJuZiMiService;

    @Inject
    public RetrofitHelper(EssayApi essayApi, JuZiMiApi juZiMiApi) {
        this.mEssayService = essayApi;
        this.mJuZiMiService = juZiMiApi;
    }

    @Override
    public Flowable<String> fetchEssay(String page) {
        return mEssayService.getEssay(page);
    }

    @Override

    public Flowable<String> fetchEssayContent(String page) {
        return mEssayService.getEssayContent(page);
    }

    @Override
    public Flowable<String> fetchMeiTu(String page, int pageId) {
        return mJuZiMiService.getMeiTu(page, pageId);
    }
}
