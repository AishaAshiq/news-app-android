package com.aisha.newsapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.newsList)
    ListView newsListView;

    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getNewsList();
        dl = (DrawerLayout)findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl,R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView)findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch(id)
                {
                    case R.id.account:
                        Toast.makeText(MainActivity.this, "My Account",Toast.LENGTH_SHORT).show();break;
                    case R.id.settings:
                        Toast.makeText(MainActivity.this, "Settings",Toast.LENGTH_SHORT).show();break;
                    case R.id.mycart:
                        Toast.makeText(MainActivity.this, "My Cart",Toast.LENGTH_SHORT).show();break;
                    default:
                        return true;
                }


                return true;

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
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

                        if (!TextUtils.isEmpty(response)) {
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

        if (newsResult != null && newsResult.getArticles() != null && !newsResult.getArticles().isEmpty()) {
            final ArrayList<Article> articleList = newsResult.getArticles();


            NewsListAdapter newsListAdapter = new NewsListAdapter(MainActivity.this, articleList);
            newsListView.setAdapter(newsListAdapter);


            newsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String newsContent = articleList.get(position).getContent();
                    String urlImage = articleList.get(position).getUrlToImage();

                    Intent intent = new Intent(MainActivity.this, NewsDetailsActivity.class);
                    intent.putExtra("newsContent", newsContent);
                    intent.putExtra("urlImage", urlImage);
                    startActivity(intent);
                }

            });


        } else {
            //TODO handle null response
        }


    }
}



