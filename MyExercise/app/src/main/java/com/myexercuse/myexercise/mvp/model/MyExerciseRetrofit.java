package com.myexercuse.myexercise.mvp.model;

import com.myexercuse.myexercise.mvp.model.api.GankApi;
import com.myexercuse.myexercise.mvp.model.api.NewsApi;
import com.myexercuse.myexercise.mvp.model.api.ZhuangbiApi;
import com.myexercuse.myexercise.util.retrofit2.converter.scalars.ScalarsConverterFactory;

import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyExerciseRetrofit {

    private static OkHttpClient okHttpClient = new OkHttpClient();
    private static Converter.Factory gsonConverterFactory = GsonConverterFactory.create();
    private static Converter.Factory stringConverterFactory = ScalarsConverterFactory.create();
    private static CallAdapter.Factory rxJavaCallAdapterFactory = RxJavaCallAdapterFactory.create();

    final GankApi gankService;
    final ZhuangbiApi zhuangbiService;
    final NewsApi newsService;

    public MyExerciseRetrofit() {
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
}