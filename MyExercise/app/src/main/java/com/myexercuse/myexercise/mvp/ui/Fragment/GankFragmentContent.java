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
import com.myexercuse.myexercise.FirstEvent;
import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.data.GanksData;
import com.myexercuse.myexercise.data.entity.GankEntity;
import com.myexercuse.myexercise.mvp.presenter.FGContentPresenterImpl;
import com.myexercuse.myexercise.mvp.ui.Fragment.BaseFrament.LazyFragment;
import com.myexercuse.myexercise.mvp.ui.activity.WebActivity;
import com.myexercuse.myexercise.mvp.ui.adapter.MyAdapter;
import com.myexercuse.myexercise.mvp.view.FGContentView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class GankFragmentContent extends LazyFragment implements FGContentView {

    private LinearLayout rv_Notdata;
    private List<GankEntity> ganklist;
    private int mType;
    private int mSize = 10;
    private int mPage = 1;
    private FGContentPresenterImpl fgContentPresenter;
    //是否正在加载更多的标志
    private boolean isMoreLoading = false;
    private int lastVisibleItem;

    private XRecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private int refreshTime = 0;
    private int times = 0;

    public static Fragment newInstance(int type) {
        Bundle args = new Bundle();
        GankFragmentContent fragment = new GankFragmentContent();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("ONCREATE","onCreate");
        mType = getArguments().getInt("type");
    }

    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        Log.i("ONCREATEVIEW","onCreateView");
//        return inflater.inflate(R.layout.gank_fragmnet_layout, container, false);
//    }


//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }


    private void setListener() {
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                Log.i("onRefresh","onRefresh");
//                times = 0;


                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        mPage = 1;
                        if(ganklist != null){
                            ganklist.clear();
                        }
                        getGanks();
//                        mRecyclerView.refreshComplete();
//                        mAdapter.notifyDataSetChanged();
                    }

                }, 1000);
            }

            @Override
            public void onLoadMore() {

                new Handler().postDelayed(new Runnable(){
                        public void run() {
                            if(mPage>1){
                                Log.i("mPage",mPage+"");
                                getGanks();
                                mAdapter.notifyDataSetChanged();
                            }
                            Log.i("onLoadMore1","onLoadMore1");
                            mRecyclerView.loadMoreComplete();
                        }
                    }, 1000);
                mPage++;
            }
        });


        //xrecycleview item监听
        mAdapter.setOnItemClickListener(new MyAdapter.OnRecyclerViewItemClickListener(){
            @Override
            public void onItemClick(View view , GankEntity gankEntity){
                Toast.makeText(getActivity(), "gankEntity", Toast.LENGTH_LONG).show();
                startWebActivity(gankEntity, view);
            }
        });

        rv_Notdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGanks();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if(!EventBus.getDefault().isRegistered(GankFragmentContent.this)){
            //注册EventBus
            EventBus.getDefault().register(GankFragmentContent.this);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(GankFragmentContent.this);//反注册EventBus
    }

    private void initView() {
//        rv_Notdata = (LinearLayout) view.findViewById(R.id.notdata);
        rv_Notdata = findView(R.id.notdata);

//        mRecyclerView = (XRecyclerView)view.findViewById(R.id.rlv_gank);
        mRecyclerView = findView(R.id.rlv_gank);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);

        //设置header
//        View header = LayoutInflater.from(getActivity()).inflate(R.layout.recyclerview_header, (ViewGroup)view.findViewById(android.R.id.content),false);
//        mRecyclerView.addHeaderView(header);
    }

    @Override
    public void getGanks() {
        fgContentPresenter.getGanks(mType, mSize, mPage);
    }

    @Override
    public void getGanksSuccess(GanksData ganksData) {
        if(ganksData != null){
            ganklist.addAll(ganksData.results);
            Log.i("androidList", ganklist.toString());
            mRecyclerView.refreshComplete();
            mAdapter.notifyDataSetChanged();
        }else {
            Toast.makeText(getActivity(),"没有更多数据了",Toast.LENGTH_LONG).show();
            mRecyclerView.setNoMore(true);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public void getGanksFailure() {
        Toast.makeText(getActivity(),"失败",Toast.LENGTH_LONG).show();
        rv_Notdata.setVisibility(View.VISIBLE);
    }

    /**Eventbus接受器*/
    @Subscribe
    public void onEventMainThread(FirstEvent event) {

        String msg = "onEventMainThread收到了消息：" + event.getMsg();
        Log.d("harvic", msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(ganklist != null){
            ganklist.clear();
        }
    }


    private void startWebActivity(GankEntity GankEntity, View transitView) {
        Intent intent = new Intent(getActivity(), WebActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("GankEntity", GankEntity);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gank_fragmnet_layout, container, false);
    }

    @Override
    protected void initData() {
        initView();
        ganklist = new ArrayList<>();
        fgContentPresenter = new FGContentPresenterImpl(GankFragmentContent.this);
        mAdapter = new MyAdapter(ganklist);
        mRecyclerView.setAdapter(mAdapter);
        getGanks();
        setListener();
        mRecyclerView.setRefreshing(true);

    }

}
