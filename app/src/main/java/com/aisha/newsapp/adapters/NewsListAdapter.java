package com.aisha.newsapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aisha.newsapp.R;
import com.aisha.newsapp.models.Article;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class NewsListAdapter extends ArrayAdapter<Article> {
    Context context;
    public NewsListAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Article article = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_news_list, parent, false);
        }
        // Lookup view for data population
        TextView newsTitle = (TextView) convertView.findViewById(R.id.newsTitle);
        ImageView newsImage = (ImageView) convertView.findViewById(R.id.newsImage);

        // Populate the data into the template view using the data object
        newsTitle.setText(article.getTitle());
        Glide.with(context).load(article.getUrlToImage()).into(newsImage);
        // Return the completed view to render on screen
        return convertView;
    }

}
