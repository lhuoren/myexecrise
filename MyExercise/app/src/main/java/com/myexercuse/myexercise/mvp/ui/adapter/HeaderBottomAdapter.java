package com.myexercuse.myexercise.mvp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.data.entity.GankEntity;

import java.util.List;

public class HeaderBottomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;

    private View headerView;
    private View footerView;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int mHeaderCount=1;//头部View个数
    private int mBottomCount=1;//底部View个数
    private List<GankEntity> gankList;
    public HeaderBottomAdapter(Context context, List<GankEntity> datas) {
        this.mContext = context;
        this.gankList = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }
    //内容长度
    public int getContentItemCount(){
        return gankList.size() - 1;
    }
    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }
    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
            //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
            //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
            //内容View
            return ITEM_TYPE_CONTENT;
        }
    }
    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_gank;
        public ContentViewHolder(View itemView) {
            super(itemView);
            tv_gank=(TextView)itemView.findViewById(R.id.tv_gank_title);
        }
    }
    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }
    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType ==ITEM_TYPE_HEADER) {
             headerView = mLayoutInflater.inflate(R.layout.rv_header, parent, false);
            return new HeaderViewHolder(headerView);
        } else if (viewType == mHeaderCount) {
            return new ContentViewHolder(mLayoutInflater.inflate(R.layout.item_gank, parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            footerView = mLayoutInflater.inflate(R.layout.rv_footer, parent, false);
            return new BottomViewHolder(footerView);
        }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {
        } else if (holder instanceof ContentViewHolder) {
//            ((ContentViewHolder) holder).tv_gank.setText(texts[position - mHeaderCount]);
            //设置值
            if(gankList.get(position).desc != null){
                if(gankList.get(position).who!=null){
                    ((ContentViewHolder) holder).tv_gank.setText(gankList.get(position).desc + "。作者：" + gankList.get(position).who);
                }else {
                    ((ContentViewHolder) holder).tv_gank.setText(gankList.get(position).desc + "。作者：");
                }
            }
        } else if (holder instanceof BottomViewHolder) {
        }
    }
    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }

    //显示HeadView
    public void showHeaderView(){
            headerView.setVisibility(View.VISIBLE);
    }
    //隐藏HeadView
    public void hideHeaderView(){
             headerView.setVisibility(View.GONE);
    }
    //显示FooterView
    public void showFooterView(){
             footerView.setVisibility(View.VISIBLE);
    }
    //隐藏FooterView
    public void hideFooterView(){
            footerView.setVisibility(View.GONE);
    }
}
