package com.myexercuse.myexercise.mvp.model.api;

import com.myexercuse.myexercise.data.WeatherData;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by job on 2016/6/18.
 */
public interface WeatherApi {

    /**
     * https://free-api.heweather.com/v5/now?city=北京&key=cb2faa4b1011491a8bd3b953907588ed
     * city:required string
     * 城市名称 city可通过城市中英文名称、ID和IP地址进行，例如city=北京，city=beijing，city=CN101010100，city= 60.194.130.1
     * key:required string
     * 用户认证key
     * lang:string
     * 多语言，默认为中文，可选参数
     */

//    @GET("now" + "?city={city}" + "&key={key}")
    @GET("now")
    Observable<WeatherData> getWeatherData(
            @Query("city") String city, @Query("key") String key);

}
