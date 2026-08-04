package com.lakabe.laoka.gs.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.lakabe.laoka.gs.model.Fady;

import java.util.List;

@Dao
public interface FadyDao {
    @Insert
    void insert(Fady fady);

    @Update
    void update(Fady fady);

    @Delete
    void delete(Fady fady);

    @Query("SELECT * FROM tabFady")
    LiveData<List<Fady>> getAllFady();
}
