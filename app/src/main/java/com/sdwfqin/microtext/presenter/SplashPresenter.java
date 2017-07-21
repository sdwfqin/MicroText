package com.sdwfqin.microtext.presenter;

import com.sdwfqin.microtext.base.Constants;
import com.sdwfqin.microtext.base.RxPresenter;
import com.sdwfqin.microtext.contract.SplashContract;
import com.sdwfqin.microtext.model.DataManager;
import com.sdwfqin.microtext.util.RxUtil;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

import static com.sdwfqin.microtext.base.Constants.DELAY;

/**
 * Created by sdwfqin on 2017/7/21.
 */
public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter {

    private DataManager mDataManager;

    @Inject
    public SplashPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void initData() {

        addSubscribe(Flowable.create(new FlowableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(@NonNull FlowableEmitter<Integer> e) throws Exception {
                        // 获取开始执行时间
                        long startTime = System.currentTimeMillis();

                        long nowTime = System.currentTimeMillis();
                        long spendTime = nowTime - startTime;

                        if (spendTime < DELAY){
                            Thread.sleep(DELAY - spendTime);
                        }

                        e.onNext(Constants.CODE_ENTER_HOME);
                        e.onComplete();
                    }
                }, BackpressureStrategy.ERROR)
                        .compose(RxUtil.<Integer>rxSchedulerHelper())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(@NonNull Integer integer) throws Exception {
                                mView.enterHome();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(@NonNull Throwable throwable) throws Exception {
                                mView.showErrorMsg("加载错误。。。");
                            }
                        })
        );
    }
}
