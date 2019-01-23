package com.example.jjandroidchallenge.utils;

import com.example.jjandroidchallenge.repository.Repository;
import com.example.jjandroidchallenge.viewmodel.MainViewModelFactory;

public class Injector {
    public static Repository provideRepository(){
        return Repository.getInstance();
    }

    public static MainViewModelFactory provideMainActivityViewModelFactory() {
        Repository repository = provideRepository();
        return new MainViewModelFactory(repository);
    }
}
