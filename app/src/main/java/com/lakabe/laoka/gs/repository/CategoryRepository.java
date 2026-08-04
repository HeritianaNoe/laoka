package com.lakabe.laoka.gs.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.lakabe.laoka.gs.database.LaokaDataBase;
import com.lakabe.laoka.gs.database.dao.CategoryDao;
import com.lakabe.laoka.gs.model.Category;

import java.util.List;

public class CategoryRepository {
    private CategoryDao categoryDao;
    private LiveData<List<Category>> categories;
    private LiveData<List<String>> categoryNames;

    public CategoryRepository(Application application) {
        LaokaDataBase dBase = LaokaDataBase.getInstance(application);
        categoryDao = dBase.categoryDao();

        categories = categoryDao.getAllCategory();
        categoryNames = categoryDao.getAllCategoryName();
    }

    public LiveData<List<Category>> getAllCategory() {
        return categories;
    }

    public LiveData<List<String>> getAllCategoryName() {
        return categoryNames;
    }
}
