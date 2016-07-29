package com.myexercuse.myexercise.mvp.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.data.entity.GankEntity;
import com.myexercuse.myexercise.mvp.ui.adapter.recyclerview.CommonAdapter;
import com.myexercuse.myexercise.mvp.ui.adapter.recyclerview.ViewHolder;

import java.util.List;

/**
 * Created by job on 2016/7/8.
 */
public class GankListAdapter extends CommonAdapter<GankEntity>{

    public GankListAdapter(Context context, List<GankEntity> datas) {
        super(context, R.layout.item_gank, datas);
    }

    @Override
    public void convert(ViewHolder holder, GankEntity gankEntity) {
        //初始化控件
//        ImageView iv_gank = holder.getView(R.id.iv_gank);
//        Log.i("gankEntity.who", gankEntity.who);
        TextView tv_gank = holder.getView(R.id.tv_gank_title);
        //设置值
        if(gankEntity.desc != null){
            if(gankEntity.who!=null){
                tv_gank.setText(gankEntity.desc + "作者：" + gankEntity.who);
            }else {
                tv_gank.setText(gankEntity.desc + "作者：");
            }
        }

    }
}
