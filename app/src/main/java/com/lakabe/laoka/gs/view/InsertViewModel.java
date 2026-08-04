package com.lakabe.laoka.gs.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lakabe.laoka.gs.model.Category;
import com.lakabe.laoka.gs.model.Food;
import com.lakabe.laoka.gs.repository.CategoryRepository;
import com.lakabe.laoka.gs.repository.FoodRepository;

import java.util.List;

public class InsertViewModel extends AndroidViewModel {
    private FoodRepository repository1;
    private CategoryRepository repository2;

    public InsertViewModel(@NonNull Application application) {
        super(application);
        repository1 = new FoodRepository(application);
        repository2 = new CategoryRepository(application);
    }

    public void insert(Food food){
        repository1.insert(food);
    }

    public LiveData<List<String>> getAllCategoryName() {
        return repository2.getAllCategoryName();
    }
}