package com.example.newsapplication.data.local;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.newsapplication.data.models.NewsModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface NewsDao {

    @Insert
    void putNews(ArrayList<NewsModel> newsModels);

    @Query("SELECT * FROM newsmodel")
    List<NewsModel> getAll();

    @Query("SELECT * FROM newsmodel")
    LiveData<List<NewsModel>> getAllLive();

    @Query("DELETE FROM newsmodel")
    void delete();

}
