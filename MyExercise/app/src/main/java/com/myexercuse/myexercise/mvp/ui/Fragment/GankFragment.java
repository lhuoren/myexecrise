package com.myexercuse.myexercise.mvp.ui.Fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gxz.PagerSlidingTabStrip;
import com.myexercuse.myexercise.FirstEvent;
import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.mvp.ui.adapter.ViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class GankFragment extends Fragment {

	public static final int GANK_TYPE_ANDROID = 0;
	public static final int GANK_TYPE_IOS = 1;
	public static final int GANK_TYPE_VIDIO = 2;
	public static final int GANK_TYPE_RECOMMEND = 3;
	public static final int GANK_TYPE_EXPAND = 4;

	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private Fragment mFragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_tab1_fragment, container,
				false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		pager = (ViewPager) view.findViewById(R.id.pager);
		tabs = (PagerSlidingTabStrip) view.findViewById(R.id.tabs);
		pager.setOffscreenPageLimit(4);
		ArrayList<String> titles = new ArrayList<>();
		titles.add("安卓");
		titles.add("苹果");
		titles.add("视频");
		titles.add("推荐");
		titles.add("拓展");

		//Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
		ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
		adapter.addFragment(GankFragmentContent.newInstance(GANK_TYPE_ANDROID), "安卓");
		adapter.addFragment(GankFragmentContent.newInstance(GANK_TYPE_IOS), "苹果");
		adapter.addFragment(GankFragmentContent.newInstance(GANK_TYPE_VIDIO), "视频");
		adapter.addFragment(GankFragmentContent.newInstance(GANK_TYPE_RECOMMEND), "推荐");
		adapter.addFragment(GankFragmentContent.newInstance(GANK_TYPE_EXPAND), "拓展");
		pager.setAdapter(adapter);

		tabs.setViewPager(pager);
		pager.setCurrentItem(0);

		pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {

				Log.i("position","position");
				EventBus.getDefault().post(
						new FirstEvent(String.valueOf(position)));
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			Field childFragmentManager = Fragment.class
					.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		} catch (NoSuchFieldException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onDestroyView() {
		
		super.onDestroyView();

		if(mFragment != null){
			FragmentManager f = getFragmentManager();
			if(f != null && !f.isDestroyed()){
				final FragmentTransaction ft = f.beginTransaction();
				if(ft != null){
					ft.remove(mFragment).commit();
				}
			}
		}

	}


}
