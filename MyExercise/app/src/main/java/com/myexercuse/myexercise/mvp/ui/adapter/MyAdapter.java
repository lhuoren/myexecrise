package com.myexercuse.myexercise.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.data.entity.GankEntity;
import com.myexercuse.myexercise.mvp.ui.activity.MainActivity;

import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements View.OnClickListener{
    public List<GankEntity> datas = null;
    private GankEntity gankdata;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private Context mContext;

    public MyAdapter(List<GankEntity> datas,Context context) {
        this.datas = datas;
        this.mContext = context;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View mConvertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gank,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(mConvertView);
        //将创建的View注册点击事件
        mConvertView.setOnClickListener(this);
        return viewHolder;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        viewHolder.mTextView.setText(datas.get(position));
        //设置值
        Log.i("datas.get(position).url",datas.get(position).url);
//        Glide.with(mContext).load(datas.get(position).url).into(viewHolder.ivGankImg);
        if(datas.get(position).desc != null){
            Log.i("datas.get(position).desc",datas.get(position).desc);
            String newstring = datas.get(position).desc;
            if (datas.get(position).desc.length() > 30) {
                newstring = datas.get(position).desc.substring(0, 30) + "···";
            }
            if(datas.get(position).who!=null){
                viewHolder.tvGankTitle.setText(newstring + "作者：" + datas.get(position).who);
            }else {
                viewHolder.tvGankTitle.setText(newstring + "作者：");
            }
        }

        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(datas.get(position));
//        gankdata = datas.get(position);
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvGankTitle;
        public ImageView ivGankImg;
        public ViewHolder(View view){
            super(view);
            tvGankTitle = (TextView) view.findViewById(R.id.tv_gank_title);
            RelativeLayout.LayoutParams Params = new RelativeLayout.LayoutParams(
                    MainActivity.width, MainActivity.height);
//            tvGankTitle.setLayoutParams(Params);
            ivGankImg = (ImageView) view.findViewById(R.id.iv_gank_img);
//            ivGankImg.setLayoutParams(Params);
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v,(GankEntity)v.getTag());
//            mOnItemClickListener.onItemClick(v, gankdata);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**item点击监听接口*/
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , GankEntity gankEntity);
    }

}
