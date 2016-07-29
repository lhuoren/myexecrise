package com.myexercuse.myexercise.mvp.presenter;

import android.util.Log;

import com.myexercuse.myexercise.data.NewsDetailData;
import com.myexercuse.myexercise.mvp.model.MyExerciseFactory;
import com.myexercuse.myexercise.mvp.view.NewsDetailView;
import com.myexercuse.myexercise.util.LogUtils;
import com.myexercuse.myexercise.util.NewsJsonUtils;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by job on 2016/7/21.
 */
public class NewsDetailPresenterImpl extends BasePresenterImpl{
    private NewsDetailView newsDetailView;

    public NewsDetailPresenterImpl(NewsDetailView newsDetailView){
        this.newsDetailView = newsDetailView;
    }

    public void getDetailNews(final String docId){
        LogUtils.i("docId",docId);
        Subscription subscription = MyExerciseFactory.getNewsIOSingleton()
                .getNewsDetailData(docId)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, NewsDetailData>() {
                    @Override
                    public NewsDetailData call(String s) {
                        LogUtils.i("ssss",s);
                        return NewsJsonUtils.readJsonNewsDetailBeans(s, docId);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsDetailData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("eeee",e.toString());
                        newsDetailView.getNewsDetailFailure();
                    }

                    @Override
                    public void onNext(NewsDetailData newsDetailDatas) {
                        newsDetailView.getNewsDetailSuccess(newsDetailDatas);
                    }

                });
        addSubscription(subscription);

    }

}
