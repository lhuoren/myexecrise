package com.myexercuse.myexercise.mvp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;

import com.myexercuse.myexercise.R;

public abstract class ToolbarActivity extends BaseActivity {

    abstract protected int provideContentViewId();
    private Toolbar toolbar;

    public void onToolbarClick() {}


    protected AppBarLayout mAppBar;
    protected Toolbar mToolbar;
    protected boolean mIsHidden = false;


    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideContentViewId());
        initView();
    }

    private void initView() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("MyExercise");
        setSupportActionBar(toolbar);
    }

}
