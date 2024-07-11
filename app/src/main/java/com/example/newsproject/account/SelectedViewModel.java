package com.example.newsproject.account;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.security.crypto.EncryptedSharedPreferences;

import com.example.newsproject.database.AppDatabase;
import com.example.newsproject.database.DatabaseNews;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import kotlin.jvm.internal.Intrinsics;

public class SelectedViewModel extends ViewModel {

    private final MutableLiveData<List<DatabaseNews>> databaseNews = new MutableLiveData<>();

    public LiveData<List<DatabaseNews>> getDatabaseNews() {
        return databaseNews;
    }



    private final CompositeDisposable disposable = new CompositeDisposable();


    public void initialize(Context context) {
        disposable.add(
                Observable.fromCallable(() -> AppDatabase.getDatabase(context).newsDao().getAllNews())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                databaseNews::postValue,
                                Throwable::printStackTrace
                        )
        );
    }



    public void removeFav(@NotNull final Context context, @NotNull final String title) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(title, "title");

        Observable.fromCallable(() -> {
                    AppDatabase.getDatabase(context).newsDao().deleteNew(title);
                    return AppDatabase.getDatabase(context).newsDao().getAllNews();
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(databaseNews::setValue);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
