package com.example.newsapplication.adapterNews;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.example.newsapplication.R;
import com.example.newsapplication.data.models.NewsModel;
import com.example.newsapplication.ui.news.NewsFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    ArrayList<NewsModel> newsModels = new ArrayList<>();
    OnNewsClickListener onNewsClickListener;

    public void setOnNewsClickListener(OnNewsClickListener onNewsClickListener) {
        this.onNewsClickListener = onNewsClickListener;
    }

    public void setNewsModels(ArrayList<NewsModel> newsModels) {
        this.newsModels = newsModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.news_view_holder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.onBind(newsModels.get(position).getTitle(),
                newsModels.get(position).getDescription(),
                newsModels.get(position).getUrlToImage(),
                newsModels.get(position).getPublishedAt());
    }

    @Override
    public int getItemCount() {
        return newsModels.size();
    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView title,description,published;
        ImageView image;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.news_title_tv);
            description = itemView.findViewById(R.id.news_description_tv);
            published = itemView.findViewById(R.id.published_at);
            image = itemView.findViewById(R.id.news_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onNewsClickListener.onClick(getAdapterPosition());
                }
            });
        }

        public void onBind(String title, String description, String url,String published){
            this.title.setText(title);
            this.description.setText(description);
            this.published.setText(getDate(published));
            Glide
                    .with((NewsFragment)onNewsClickListener)
                    .load(Uri.parse(url))
                    .transform(new RoundedCorners(16))
                    .into(image);
        }

        public String getDate(String date){
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MMM.dd", Locale.getDefault());
            String publishedAt = "";
            try {
                Date date1 = simpleDateFormat.parse(date);
                publishedAt =  simpleDateFormat.format(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return publishedAt;
        }

    }

}
