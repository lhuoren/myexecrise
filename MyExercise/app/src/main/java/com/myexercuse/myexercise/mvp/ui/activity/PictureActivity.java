package com.myexercuse.myexercise.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.myexercuse.myexercise.R;

/**
 * Created by job on 2016/6/28.
 */
public class PictureActivity extends ToolbarActivity {

    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String TRANSIT_PIC = "picture";
    //toolbar
    private Toolbar toolbar;
    private ImageView iv_ShowMeizhi;
    private String mImageUrl;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_picture;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        initUI();
        Glide.with(PictureActivity.this).load(mImageUrl).into(iv_ShowMeizhi);
    }

    private void initUI() {
//        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
//        toolbar.setTitle("MyExercise");
//        setSupportActionBar(toolbar);
        iv_ShowMeizhi = (ImageView) findViewById(R.id.picture);
    }

}
