package com.myexercuse.myexercise.mvp.view;

import com.myexercuse.myexercise.data.WeatherData;

/**
 * Created by job on 2016/7/8.
 */
public interface WeatherView {

    void getWeather();
    void getWeatherSuccess(WeatherData weatherData);
    void getWeatherFailure();
}
