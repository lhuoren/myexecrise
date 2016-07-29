package com.myexercuse.myexercise.mvp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.mvp.ui.Fragment.GankFragment;
import com.myexercuse.myexercise.mvp.ui.Fragment.MeizhiFragment;
import com.myexercuse.myexercise.mvp.ui.Fragment.NewsFragment;
import com.myexercuse.myexercise.mvp.ui.adapter.MenuItemAdapter;

public class MainActivity extends ToolbarActivity implements MeizhiFragment.hideWidget, View.OnClickListener {

    //底部导航条
    private static LinearLayout ll_bottom_tab;
    // 三个tab布局
    private RelativeLayout gankLayout, meizhiLayout, meLayout;
    // 底部标签切换的Fragment
    private Fragment gankFragment, meizhiFragment, newsFragment,
            currentFragment;
    // 底部标签图片
    private ImageView gankImg, meizhiImg, meImg;
    // 底部标签的文本
    private TextView gankTv, meizhiTv, meTv;
    //toolbar
//    private static Toolbar toolbar;
    //左边侧滑
    private ListView mLvLeftMenu;
    private DrawerLayout mDrawerLayout;

    private Animation animation;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        initUI();
        initTab();
        setUpDrawer();
    }

    private void setUpDrawer() {
        LayoutInflater inflater = LayoutInflater.from(this);
        mLvLeftMenu.addHeaderView(inflater.inflate(R.layout.header_just_username, mLvLeftMenu, false));
        mLvLeftMenu.setAdapter(new MenuItemAdapter(this));
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        ll_bottom_tab = (LinearLayout) findViewById(R.id.ll_bottom_tab);
        gankLayout = (RelativeLayout) findViewById(R.id.rl_gank);
        meizhiLayout = (RelativeLayout) findViewById(R.id.rl_meizhi);
        meLayout = (RelativeLayout) findViewById(R.id.rl_me);
        gankLayout.setOnClickListener(this);
        meizhiLayout.setOnClickListener(this);
        meLayout.setOnClickListener(this);
        gankImg = (ImageView) findViewById(R.id.iv_gank);
        meizhiImg = (ImageView) findViewById(R.id.iv_meizhi);
        meImg = (ImageView) findViewById(R.id.iv_me);
        gankTv = (TextView) findViewById(R.id.tv_gank);
        meizhiTv = (TextView) findViewById(R.id.tv_meizhi);
        meTv = (TextView) findViewById(R.id.tv_me);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("MyExercise");
//        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mLvLeftMenu = (ListView) findViewById(R.id.id_lv_left_menu);
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        animation = new AlphaAnimation(0.1f, 1.0f);
        animation.setDuration(5000);
    }


    /**
     * 初始化底部标签
     */
    private void initTab() {
        if (meizhiFragment == null) {
            meizhiFragment = new MeizhiFragment();
        }

        if (!meizhiFragment.isAdded()) {
            // 提交事务
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.content_layout, meizhiFragment).commit();

            // 记录当前Fragment
            currentFragment = meizhiFragment;
            // 设置图片文本的变化
            gankImg.setImageResource(R.drawable.btn_know_nor);
            gankTv.setTextColor(getResources()
                    .getColor(R.color.bottomtab_normal));
            meizhiImg.setImageResource(R.drawable.btn_wantknow_pre);
            meizhiTv.setTextColor(getResources().getColor(
                    R.color.bottomtab_press));
            meImg.setImageResource(R.drawable.btn_my_nor);
            meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_gank: // 干货
                clickTab1Layout();
                break;
            case R.id.rl_meizhi: // 妹纸
                clickTab2Layout();
                break;
            case R.id.rl_me: // 我的
                clickTab3Layout();
                break;
            default:
                break;
        }
    }

    /**
     * 点击第一个tab
     */
    private void clickTab1Layout() {
        if (gankFragment == null) {
            gankFragment = new GankFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), gankFragment);

        // 设置底部tab变化
        gankImg.setImageResource(R.drawable.btn_know_pre);
        gankTv.setTextColor(getResources().getColor(R.color.bottomtab_press));
        meizhiImg.setImageResource(R.drawable.btn_wantknow_nor);
        meizhiTv.setTextColor(getResources().getColor(
                R.color.bottomtab_normal));
        meImg.setImageResource(R.drawable.btn_my_nor);
        meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
    }

    /**
     * 点击第二个tab
     */
    private void clickTab2Layout() {
        if (meizhiFragment == null) {
            meizhiFragment = new MeizhiFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), meizhiFragment);

        gankImg.setImageResource(R.drawable.btn_know_nor);
        gankTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        meizhiImg.setImageResource(R.drawable.btn_wantknow_pre);
        meizhiTv.setTextColor(getResources().getColor(
                R.color.bottomtab_press));
        meImg.setImageResource(R.drawable.btn_my_nor);
        meTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));

    }

    /**
     * 点击第三个tab
     */
    private void clickTab3Layout() {
        if (newsFragment == null) {
            newsFragment = new NewsFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), newsFragment);

        gankImg.setImageResource(R.drawable.btn_know_nor);
        gankTv.setTextColor(getResources().getColor(R.color.bottomtab_normal));
        meizhiImg.setImageResource(R.drawable.btn_wantknow_nor);
        meizhiTv.setTextColor(getResources().getColor(
                R.color.bottomtab_normal));
        meImg.setImageResource(R.drawable.btn_my_pre);
        meTv.setTextColor(getResources().getColor(R.color.bottomtab_press));

    }

    /**
     * 添加或者显示碎片
     *
     * @param transaction
     * @param fragment
     */
    private void addOrShowFragment(FragmentTransaction transaction,
                                   Fragment fragment) {
        if (currentFragment == fragment)
            return;

        if (!fragment.isAdded()) { // 如果当前fragment未被添加，则添加到Fragment管理器中
            transaction.hide(currentFragment)
                    .add(R.id.content_layout, fragment).commit();
        } else {
            transaction.hide(currentFragment).show(fragment).commit();
        }

        currentFragment = fragment;
    }

    /**
     * 设置菜单
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nav_list_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void hideToobar() {
//       toolbar.setAnimation(animation);
//        toolbar.setVisibility(View.GONE);
    }

    @Override
    public void hideBottomTab() {
        ll_bottom_tab.setAnimation(animation);
        ll_bottom_tab.setVisibility(View.GONE);
    }

}
