package com.example.newsproject.account;

import static android.content.ContentValues.TAG;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.security.crypto.EncryptedSharedPreferences;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsproject.R;
import com.example.newsproject.database.MyEncryptedSharedPreferences;
import com.example.newsproject.databinding.FragmentAccountBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class AccountFragment extends Fragment {

    private BottomNavigationView bottomNavigation;

    private FragmentAccountBinding binding;

    private AccountViewModel viewModel;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        EncryptedSharedPreferences encryptedSharedPreferences = MyEncryptedSharedPreferences.getEncryptedSharedPreferences();


        binding.exitButton.setOnClickListener(v -> {
            viewModel.logout(preferences);
        });

        binding.loginTextAccount.setText(preferences.getString("LOGIN", ""));

        binding.savedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction fragmentTransaction = getActivity()
                        .getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragment_place, new SavedFragment()).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }

}