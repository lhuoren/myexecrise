package com.myexercuse.myexercise.mvp.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.data.MeizhiData;
import com.myexercuse.myexercise.mvp.ui.adapter.recyclerview.CommonAdapter;
import com.myexercuse.myexercise.mvp.ui.adapter.recyclerview.ViewHolder;

import java.util.List;

/**
 * Created by job on 2016/6/19.
 */
public class MeizhiListAdapter extends CommonAdapter<MeizhiData.ResultsBean>{

    private Activity activity = (Activity) mContext;

    public MeizhiListAdapter(Context context, List<MeizhiData.ResultsBean> datas) {
        super(context, R.layout.item_meizhi, datas);
    }

    @Override
    public void convert(ViewHolder holder, MeizhiData.ResultsBean resultsBean) {

        //初始化控件
        ImageView iv_Item_Meizhi = holder.getView(R.id.iv_item_mezizhi_img);
        //给ImageView设置固定高度，防止错位但是宽高固定死了
//        CardView.LayoutParams lp = (CardView.LayoutParams) iv_Item_Meizhi.getLayoutParams();
//        lp.width = costomImageViewWidth();
//        lp.height = costomImageViewHight();
//        iv_Item_Meizhi.setLayoutParams(lp);


        Log.i("resultsBean.getUrl()",resultsBean.getUrl());
        Glide.with(mContext).load(resultsBean.getUrl()).into(iv_Item_Meizhi);

        //监听事件

    }

    private int costomImageViewWidth() {

        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels * 4 / 5;
        Log.i("width", String.valueOf(width));
        return width;
    }

    private int costomImageViewHight() {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int height = metric.heightPixels/3;
        Log.i("height", String.valueOf(height));
        return height;
    }
}
