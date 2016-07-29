package com.myexercuse.myexercise.mvp.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.data.ZhuangbiImage;
import com.myexercuse.myexercise.mvp.ui.adapter.recyclerview.CommonAdapter;
import com.myexercuse.myexercise.mvp.ui.adapter.recyclerview.ViewHolder;

import java.util.List;

/**
 * Created by job on 2016/7/8.
 */
public class ZhuangbiListAdapter extends CommonAdapter<ZhuangbiImage>{
    public ZhuangbiListAdapter(Context context, List<ZhuangbiImage> datas) {
        super(context, R.layout.item_zhuangbi, datas);
        Log.i("ZhuangbiListAdapter","ZhuangbiListAdapter");
    }

    @Override
    public void convert(ViewHolder holder, ZhuangbiImage zhuangbiImage) {
        //初始化控件
        ImageView iv_zhuangbi = holder.getView(R.id.iv_zhuangbi);
        TextView tv_zhuangbi = holder.getView(R.id.tv_zhuangbi);
        //设置值
        tv_zhuangbi.setText(zhuangbiImage.description);
        Log.i("zhuangbiImage.image_url",zhuangbiImage.image_url);
        Glide.with(mContext).load(zhuangbiImage.image_url).into(iv_zhuangbi);
    }
}
