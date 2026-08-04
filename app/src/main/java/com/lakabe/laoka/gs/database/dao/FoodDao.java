package com.lakabe.laoka.gs.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.lakabe.laoka.gs.model.Category;
import com.lakabe.laoka.gs.model.Food;

import java.util.List;

@Dao
public interface FoodDao {
    @Insert
    void insert(Food food);

    @Update
    void update(Food food);

    @Delete
    void delete(Food food);

    @Query("SELECT * FROM tabFood")
    LiveData<List<Food>> getAllFood();

    @Query("SELECT * FROM tabFood WHERE name NOT IN (:fadies)")
    LiveData<List<Food>> getAllFoodNotFady(List<String> fadies);

    @Query("SELECT * FROM tabFood WHERE categoryId = :catId")
    LiveData<List<Food>> getAllFoodWithCategory(int catId);

    @Query("SELECT * FROM tabFood WHERE heart = 1 ORDER BY rating DESC")
    LiveData<List<Food>> getAllFoodWithHeart();

    @Query("SELECT * FROM tabFood WHERE name LIKE '%' || :search || '%' ORDER BY id ASC")
    LiveData<List<Food>> getAllFoodWithSearch(String search);

    @Query("SELECT * FROM tabFood WHERE id = :id LIMIT 1")
    LiveData<Food> getFood(int id);
}
