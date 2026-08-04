package com.lakabe.laoka.gs.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.lakabe.laoka.gs.database.LaokaDataBase;
import com.lakabe.laoka.gs.database.dao.FoodDao;
import com.lakabe.laoka.gs.model.Food;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FoodRepository {
    private FoodDao foodDao;
    private LiveData<List<Food>> foods;
    private LiveData<List<Food>> hearts;

    public FoodRepository(Application application) {
        LaokaDataBase dBase = LaokaDataBase.getInstance(application);
        foodDao = dBase.foodDao();

        foods = foodDao.getAllFood();
        hearts = foodDao.getAllFoodWithHeart();
    }

    public void insert(Food food) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                foodDao.insert(food);
            }
        }).start();
    }

    public void update(Food food){
        new Thread(new Runnable() {
            @Override
            public void run() {
                foodDao.update(food);
            }
        }).start();
    }

    public void delete(Food food){
        new Thread(new Runnable() {
            @Override
            public void run() {
                foodDao.delete(food);
            }
        }).start();
    }

    public LiveData<List<Food>> getAllFood() {
        return foods;
    }

    public LiveData<List<Food>> getAllFoodWithCategory(int catId) {
        return foodDao.getAllFoodWithCategory(catId);
    }

    public LiveData<List<Food>> getAllFoodWithSearch(String search) {
        Future<LiveData<List<Food>>> future = LaokaDataBase.dbExecutor.submit(() -> foodDao.getAllFoodWithSearch(search));
        try {
            return future.get(); // wait for the result
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
            return null;//Collections.emptyList();
        }
        //return foodDao.getAllFoodWithSearch(search);
    }

    public LiveData<List<Food>> getAllFoodWithHeart() {
        return hearts;
    }

    public LiveData<Food> getFood(int id){
        return foodDao.getFood(id);
    }
}
