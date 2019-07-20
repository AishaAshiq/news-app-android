package com.aisha.newsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.aisha.newsapp.R;
import com.aisha.newsapp.adapters.NewsListAdapter;
import com.aisha.newsapp.models.Article;
import com.aisha.newsapp.models.NewsResult;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailsActivity extends AppCompatActivity {

    @BindView(R.id.newsDetailsImage) ImageView newsDetailsImageView;
    @BindView(R.id.newsContent) TextView newsContentTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        String newsContent = bundle.getString("newsContent");
        String urlImage = bundle.getString("urlImage");

        newsContentTextView.setText(newsContent);
        Glide.with(newsDetailsImageView).load(urlImage).into(newsDetailsImageView);
     }



}
