package com.myexercuse.myexercise.mvp.view;

import com.myexercuse.myexercise.data.GanksData;

/**
 * Created by job on 2016/7/8.
 */
public interface FGContentView {

    void getGanks();
    void getGanksSuccess(GanksData ganksData);
    void getGanksFailure();
}
