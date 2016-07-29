package com.myexercuse.myexercise.mvp.ui.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.mvp.ui.adapter.ViewPagerAdapter;

import java.lang.reflect.Field;

public class NewsFragment extends Fragment {
	public static final int NEWS_TYPE_HEADLINE = 0;
	public static final int NEWS_TYPE_NBA = 1;
	public static final int NEWS_TYPE_CAR = 2;
	public static final int NEWS_TYPE_JOKE = 3;

	private TabLayout newstabs;
	private ViewPager newspager;
	private Fragment newsFragment;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.main_tab3_fragment, container, false);
		return view;
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		newspager = (ViewPager) view.findViewById(R.id.news_pager);
		newstabs = (TabLayout) view.findViewById(R.id.news_tabs);
		newspager.setOffscreenPageLimit(3);
		newstabs.addTab(newstabs.newTab().setText(R.string.top));
		newstabs.addTab(newstabs.newTab().setText(R.string.nba));
		newstabs.addTab(newstabs.newTab().setText(R.string.cars));
		newstabs.addTab(newstabs.newTab().setText(R.string.jokes));
		newstabs.setupWithViewPager(newspager);

		//Fragment中嵌套使用Fragment一定要使用getChildFragmentManager(),否则会有问题
		ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
		adapter.addFragment(NewsFragmentContent.newInstance(NEWS_TYPE_HEADLINE), getString(R.string.top));
		adapter.addFragment(NewsFragmentContent.newInstance(NEWS_TYPE_NBA), getString(R.string.nba));
		adapter.addFragment(NewsFragmentContent.newInstance(NEWS_TYPE_CAR), getString(R.string.cars));
		adapter.addFragment(NewsFragmentContent.newInstance(NEWS_TYPE_JOKE), getString(R.string.jokes));
		newspager.setAdapter(adapter);

		newspager.setCurrentItem(0);
		newspager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {

//				Log.i("position","position");
//				EventBus.getDefault().post(
//						new FirstEvent(String.valueOf(position)));
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

		if(newsFragment != null){
			FragmentManager f = getFragmentManager();
			if(f != null && !f.isDestroyed()){
				final FragmentTransaction ft = f.beginTransaction();
				if(ft != null){
					ft.remove(newsFragment).commit();
				}
			}
		}

	}
}
