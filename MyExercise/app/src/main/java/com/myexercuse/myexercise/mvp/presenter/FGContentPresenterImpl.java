package com.myexercuse.myexercise.mvp.presenter;

import android.util.Log;

import com.myexercuse.myexercise.data.GanksData;
import com.myexercuse.myexercise.mvp.model.MyExerciseFactory;
import com.myexercuse.myexercise.mvp.ui.Fragment.GankFragment;
import com.myexercuse.myexercise.mvp.view.FGContentView;
import com.myexercuse.myexercise.util.Commons;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by job on 2016/7/8.
 */
public class FGContentPresenterImpl extends BasePresenterImpl {

    private FGContentView fgContentView;

    public FGContentPresenterImpl(FGContentView fgContentView){
        this.fgContentView = fgContentView;
    }

    public void getGanks(int type, int ganksSize, int gankspage){
        switch (type) {
            case GankFragment.GANK_TYPE_ANDROID:
                subscriptionGankDatas(Commons.ANDROID, ganksSize, gankspage);
                break;

            case GankFragment.GANK_TYPE_IOS:
                subscriptionGankDatas(Commons.IOS, ganksSize, gankspage);
                break;

            case GankFragment.GANK_TYPE_VIDIO:
                subscriptionGankDatas(Commons.VIDIO, ganksSize, gankspage);
                break;

            case GankFragment.GANK_TYPE_RECOMMEND:
                subscriptionGankDatas(Commons.RECOMMEND, ganksSize, gankspage);
                break;

            case GankFragment.GANK_TYPE_EXPAND:
                subscriptionGankDatas(Commons.EXPAND, ganksSize, gankspage);
                break;
            default:
                break;
        }

    }

    private void subscriptionGankDatas(String str_type, int ganksSize, int gankspage){
        Subscription subscription = MyExerciseFactory.getGankIOSingleton()
                .getGankData(str_type, ganksSize, gankspage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GanksData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("eeee",e.toString());
                        fgContentView.getGanksFailure();
                    }

                    @Override
                    public void onNext(GanksData ganksDatas) {
                        fgContentView.getGanksSuccess(ganksDatas);
                    }

                });
        addSubscription(subscription);
    }


//    public void GetZhuanbiImage(String zhuanbi) {
//        Subscription subscription  = MyExerciseFactory.getZhuangbiIOSingleton()
//                .search(zhuanbi)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<ZhuangbiImage>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        fgContentView.GetZhuangbiFailure();
//                    }
//
//                    @Override
//                    public void onNext(List<ZhuangbiImage> zhuangbiImages) {
//                        fgContentView.GetZhuangbiSuccess(zhuangbiImages);
//                    }
//                });
//        addSubscription(subscription);
//    }



}
