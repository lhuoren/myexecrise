package com.myexercuse.myexercise.mvp.presenter;

import android.util.Log;

import com.myexercuse.myexercise.data.WeatherData;
import com.myexercuse.myexercise.mvp.model.MyExerciseFactory;
import com.myexercuse.myexercise.mvp.view.WeatherView;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by job on 2016/7/8.
 */
public class WeatherPresenterImpl extends BasePresenterImpl {

    private WeatherView mWeatherView;

    public WeatherPresenterImpl(WeatherView weatherView){
        this.mWeatherView = weatherView;
    }

    public void getWeather(String city, String key){
        Log.i("getWeather2","getWeather2");
        subscriptionWeather(city, key);
    }

    private void subscriptionWeather(String city, String key){
        Subscription subscription = MyExerciseFactory.getWeatherSingleton()
                .getWeatherData(city, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WeatherData>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("eeee",e.toString()+",123");
                        mWeatherView.getWeatherFailure();
                    }

                    @Override
                    public void onNext(WeatherData weatherData) {
                        Log.i("weatherData",weatherData.toString());
                        mWeatherView.getWeatherSuccess(weatherData);
                    }

                });
        addSubscription(subscription);
    }

}
