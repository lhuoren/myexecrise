package com.myexercuse.myexercise.mvp.view;

import com.myexercuse.myexercise.data.NewsDetailData;

/**
 * Created by job on 2016/7/25.
 */
public interface NewsDetailView {
    void getNewsDetail();
    void getNewsDetailSuccess(NewsDetailData newsDetailData);
    void getNewsDetailFailure();
}
