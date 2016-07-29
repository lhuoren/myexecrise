package com.myexercuse.myexercise.mvp.presenter;

import android.util.Log;

import com.myexercuse.myexercise.data.NewsData;
import com.myexercuse.myexercise.mvp.model.MyExerciseFactory;
import com.myexercuse.myexercise.mvp.ui.Fragment.NewsFragment;
import com.myexercuse.myexercise.mvp.view.FNContentView;
import com.myexercuse.myexercise.util.Commons;
import com.myexercuse.myexercise.util.LogUtils;
import com.myexercuse.myexercise.util.NewsJsonUtils;

import java.util.List;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by job on 2016/7/21.
 */
public class FNContentPresenterImpl extends BasePresenterImpl{
    private FNContentView fnContentView;

    public FNContentPresenterImpl(FNContentView fnContentView){
        this.fnContentView = fnContentView;
    }

    public void getNews(final int type, int newsPage){
        LogUtils.i("type",type+"");
        switch (type){
            case NewsFragment.NEWS_TYPE_HEADLINE:
                subscriptionNewsDatas(Commons.TOP_PATH, Commons.TOP_NID, newsPage);
                break;
            case NewsFragment.NEWS_TYPE_NBA:
                subscriptionNewsDatas(Commons.COMMON_URL, Commons.NBA_NID, newsPage);
                break;
            case NewsFragment.NEWS_TYPE_CAR:
                subscriptionNewsDatas(Commons.COMMON_URL, Commons.CAR_NID, newsPage);
                break;
            case NewsFragment.NEWS_TYPE_JOKE:
                subscriptionNewsDatas(Commons.COMMON_URL, Commons.JOKE_NID, newsPage);
                break;
            default:
                break;
        }

    }

    private void subscriptionNewsDatas(String newsPath, final String newsNid, int newsPage){
        LogUtils.i("newsNid",newsNid);
        Subscription subscription = MyExerciseFactory.getNewsIOSingleton()
                .getNewsData(newsPath, newsNid, newsPage)
                .subscribeOn(Schedulers.io())
                .map(new Func1<String, List<NewsData>>() {
                    @Override
                    public List<NewsData> call(String s) {
                        LogUtils.i("ssss",s);
                        return NewsJsonUtils.readJsonNewsBeans(s, newsNid);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<NewsData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("eeee",e.toString());
                        fnContentView.getNewsFailure();
                    }

                    @Override
                    public void onNext(List<NewsData> newsDatas) {
                        fnContentView.getNewsSuccess(newsDatas);
                    }

                });
        addSubscription(subscription);
    }
}
