package com.example.newsproject.newslist;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsproject.R;
import com.example.newsproject.network.ApiService;
import com.example.newsproject.network.NewsRepository;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FeedFragment extends Fragment {

    private RecyclerView newsRecycler;
    private NewsListViewModel viewModel;
    private BottomNavigationView bottomNavigation;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        newsRecycler = view.findViewById(R.id.news);
        viewModel = new NewsListViewModel(new NewsRepository(ApiService.create()));
        viewModel.fetchNews();

        NewsAdapter adapter = new NewsAdapter(getContext(), viewModel);


        newsRecycler.setAdapter(adapter);
        newsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getNetworkNews().observe(getViewLifecycleOwner(), adapter::submitList);

    }
}