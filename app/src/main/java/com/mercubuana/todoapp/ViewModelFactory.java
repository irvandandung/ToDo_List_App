package com.mercubuana.todoapp;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.mercubuana.todoapp.viewmodels.ToDoInsertUpdateDeleteViewModel;
import com.mercubuana.todoapp.viewmodels.ToDoMainViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private static volatile ViewModelFactory INSTANCE;

    private final Application mApplication;

    private ViewModelFactory(Application application) {
        mApplication = application;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                INSTANCE = new ViewModelFactory(application);
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public  <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ToDoMainViewModel.class)) {
            return (T) new ToDoMainViewModel((mApplication));
        } else if (modelClass.isAssignableFrom(ToDoInsertUpdateDeleteViewModel.class)) {
            return (T) new ToDoInsertUpdateDeleteViewModel((mApplication));
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
