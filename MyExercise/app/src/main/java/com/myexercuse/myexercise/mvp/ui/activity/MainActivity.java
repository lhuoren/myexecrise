package com.myexercuse.myexercise.mvp.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.util.DisplayMetrics;
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
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bumptech.glide.Glide;
import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.data.WeatherData;
import com.myexercuse.myexercise.mvp.presenter.WeatherPresenterImpl;
import com.myexercuse.myexercise.mvp.ui.Fragment.GankFragment;
import com.myexercuse.myexercise.mvp.ui.Fragment.MeizhiFragment;
import com.myexercuse.myexercise.mvp.ui.Fragment.NewsFragment;
import com.myexercuse.myexercise.mvp.ui.adapter.MenuItemAdapter;
import com.myexercuse.myexercise.mvp.view.WeatherView;
import com.myexercuse.myexercise.util.Commons;
import com.myexercuse.myexercise.util.LogUtil;
import com.myexercuse.myexercise.util.StringDealUtil;

public class MainActivity extends ToolbarActivity implements
        MeizhiFragment.hideWidget,
        View.OnClickListener,
        WeatherView {

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
    public static int width;
    public static int height;

    private WeatherPresenterImpl mWeatherPresenter;
    private TextView tvWeatherText;
    private TextView tvDateText;
    private TextView tvTemperatureText;
    private TextView tvWindText;
    private ImageView ivWeatherImg;
    private TextView tvAddressText;

    /**高德地图定位*/
    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();
    private String locationCity;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        mWeatherPresenter = new WeatherPresenterImpl(MainActivity.this);
        getPhonePix();
        initUI();
        initTab();
        initLocation();
        startLocation();
        setUpDrawer();
        getWeather();
        setLisetener();
        //初始化定位

    }

    private void setLisetener() {
//        mLvLeftMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                LogUtils.i("mLvLeftMenu","mLvLeftMenu");
//                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
//                startActivity(intent);
//
//            }
//        });


    }

    private void setUpDrawer() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View mView = inflater.inflate(R.layout.header_just_username, mLvLeftMenu, false);
        mLvLeftMenu.addHeaderView(mView);
        MenuItemAdapter menuItemAdapter = new MenuItemAdapter(this);
        mLvLeftMenu.setAdapter(menuItemAdapter);
//        menuItemAdapter.setOnItemClickListener(mOnItemClickListener);
        tvWeatherText = (TextView) mView.findViewById(R.id.tv_weather_text);
        tvDateText = (TextView) mView.findViewById(R.id.tv_date_text);
        tvTemperatureText = (TextView) mView.findViewById(R.id.tv_temperature_text);
        tvWindText = (TextView) mView.findViewById(R.id.tv_wind_text);
        ivWeatherImg = (ImageView) mView.findViewById(R.id.iv_weather_img);
        tvAddressText = (TextView) mView.findViewById(R.id.tv_address_text);

    }

