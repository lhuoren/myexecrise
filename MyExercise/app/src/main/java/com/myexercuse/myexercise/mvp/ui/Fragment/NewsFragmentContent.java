package com.myexercuse.myexercise.mvp.ui.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.data.NewsData;
import com.myexercuse.myexercise.mvp.presenter.FNContentPresenterImpl;
import com.myexercuse.myexercise.mvp.ui.Fragment.BaseFrament.LazyFragment;
import com.myexercuse.myexercise.mvp.ui.activity.NewsDetailActivity;
import com.myexercuse.myexercise.mvp.ui.adapter.NewsAdapter;
import com.myexercuse.myexercise.mvp.view.FNContentView;
import com.myexercuse.myexercise.util.Commons;
import com.myexercuse.myexercise.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsFragmentContent extends LazyFragment implements FNContentView{

    private LinearLayout rv_Notdata;
    private XRecyclerView newsRecyclerView;
    private List<NewsData> newsDataList;
    private NewsAdapter newsAdapter;

    private FNContentPresenterImpl fnContentPresenter;
    private int mType;
    private int mSize = 10;
    private int mPage = 0;
    private LinearLayoutManager layoutManager = null;

    public static Fragment newInstance(int type) {
        Bundle args = new Bundle();
        NewsFragmentContent fragment = new NewsFragmentContent();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt("type");
    }

    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.news_fragmnet_layout, container, false);
//    }


//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        fnContentPresenter = new FNContentPresenterImpl(NewsFragmentContent.this);
//        initView(view);
//        if(newsDataList == null) {
//            newsDataList = new ArrayList<>();
//        }
//        getNews();
//        newsAdapter = new NewsAdapter(getActivity(),newsDataList);
//        setListener();
//        newsRecyclerView.setAdapter(newsAdapter);
//        newsRecyclerView.setRefreshing(true);
//
//    }

    private void setListener() {
        newsRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.i("onRefresh","onRefresh");

                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mPage = 0;
                        if(newsDataList != null){
                            newsDataList.clear();
                        }
                        getNews();
                    }

                }, 1000);
            }

            @Override
            public void onLoadMore() {

                new Handler().postDelayed(new Runnable(){
                    public void run() {

                        mPage = mPage + Commons.PAZE_SIZE;
                        Log.i("mPage",mPage+"");
                        getNews();
                        newsAdapter.notifyDataSetChanged();
                        Log.i("onLoadMore1","onLoadMore1");
                        newsRecyclerView.loadMoreComplete();
                    }
                }, 1000);
            }
        });

        //xrecycleview item监听
        newsAdapter.setOnItemClickListener(new NewsAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view , NewsData newsData){
                Toast.makeText(getActivity(), "newsData", Toast.LENGTH_LONG).show();
                startWebActivity(newsData, view);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
//        if(!EventBus.getDefault().isRegistered(NewsFragmentContent.this)){
//            //注册EventBus
//            EventBus.getDefault().register(NewsFragmentContent.this);
//        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(NewsFragmentContent.this);//反注册EventBus
    }

    private void initView() {
//        rv_Notdata = (LinearLayout) view.findViewById(R.id.notdata);
        rv_Notdata = findView(R.id.notdata);

//        newsRecyclerView = (XRecyclerView)view.findViewById(R.id.rlv_news);
        newsRecyclerView = findView(R.id.rlv_news);
        if(layoutManager == null){
            layoutManager = new LinearLayoutManager(getActivity());
        }
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        newsRecyclerView.setLayoutManager(layoutManager);

        newsRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        newsRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        newsRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void startWebActivity(NewsData newsData, View transitView) {
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("newsData", newsData);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void getNews() {
        fnContentPresenter.getNews(mType, mPage);
    }

    @Override
    public void getNewsSuccess(List<NewsData> newsDatass) {
//        Toast.makeText(getActivity(),"成功",Toast.LENGTH_LONG).show();
        LogUtils.i("newsDatass",newsDatass+"");
        newsDataList.addAll(newsDatass);
        newsRecyclerView.refreshComplete();
//        //如果没有更多数据了,则隐藏footer布局
//        if(newsDataList == null || newsDataList.size() == 0) {
//            newsRecyclerView.setNoMore(true);
//        }
//        newsAdapter.notifyDataSetChanged();
        mPage += Commons.PAZE_SIZE;

    }

    @Override
    public void getNewsFailure() {
        Toast.makeText(getActivity(),"没有更多数据了",Toast.LENGTH_LONG).show();
        newsRecyclerView.setNoMore(true);
        newsAdapter.notifyDataSetChanged();
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_fragmnet_layout, container, false);
    }

    @Override
    protected void initData() {
        fnContentPresenter = new FNContentPresenterImpl(NewsFragmentContent.this);
        initView();
        if(newsDataList == null) {
            newsDataList = new ArrayList<>();
        }
        getNews();
        newsAdapter = new NewsAdapter(getActivity(),newsDataList);
        setListener();
        newsRecyclerView.setAdapter(newsAdapter);
        newsRecyclerView.setRefreshing(true);
    }

}
