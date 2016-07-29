package com.myexercuse.myexercise.mvp.view;

import com.myexercuse.myexercise.data.NewsData;

import java.util.List;

/**
 * Created by job on 2016/7/21.
 */
public interface FNContentView {
    void getNews();
    void getNewsSuccess(List<NewsData> newsDatas);
    void getNewsFailure();
}