//    MenuItemAdapter.OnItemClickListener mOnItemClickListener = new MenuItemAdapter
//            .OnItemClickListener() {
//        @Override
//        public void onItemClick(View view) {
//            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
//            startActivity(intent);
//        }
//    };

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
            gankImg.setImageResource(R.drawable.tab_center_gray);
            gankTv.setTextColor(getResources()
                    .getColor(R.color.titleColor));
            meizhiImg.setImageResource(R.drawable.tab_counter_light);
            meizhiTv.setTextColor(getResources().getColor(
                    R.color.titleColorSelected));
            meImg.setImageResource(R.drawable.tab_assistant_gray);
            meTv.setTextColor(getResources().getColor(R.color.titleColor));

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
        gankImg.setImageResource(R.drawable.tab_center_light);
        gankTv.setTextColor(getResources().getColor(R.color.titleColorSelected));
        meizhiImg.setImageResource(R.drawable.tab_counter_gray);
        meizhiTv.setTextColor(getResources().getColor(
                R.color.titleColor));
        meImg.setImageResource(R.drawable.tab_assistant_gray);
        meTv.setTextColor(getResources().getColor(R.color.titleColor));
    }

    /**
     * 点击第二个tab
     */
    private void clickTab2Layout() {
        if (meizhiFragment == null) {
            meizhiFragment = new MeizhiFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), meizhiFragment);

        gankImg.setImageResource(R.drawable.tab_center_gray);
        gankTv.setTextColor(getResources().getColor(R.color.titleColor));
        meizhiImg.setImageResource(R.drawable.tab_counter_light);
        meizhiTv.setTextColor(getResources().getColor(
                R.color.titleColorSelected));
        meImg.setImageResource(R.drawable.tab_assistant_gray);
        meTv.setTextColor(getResources().getColor(R.color.titleColor));

    }

    /**
     * 点击第三个tab
     */
    private void clickTab3Layout() {
        if (newsFragment == null) {
            newsFragment = new NewsFragment();
        }
        addOrShowFragment(getSupportFragmentManager().beginTransaction(), newsFragment);

        gankImg.setImageResource(R.drawable.tab_center_gray);
        gankTv.setTextColor(getResources().getColor(R.color.titleColor));
        meizhiImg.setImageResource(R.drawable.tab_counter_gray);
        meizhiTv.setTextColor(getResources().getColor(
                R.color.titleColor));
        meImg.setImageResource(R.drawable.tab_assistant_light);
        meTv.setTextColor(getResources().getColor(R.color.titleColorSelected));

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

    public void getPhonePix() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;
        height = metric.heightPixels * 1 / 13;
    }

    @Override
    public void getWeather() {
        LogUtil.i("getWeather","getWeather");
//        LogUtil.i("locationCity",locationCity);
        if(null==locationCity){
            locationCity = "深圳";
        }
        mWeatherPresenter.getWeather(locationCity, Commons.HEWEATHERKEY);
    }

    @Override
    public void getWeatherSuccess(WeatherData weatherData) {
        LogUtil.i("getWeatherSuccess","getWeatherSuccess");

        for(WeatherData.HeWeather5Bean heWeather5Bean:weatherData.HeWeather5){
            tvWeatherText.setText(heWeather5Bean.now.cond.txt);
            tvDateText.setText(StringDealUtil.cutTenString(heWeather5Bean.basic.update.utc));
            tvTemperatureText.setText(heWeather5Bean.now.tmp+"℃");
            tvWindText.setText(heWeather5Bean.now.wind.dir);
            tvAddressText.setText(heWeather5Bean.basic.city);
            Glide.with(MainActivity.this).load("http://files.heweather.com/cond_icon/"+heWeather5Bean.now.cond.code+".png").into(ivWeatherImg);
        }

    }

    @Override
    public void getWeatherFailure() {
        Toast.makeText(MainActivity.this,"获取天气失败",Toast.LENGTH_LONG).show();
    }

    /*************************高德定位相关***************************/
    @Override
    protected void onResume() {
        super.onResume();
    }
    /**
     * 初始化定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void initLocation(){
        //初始化client
        locationClient = new AMapLocationClient(this.getApplicationContext());
        //设置定位参数
        locationClient.setLocationOption(getDefaultOption());
        // 设置定位监听
        locationClient.setLocationListener(locationListener);
    }

    /**
     * 默认的定位参数
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private AMapLocationClientOption getDefaultOption(){
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
//        mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(60000);//可选，设置定位间隔。默认为2秒
//        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
//        mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
//        mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
//        AMapLocationClientOption.setLocationProtocol(AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
        return mOption;
    }

    /**
     * 定位监听
     */
    AMapLocationListener locationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation loc) {
            if (null != loc) {
                //解析定位结果
//                String result = GaoDeMapUtils.getLocationStr(loc);
                if(loc.getErrorCode()==0){
                    locationCity = loc.getCity();
                    LogUtil.i("locationCity",locationCity);
                }
                stopLocation();
//                tvResult.setText(result);
            } else {
//                tvResult.setText("定位失败，loc is null");
                Toast.makeText(MainActivity.this,"定位失败",Toast.LENGTH_SHORT);
                stopLocation();
            }
        }
    };

    /**
     * 开始定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void startLocation(){
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /**
     * 停止定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void stopLocation(){
        // 停止定位
        locationClient.stopLocation();
    }

    /**
     * 销毁定位
     *
     * @since 2.8.0
     * @author hongming.wang
     *
     */
    private void destroyLocation(){
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocation();
        destroyLocation();
    }

}
