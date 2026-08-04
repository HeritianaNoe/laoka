package com.lakabe.laoka.gs.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.lakabe.laoka.gs.model.Fady;
import com.lakabe.laoka.gs.repository.FadyRepository;

import java.util.List;

public class FadyViewModel extends AndroidViewModel {
    private FadyRepository repository;

    public FadyViewModel(@NonNull Application application) {
        super(application);
        repository = new FadyRepository(application);
    }

    public void insert(Fady fady){
        repository.insert(fady);
    }

    public void update(Fady fady){
        repository.update(fady);
    }

    public void delete(Fady fady){
        repository.delete(fady);
    }

    public LiveData<List<Fady>> getAllFady() {
        return repository.getAllFady();
    }
}