package com.sdwfqin.microtext.model.http;

import com.sdwfqin.microtext.model.http.api.EssayApi;

import javax.inject.Inject;

import io.reactivex.Flowable;

public class RetrofitHelper implements HttpHelper {

    private EssayApi mEssayService;

    /**
     * 注入
     * <p>
     * 详见：HttpModule -> provideZhihuService
     *
     * @param essayApi TestApi接口的实现类的实例对象
     */
    @Inject
    public RetrofitHelper(EssayApi essayApi) {
        this.mEssayService = essayApi;
    }

    /**
     * 调用接口中的业务方法
     *
     * @param page
     * @return
     */
    @Override
    public Flowable<String> fetchEssay(String page) {
        return mEssayService.getEssay(page);
    }
}
