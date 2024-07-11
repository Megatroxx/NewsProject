package com.example.newsproject.newslist;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.security.crypto.EncryptedSharedPreferences;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.newsproject.R;
import com.example.newsproject.account.AccountFragment;
import com.example.newsproject.account.AccountViewModel;
import com.example.newsproject.account.AuthFragment;
import com.example.newsproject.database.MyEncryptedSharedPreferences;
import com.example.newsproject.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class AppActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    private AccountViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(AccountViewModel.class);


        MyEncryptedSharedPreferences.initialize(this);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        EncryptedSharedPreferences encryptedSharedPreferences = MyEncryptedSharedPreferences.getEncryptedSharedPreferences();




        viewModel.getLoginSuccess().observe(this, loginSuccess -> {
            if (loginSuccess) {
                setupAccountInfo(preferences);
            } else {
                Toast.makeText(this, "Неверный логин или пароль!", Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getLogoutSuccess().observe(this, logoutSuccess -> {
            if (logoutSuccess) {
                setupAccountInfo(preferences);
            }
        });

        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int itemId = item.getItemId();
                if (itemId == R.id.account) {
                    setupAccountInfo(preferences);
                }
                if (itemId == R.id.feed){
                    changeFragments(new FeedFragment());
                }
                item.setChecked(true);

                return false;
            }
        });



    }

    public void changeFragments(Fragment fragment) {
        androidx.fragment.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_place, fragment, null)
                .commit();
    }

    private void setupAccountInfo(SharedPreferences preferences) {
        Log.d(TAG, "isUserExist: " + preferences.getInt("isUserExist", 0));
        Log.d(TAG, "isUserLog: " + preferences.getInt("isUserLog", 0));
        Log.d(TAG, "LOGIN: " + preferences.getString("LOGIN", ""));
        if (preferences.getInt("isUserLog", 0) == 1) {
            changeFragments(new AccountFragment());
        } else {
            changeFragments(new AuthFragment());
        }
    }

}