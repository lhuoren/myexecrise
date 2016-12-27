package com.myexercuse.myexercise.mvp.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.ImageView;

import com.bm.library.PhotoView;
import com.myexercuse.myexercise.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by job on 2016/6/28.
 */
public class PictureActivity extends ToolbarActivity {

    public static final String EXTRA_IMAGE_URL = "image_url";
    public static final String TRANSIT_PIC = "picture";
    //toolbar
    private Toolbar toolbar;
//    private ImageView ivShowMeizhi;
    private PhotoView pvShowMeizhi;
    private String mImageUrl;

    @Override
    protected int provideContentViewId() {
        return R.layout.activity_picture;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getIntent().getStringExtra(EXTRA_IMAGE_URL);
        Log.i("mImageUrl",mImageUrl);
        initUI();
//        Glide.with(PictureActivity.this).load(mImageUrl).into(iv_ShowMeizhi);

        Transformation transformation = new Transformation() {
            @Override
            public Bitmap transform(Bitmap source) {

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int targetWidth = displayMetrics.widthPixels;
                Log.i("ShowBigPhotoHolderView", "source.getHeight()=" + source.getHeight()
                        + ",source.getWidth()=" + source.getWidth() + ",targetWidth=" + targetWidth);

                if (source.getWidth() == 0) {
                    Log.i("ShowBigPhotoHolderView", "transform: width of source is 0");
                    return source;
                }

                double aspectRatio = (double) source.getHeight() / (double) source.getWidth();
                int targetHeight = (int) (targetWidth * aspectRatio);
                if (targetHeight != 0 && targetWidth != 0) {
                    Bitmap result = Bitmap.createScaledBitmap(source, targetWidth, targetHeight, true);
                    if (result != source) {
                        // Same bitmap is returned if sizes are the same
                        source.recycle();
                    }
                    return result;
                } else {
                    return source;
                }
            }

            @Override
            public String key() {
                return mImageUrl;
            }
        };

        Picasso.with(PictureActivity.this)
                .load(mImageUrl)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .config(Bitmap.Config.RGB_565)
                .transform(transformation)
                .into(pvShowMeizhi);

    }

    private void initUI() {
//        toolbar = (Toolbar) findViewById(R.id.id_toolbar);
//        toolbar.setTitle("MyExercise");
//        setSupportActionBar(toolbar);
//        iv_ShowMeizhi = (ImageView) findViewById(R.id.picture);
        pvShowMeizhi = (PhotoView) findViewById(R.id.pv_picture);
        pvShowMeizhi.enable();
        pvShowMeizhi.setMaxScale(3f);
        pvShowMeizhi.setScaleType(ImageView.ScaleType.CENTER);

//        pvShowMeizhi = new PhotoView(PictureActivity.this);
//        pvShowMeizhi.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT));
//        pvShowMeizhi.enable();
//        pvShowMeizhi.setMaxScale(3f);
//        pvShowMeizhi.setScaleType(ImageView.ScaleType.CENTER);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
