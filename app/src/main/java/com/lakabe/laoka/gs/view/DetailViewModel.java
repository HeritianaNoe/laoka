package com.lakabe.laoka.gs.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lakabe.laoka.gs.model.Food;
import com.lakabe.laoka.gs.repository.FoodRepository;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {
    private FoodRepository repository;

    public DetailViewModel(@NonNull Application application) {
        super(application);
        repository = new FoodRepository(application);
    }

    public void update(Food food){
        repository.update(food);
    }

    public LiveData<Food> getFood(int id) {
        return repository.getFood(id);
    }
}
