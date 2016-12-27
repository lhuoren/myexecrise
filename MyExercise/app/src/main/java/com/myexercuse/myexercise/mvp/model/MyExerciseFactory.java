package com.myexercuse.myexercise.mvp.model;

import com.myexercuse.myexercise.mvp.model.api.GankApi;
import com.myexercuse.myexercise.mvp.model.api.NewsApi;
import com.myexercuse.myexercise.mvp.model.api.WeatherApi;
import com.myexercuse.myexercise.mvp.model.api.ZhuangbiApi;

/**
 * Created by job on 2016/6/18.
 */
public class MyExerciseFactory {

    protected final static Object monitor = new Object();
    static GankApi sGankIOSingleton = null;
    static ZhuangbiApi sZhuangbiIOSingleton = null;
    static NewsApi sNewsIOSingleton = null;
    static WeatherApi sWeatherSingleton = null;

    public static GankApi getGankIOSingleton(){
        synchronized(monitor){
            if(sGankIOSingleton == null){
                sGankIOSingleton = new MyExerciseRetrofit().getGankService();
            }
            return sGankIOSingleton;
        }
    }

    public static ZhuangbiApi getZhuangbiIOSingleton(){
        synchronized(monitor){
            if(sZhuangbiIOSingleton == null){
                sZhuangbiIOSingleton = new MyExerciseRetrofit().getZhuangbiService();
            }
            return sZhuangbiIOSingleton;
        }
    }

    public static NewsApi getNewsIOSingleton(){
        synchronized(monitor){
            if(sNewsIOSingleton == null){
                sNewsIOSingleton = new MyExerciseRetrofit().getNewsService();
            }
            return sNewsIOSingleton;
        }
    }

    public static WeatherApi getWeatherSingleton(){
        synchronized(monitor){
            if(sWeatherSingleton == null){
                sWeatherSingleton = new MyExerciseRetrofit().getWeatherService();
            }
            return sWeatherSingleton;
        }
    }
}
