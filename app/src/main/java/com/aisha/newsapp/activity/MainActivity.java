package com.aisha.newsapp.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ListView;

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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.newsList) ListView newsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getNewsList();
    }

    private void getNewsList() {

        //TODO show progress
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getString(R.string.news_api_url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // success response
                        Log.i("NewsApp", response);

                        if (!TextUtils.isEmpty(response)){
                            parseNewsResponse(response);

                        } else {
                            //TODO  handle error response
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error response
                        Log.i("NewsApp", error.getMessage());
                        //TODO  handle error response
                    }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void parseNewsResponse(String response) {
        Gson gson = new Gson();
        NewsResult newsResult = gson.fromJson(response, NewsResult.class);

        if(newsResult != null && newsResult.getArticles() != null && !newsResult.getArticles().isEmpty()){
            ArrayList<Article> articleList = newsResult.getArticles();

            NewsListAdapter newsListAdapter = new NewsListAdapter(MainActivity.this, articleList);
            newsListView.setAdapter(newsListAdapter);


        } else {
            //TODO handle null response
        }


    }


}

