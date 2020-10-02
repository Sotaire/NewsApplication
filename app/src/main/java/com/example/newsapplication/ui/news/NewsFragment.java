package com.example.newsapplication.ui.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsapplication.App;
import com.example.newsapplication.R;
import com.example.newsapplication.adapterNews.NewsAdapter;
import com.example.newsapplication.adapterNews.OnNewsClickListener;
import com.example.newsapplication.data.models.NewsModel;
import com.example.newsapplication.data.models.StatusModel;
import com.example.newsapplication.data.network.NewsClient;
import com.example.newsapplication.ui.detail.DetailFragment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsFragment extends Fragment implements OnNewsClickListener {
    RecyclerView recyclerView;
    ArrayList<NewsModel> models = new ArrayList<>();
    MutableLiveData<ArrayList<NewsModel>> data = new MutableLiveData<>();
    MutableLiveData<Boolean> isScrolled = new MutableLiveData<>();
    NewsAdapter newsAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    NestedScrollView nestedScrollView;
    ConnectivityManager connectivityManager;
    int page = 1;
    int pageSize = 10;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        fill();
        uploadPage();
        scroll();
    }

    private void fill() {
        if (connectivityManager.getActiveNetworkInfo()!= null && connectivityManager.getActiveNetworkInfo().isConnected()){
            getNews(page,pageSize);
            if (App.newsDatabase.newsDao().getAll() != null){
                App.newsDatabase.newsDao().delete();
            }
            data.observe(requireActivity(), new Observer<ArrayList<NewsModel>>() {
                @Override
                public void onChanged(ArrayList<NewsModel> newsModels) {
                    App.newsDatabase.newsDao().putNews(newsModels);
                    models.addAll(newsModels);
                    newsAdapter.setNewsModels(models);
                }
            });
        }else {
            App.newsDatabase.newsDao().getAllLive().observe(requireActivity(), new Observer<List<NewsModel>>() {
                @Override
                public void onChanged(List<NewsModel> newsModels) {
                    if (newsModels!= null){
                        models.addAll(newsModels);
                        newsAdapter.setNewsModels((ArrayList<NewsModel>) newsModels);
                    }
                }
            });
        }

    }

    private void scroll() {
        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()){
                        page++;
                        pageSize=+10;
                        getNews(page,pageSize);
                    }else{
                        Toast.makeText(requireActivity(), "Отсутствует интернет", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void uploadPage() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected()){
                    models.clear();
                    pageSize=10;
                    page =1 ;
                    getNews(page,pageSize);
                }
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void init(View view) {
        connectivityManager = (ConnectivityManager) requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        swipeRefreshLayout = view.findViewById(R.id.upload_news_refresh);
        nestedScrollView = view.findViewById(R.id.news_scroll_view);
        recyclerView = view.findViewById(R.id.news_recyclerview);
        newsAdapter = new NewsAdapter();
        newsAdapter.setHasStableIds(true);
        recyclerView.setAdapter(newsAdapter);
        newsAdapter.setOnNewsClickListener(this);
    }

    private void getNews(int page,int size) {
        NewsClient.getClient().getNews("8503b546e2dc480fb29d94f37886b62c","ru","business",page,size).enqueue(new Callback<StatusModel>() {
            @Override
            public void onResponse(Call<StatusModel> call, Response<StatusModel> response) {
                if (response.isSuccessful()){
                    data.setValue(response.body().getArticles());
                    isScrolled.setValue(true);
                }
            }

            @Override
            public void onFailure(Call<StatusModel> call, Throwable t) {
                Log.d("onFailure",t.getMessage());
            }
        });
    }

    @Override
    public void onClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString(DetailFragment.IMAGE_URL,models.get(position).getUrlToImage());
        bundle.putString(DetailFragment.TITLE,models.get(position).getTitle());
        bundle.putString(DetailFragment.AUTHOR,models.get(position).getAuthor());
        bundle.putString(DetailFragment.DESCRIPTION,models.get(position).getDescription());
        bundle.putString(DetailFragment.PUBLISHED_AT,models.get(position).getPublishedAt());
        bundle.putString(DetailFragment.URL,models.get(position).getUrl());
        NavController navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment);
        navController.navigate(R.id.action_newsFragment_to_detailFragment,bundle);
    }
}