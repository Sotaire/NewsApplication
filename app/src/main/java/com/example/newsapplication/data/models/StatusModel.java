package com.example.newsapplication.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class StatusModel {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("totalResults")
        @Expose
        private Integer totalResults;
        @SerializedName("articles")
        @Expose
        private ArrayList<NewsModel> articles;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Integer getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(Integer totalResults) {
            this.totalResults = totalResults;
        }

        public ArrayList<NewsModel> getArticles() {
            return articles;
        }

        public void setArticles(ArrayList<NewsModel> articles) {
            this.articles = articles;
        }
}
