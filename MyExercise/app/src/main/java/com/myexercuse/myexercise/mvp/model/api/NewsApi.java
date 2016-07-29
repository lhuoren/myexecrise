package com.myexercuse.myexercise.mvp.model.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by job on 2016/7/21.
 */
public interface NewsApi {
    //http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
    @GET("{path}/{nid}/{page}-20.html")
    Observable<String> getNewsData(
            @Path("path") String path, @Path("nid") String nid, @Path("page") int page);

    //http://c.m.163.com/nc/article/docId/full.html
    @GET("{docId}/full.html")
    Observable<String> getNewsDetailData(
            @Path("docId") String docId);
}
