package com.lakabe.laoka.gs.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lakabe.laoka.gs.model.Food;
import com.lakabe.laoka.gs.repository.FoodRepository;

import java.util.List;

public class SearchViewModel extends AndroidViewModel {
    private FoodRepository repository;
    private MutableLiveData<String> textSearch;

    public SearchViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodRepository(application);
        textSearch = new MutableLiveData<>();
    }

    public LiveData<List<Food>> getAllFoodWithSearch(String search) {
        return repository.getAllFoodWithSearch(search);
    }

    public LiveData<String> getTextSearch(){
        return textSearch;
    }

    public void setTextSearch(String text){
        textSearch.setValue(text);
    }
}
