package com.example.newsapplication.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.newsapplication.data.models.NewsModel;

@Database(entities = {NewsModel.class},version = 1,exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    public abstract NewsDao newsDao();
}
