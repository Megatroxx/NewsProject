package com.example.newsproject.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.security.crypto.EncryptedSharedPreferences;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newsproject.R;
import com.example.newsproject.database.MyEncryptedSharedPreferences;
import com.example.newsproject.databinding.FragmentAuthBinding;

public class AuthFragment extends Fragment {

    private AccountViewModel viewModel;

    FragmentAuthBinding binding;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthBinding.inflate(inflater, container, false);
        return binding.getRoot();


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(AccountViewModel.class);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        EncryptedSharedPreferences encryptedSharedPreferences = MyEncryptedSharedPreferences.getEncryptedSharedPreferences();

        binding.enterButton.setOnClickListener(v -> {
            viewModel.handleEnterButtonClick(
                    preferences,
                    encryptedSharedPreferences,
                    binding.loginText.getText().toString(),
                    binding.passwordText.getText().toString()
            );
        });
    }
}