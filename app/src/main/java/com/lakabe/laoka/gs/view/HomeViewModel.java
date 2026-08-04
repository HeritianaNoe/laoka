package com.lakabe.laoka.gs.view;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lakabe.laoka.gs.model.Category;
import com.lakabe.laoka.gs.model.Fady;
import com.lakabe.laoka.gs.model.Food;
import com.lakabe.laoka.gs.repository.CategoryRepository;
import com.lakabe.laoka.gs.repository.FadyRepository;
import com.lakabe.laoka.gs.repository.FoodRepository;

import java.util.List;

public class HomeViewModel extends AndroidViewModel {
    private FoodRepository repository1;
    private CategoryRepository repository2;
    private FadyRepository repository3;
    private LiveData<List<Category>> categories;
    private LiveData<List<Food>> foods;
    private LiveData<List<Fady>> fadies;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        repository1 = new FoodRepository(application);
        repository2 = new CategoryRepository(application);
        repository3 = new FadyRepository(application);

        categories = repository2.getAllCategory();
        foods = repository1.getAllFood();
        fadies = repository3.getAllFady();
    }

    public LiveData<List<Food>> getAllFood() {
        return foods;
    }

    public LiveData<List<Category>> getAllCategory() {
        return categories;
    }

    public LiveData<List<Fady>> getAllFady() {
        return fadies;
    }
}