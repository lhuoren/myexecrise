package com.myexercuse.myexercise.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.data.NewsData;

import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements View.OnClickListener{
    public List<NewsData> newslists = null;
    private Context mContext;
    private NewsData newsData;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public NewsAdapter(Context context, List<NewsData> datas) {
        this.newslists = datas;
        this.mContext = context;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View mConvertView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_news,viewGroup,false);
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
        Glide.with(mContext).load(newslists.get(position).imgsrc).into(viewHolder.ivNews);
        viewHolder.tvTitle.setText(newslists.get(position).title);
        viewHolder.tvDesc.setText(newslists.get(position).digest);
        //将数据保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(newslists.get(position));

    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return newslists.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivNews;
        public TextView tvTitle;
        public TextView tvDesc;
        public ViewHolder(View view){
            super(view);
            ivNews = (ImageView) view.findViewById(R.id.ivNews);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDesc = (TextView) view.findViewById(R.id.tvDesc);
        }
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
//            mOnItemClickListener.onItemClick(v,(GankEntity)v.getTag());
            mOnItemClickListener.onItemClick(view, (NewsData) view.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    /**向外暴露item点击监听接口*/
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, NewsData newsData);
    }
}
