package com.myexercuse.myexercise.mvp.ui.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.myexercuse.myexercise.R;
import com.myexercuse.myexercise.data.NewsData;
import com.myexercuse.myexercise.data.NewsDetailData;
import com.myexercuse.myexercise.mvp.presenter.NewsDetailPresenterImpl;
import com.myexercuse.myexercise.mvp.view.NewsDetailView;

import org.sufficientlysecure.htmltextview.HtmlTextView;


/**
 * Description : 新闻详情界面
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/19
 */
public class NewsDetailActivity extends AppCompatActivity implements NewsDetailView{

    private NewsData mNews;
    private HtmlTextView mTVNewsContent;
    private NewsDetailPresenterImpl mNewsDetailPresenterImpl;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.news_toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);
        mTVNewsContent = (HtmlTextView) findViewById(R.id.htNewsContent);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mNews = (NewsData) getIntent().getParcelableExtra("newsData");

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(mNews.title);

//        ImageLoaderUtils.display(getApplicationContext(), (ImageView) findViewById(R.id.ivImage), mNews.getImgsrc());
        Glide.with(getApplicationContext()).load(mNews.imgsrc).into((ImageView) findViewById(R.id.ivImage));

//        mNewsDetailPresenter = new NewsDetailPresenterImpl(getApplication(), this);
//        mNewsDetailPresenter.loadNewsDetail(mNews.docid);
        mNewsDetailPresenterImpl = new NewsDetailPresenterImpl(NewsDetailActivity.this);
        getNewsDetail();

    }

    @Override
    public void getNewsDetail() {
        mNewsDetailPresenterImpl.getDetailNews(mNews.docid);
    }

    @Override
    public void getNewsDetailSuccess(NewsDetailData newsDetailData) {
        mProgressBar.setVisibility(View.GONE);
        mTVNewsContent.setHtmlFromString(newsDetailData.body, new HtmlTextView.LocalImageGetter());
    }

    @Override
    public void getNewsDetailFailure() {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(NewsDetailActivity.this,"加载详情失败",Toast.LENGTH_LONG).show();
    }
}
