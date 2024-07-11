package com.example.newsproject.account;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsproject.R;
import com.example.newsproject.databinding.FragmentSavedBinding;

public class SavedFragment extends Fragment {

    FragmentSavedBinding savedBinding;


    private SelectedViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        savedBinding = FragmentSavedBinding.inflate(inflater, container, false);
        return savedBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(SelectedViewModel.class);

        FavoriteAdapter adapter = new FavoriteAdapter(getContext(), viewModel);
        savedBinding.favoriteList.setAdapter(adapter);
        savedBinding.favoriteList.setLayoutManager(new LinearLayoutManager(getContext()));


        viewModel.initialize(getContext());

        viewModel.getDatabaseNews().observe((LifecycleOwner) getContext(), list -> {
            adapter.submitList(list);
        });
    }
}