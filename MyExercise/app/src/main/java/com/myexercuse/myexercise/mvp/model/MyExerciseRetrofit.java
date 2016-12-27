package com.myexercuse.myexercise.mvp.model;

import android.util.Log;

import com.myexercuse.myexercise.mvp.model.api.GankApi;
import com.myexercuse.myexercise.mvp.model.api.NewsApi;
import com.myexercuse.myexercise.mvp.model.api.WeatherApi;
import com.myexercuse.myexercise.mvp.model.api.ZhuangbiApi;
import com.myexercuse.myexercise.util.retrofit2.converter.scalars.ScalarsConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyExerciseRetrofit {

//    Retrofit retrofit = new Retrofit.Builder().client(new OkHttpClient.Builder()
//            .addNetworkInterceptor(
//                    new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)).build());

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static Converter.Factory stringConverterFactory = ScalarsConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    final GankApi gankService;
    final ZhuangbiApi zhuangbiService;
    final NewsApi newsService;
    final WeatherApi weatherService;

    public MyExerciseRetrofit() {

//        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Log.i("OkHttpClient","OkHttpMessage:"+message);
                }
            });
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
//        }
        Retrofit gankretrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://gank.io/api/")
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        gankService = gankretrofit.create(GankApi.class);

        Retrofit zhuangbiretrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://zhuangbi.info/")
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        zhuangbiService = zhuangbiretrofit.create(ZhuangbiApi.class);

        Retrofit newsretrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://c.m.163.com/nc/article/")
                .addConverterFactory(stringConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        newsService = newsretrofit.create(NewsApi.class);

        Retrofit weatherRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://free-api.heweather.com/v5/")
                .addConverterFactory(gsonConverterFactory)
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .build();
        weatherService = weatherRetrofit.create(WeatherApi.class);
    }

    public GankApi getGankService() {
        return gankService;
    }

    public ZhuangbiApi getZhuangbiService() {
        return zhuangbiService;
    }

    public NewsApi getNewsService() {
        return newsService;
    }

    public WeatherApi getWeatherService() {
        return weatherService;
    }
}