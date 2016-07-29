package com.myexercuse.myexercise.mvp.model.api;

import com.myexercuse.myexercise.data.GanksData;
import com.myexercuse.myexercise.data.MeizhiData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by job on 2016/6/18.
 */
public interface GankApi {

    @GET("data/福利" + "/{meizhiSize}" + "/{page}")
    Observable<MeizhiData> getMeizhiData(
            @Path("meizhiSize") int meizhiSize, @Path("page") int page);

    @GET("data/{type}/{ganksSize}/{page}")
    Observable<GanksData> getGankData(
            @Path("type") String type, @Path("ganksSize") int androidSize, @Path("page") int page);
}
