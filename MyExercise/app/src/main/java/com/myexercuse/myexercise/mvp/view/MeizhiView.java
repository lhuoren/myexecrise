package com.myexercuse.myexercise.mvp.view;

import com.myexercuse.myexercise.data.MeizhiData;

/**
 * Created by job on 2016/6/27.
 */
public interface MeizhiView {

    void GetMeizhis();
    void GetMeizhiFailure();
    void GetMeizhiSuccess(MeizhiData meizhiDatas);

}
