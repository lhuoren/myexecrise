package com.myexercuse.myexercise.mvp.ui.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.data.MeizhiData;
import com.myexercuse.myexercise.mvp.presenter.MeizhiPreserter;
import com.myexercuse.myexercise.mvp.presenter.MeizhiPreserterImpl;
import com.myexercuse.myexercise.mvp.ui.Fragment.BaseFrament.BaseFragment;
import com.myexercuse.myexercise.mvp.ui.activity.MainActivity;
import com.myexercuse.myexercise.mvp.ui.activity.PictureActivity;
import com.myexercuse.myexercise.mvp.ui.adapter.MeizhiListAdapter;
import com.myexercuse.myexercise.mvp.ui.adapter.recyclerview.OnItemClickListener;
import com.myexercuse.myexercise.mvp.view.MeizhiView;

import java.util.ArrayList;
import java.util.List;

/**
 * 妹纸碎片页面
 * @author wwj_748
 *
 */
public class MeizhiFragment extends BaseFragment implements MeizhiView, SwipeRefreshLayout.OnRefreshListener, OnItemClickListener<MeizhiData.ResultsBean>{

	//妹纸瀑布流列表
	private RecyclerView meizhiRecylerView;
	private MeizhiListAdapter mMeizhiListAdapter;
	private List<MeizhiData.ResultsBean> mMeizhiList;
	private int mPage = 1;
	private int meizhiSize = 10;

	private SwipeRefreshLayout swipeRefreshLayout;
	private static final int PRELOAD_SIZE = 6;
	private boolean mIsFirstTimeTouchBottom = true;
	private boolean isrefreshing = true;

	private LinearLayout rv_Notdata;
	private View view;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.main_tab2_fragment, container,
				false);
		Log.i("onCreateView1","onCreateView1");
		InitUI();
		mMeizhiList = new ArrayList<>();
		GetMeizhis();
		mMeizhiListAdapter = new MeizhiListAdapter(getContext(), mMeizhiList);
		meizhiRecylerView.setAdapter(mMeizhiListAdapter);
		mMeizhiListAdapter.setOnItemClickListener(this);
		swipeRefreshLayout.setOnRefreshListener(this);
		return view;
	}

	private void InitUI() {

		swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
		meizhiRecylerView = (RecyclerView) view.findViewById(R.id.rv_meizhi);
		final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,
				StaggeredGridLayoutManager.VERTICAL);
		meizhiRecylerView.setLayoutManager(layoutManager);
		meizhiRecylerView.addOnScrollListener(getOnBottomListener(layoutManager));
		swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
		swipeRefreshLayout.setEnabled(true);

		rv_Notdata = (LinearLayout) view.findViewById(R.id.notdata);

	}

	RecyclerView.OnScrollListener getOnBottomListener(final StaggeredGridLayoutManager layoutManager) {
		return new RecyclerView.OnScrollListener() {
			@Override public void onScrolled(RecyclerView rv, int dx, int dy) {
				boolean isBottom =
							layoutManager.findLastCompletelyVisibleItemPositions(new int[2])[1] >=
									mMeizhiListAdapter.getItemCount() - PRELOAD_SIZE;
					if (!swipeRefreshLayout.isRefreshing() && isBottom) {
						if (!mIsFirstTimeTouchBottom) {
							swipeRefreshLayout.setRefreshing(true);
							mPage += 1;
//							loadData(mPage);
							GetMeizhis();
						} else {
							mIsFirstTimeTouchBottom = false;
						}
				}
				MainActivity mainActivity = new MainActivity();
				if(dy < 0){  //向上滑

				}else if(dy > 0){  //向下滑

				}
			}
		};
	}

	@Override
	public void onRefresh() {
		mPage = 1;
		if(mMeizhiList != null){
			mMeizhiList.clear();
		}
//		loadData(mPage);
		GetMeizhis();
	}

	@Override
	public void GetMeizhis() {
		MeizhiPreserter meizhiPreserter = new MeizhiPreserterImpl(MeizhiFragment.this);
		meizhiPreserter.GetMeizhis(meizhiSize,mPage);
	}

	@Override
	public void GetMeizhiFailure() {
		swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getActivity(), R.string.loading_failed, Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setVisibility(View.GONE);
        rv_Notdata.setVisibility(View.VISIBLE);
        rv_Notdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				onRefresh();
               }
		});
	}

	@Override
	public void GetMeizhiSuccess(MeizhiData meizhiDatas) {
		swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setVisibility(View.VISIBLE);
        rv_Notdata.setVisibility(View.GONE);
        Log.i("meizhiDatas",meizhiDatas.toString());
             for(int i = 0; i < meizhiDatas.getResults().size(); i++){
                   mMeizhiList.add(meizhiDatas.getResults().get(i));
                   }
        mMeizhiListAdapter.notifyDataSetChanged();
	}


	@Override
	public void onItemClick(ViewGroup parent, View meizhiView, MeizhiData.ResultsBean resultsBean, int position) {
		Log.i("onItemClick","onItemClick");
		startPictureActivity(resultsBean, meizhiView);
	}

	@Override
	public boolean onItemLongClick(ViewGroup parent, View meizhiView, MeizhiData.ResultsBean resultsBean, int position) {
		return false;
	}

	private void startPictureActivity(MeizhiData.ResultsBean meizhi, View transitView) {
//		Intent intent = PictureActivity.newIntent(getActivity(), meizhi.getUrl());
		Intent intent =new Intent(getActivity(), PictureActivity.class);
        intent.putExtra(PictureActivity.EXTRA_IMAGE_URL,meizhi.getUrl());
		ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
				getActivity(), transitView, PictureActivity.TRANSIT_PIC);
		try {
			ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			startActivity(intent);
		}
	}

	public interface hideWidget{
        void hideToobar();
		void hideBottomTab();
	}
}
