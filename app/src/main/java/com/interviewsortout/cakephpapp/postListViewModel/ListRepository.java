package com.interviewsortout.cakephpapp.postListViewModel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.interviewsortout.cakephpapp.AppConstant;
import com.interviewsortout.cakephpapp.BuildConfig;
import com.interviewsortout.cakephpapp.model.PostListModel;
import com.interviewsortout.cakephpapp.network.ApiClient;
import com.interviewsortout.cakephpapp.network.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListRepository {
    private static final String TAG = "TodoRepository";
    private static  ListRepository ourInstance ;
    private MutableLiveData<List<PostListModel>> todoListLiveData = new MutableLiveData<>();

    private ListRepository() {
        //Nothing to do
    }

    public static synchronized ListRepository getInstance() {
        if(ourInstance == null){
            ourInstance = new ListRepository();
        }
        return ourInstance;
    }

    public LiveData<List<PostListModel>> getlistData() {
        ApiClient.getClient().create(ApiInterface.class).getPostListData(getID(), "list").enqueue(new Callback<List<PostListModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<PostListModel>> call, @NonNull Response<List<PostListModel>> response) {
                todoListLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<PostListModel>> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure: failed to fetch todo list from server");
            }
        });

        return todoListLiveData;
    }

    private int getID() {
        int value = 0;
        switch (BuildConfig.FLAVOR){
            case AppConstant.FLAVOR_IDS.CAKE_PHP:
                value = 29;
                break;
            case AppConstant.FLAVOR_IDS.PYTHON:
                value = 1284;
                break;
        }

        return value;
    }
}

