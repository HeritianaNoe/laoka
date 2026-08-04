package com.lakabe.laoka.gs.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.lakabe.laoka.gs.database.LaokaDataBase;
import com.lakabe.laoka.gs.database.dao.FadyDao;
import com.lakabe.laoka.gs.model.Fady;
import com.lakabe.laoka.gs.model.Food;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class FadyRepository {
    private FadyDao fadyDao;
    private LiveData<List<Fady>> fadies;

    public FadyRepository(Application application) {
        LaokaDataBase dBase = LaokaDataBase.getInstance(application);
        fadyDao = dBase.fadyDao();

        fadies = fadyDao.getAllFady();
    }

    public void insert(Fady fady) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                fadyDao.insert(fady);
            }
        }).start();
    }

    public void update(Fady fady){
        new Thread(new Runnable() {
            @Override
            public void run() {
                fadyDao.update(fady);
            }
        }).start();
    }

    public void delete(Fady fady){
        new Thread(new Runnable() {
            @Override
            public void run() {
                fadyDao.delete(fady);
            }
        }).start();
    }

    public LiveData<List<Fady>> getAllFady() {
        return fadies;
    }
}
