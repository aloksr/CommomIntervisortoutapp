package com.interviewsortout.cakephpapp.postListViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.interviewsortout.cakephpapp.model.PostListModel;

import java.util.List;

public class ListViewModel extends ViewModel {

    private static final String TAG = "ListViewModel";
    private ListRepository repository = ListRepository.getInstance();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private LiveData<List<PostListModel>> todoLiveData;

    public ListViewModel() {
        super();
        isLoading.setValue(true);
        todoLiveData = repository.getlistData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<PostListModel>> getListDataValue() {
        return todoLiveData;
    }
}


