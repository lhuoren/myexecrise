package com.myexercuse.myexercise.mvp.ui.activity;

import android.os.Bundle;
import android.view.Window;

import com.myexercuse.myexercise.R;

/**
 * Created by job on 2016/8/3.
 */
public class AboutActivity extends ToolbarActivity{

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_about;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
