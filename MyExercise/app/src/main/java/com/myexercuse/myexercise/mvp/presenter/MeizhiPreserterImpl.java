package com.myexercuse.myexercise.mvp.presenter;

import com.myexercuse.myexercise.data.MeizhiData;
import com.myexercuse.myexercise.mvp.model.MyExerciseFactory;
import com.myexercuse.myexercise.mvp.view.MeizhiView;
import com.sina.weibo.sdk.utils.LogUtil;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by job on 2016/6/27.
 */
public class MeizhiPreserterImpl extends BasePresenterImpl implements MeizhiPreserter{

    private MeizhiView meizhiView;

    public MeizhiPreserterImpl(MeizhiView meizhiView){
        this.meizhiView = meizhiView;
    }


    @Override
    public void GetMeizhis(int meizhiSize,int page) {
        Subscription subscription = MyExerciseFactory.getGankIOSingleton()
                .getMeizhiData(meizhiSize,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MeizhiData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        meizhiView.GetMeizhiFailure();
                    }

                    @Override
                    public void onNext(MeizhiData meizhiDatas) {
                        LogUtil.i("meizhiDatas",meizhiDatas.toString());
                        meizhiView.GetMeizhiSuccess(meizhiDatas);
                    }

                });
        addSubscription(subscription);
    }
}
