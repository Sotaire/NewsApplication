package com.example.newsapplication;

import android.app.Application;

import androidx.room.Room;

import com.example.newsapplication.data.local.NewsDatabase;
import com.example.newsapplication.data.network.NewsClient;

public class App extends Application {

    public static NewsDatabase newsDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        newsDatabase = Room.databaseBuilder(this,NewsDatabase.class,"news_database")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
    }
}
