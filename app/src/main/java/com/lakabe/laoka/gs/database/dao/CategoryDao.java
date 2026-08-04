package com.lakabe.laoka.gs.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.lakabe.laoka.gs.model.Category;

import java.util.List;

@Dao
public interface CategoryDao {
    @Insert
    void insert(Category category);

    @Query("SELECT * FROM tabCategory")
    LiveData<List<Category>> getAllCategory();

    @Query("SELECT name FROM tabCategory")
    LiveData<List<String>> getAllCategoryName();
}
