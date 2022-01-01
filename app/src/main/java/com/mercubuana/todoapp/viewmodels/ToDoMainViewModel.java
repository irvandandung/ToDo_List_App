package com.mercubuana.todoapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.mercubuana.todoapp.entities.ToDo;
import com.mercubuana.todoapp.repositories.LocalToDoRepository;

import java.util.List;

public class ToDoMainViewModel extends ViewModel {
    private final LocalToDoRepository mLocalToDoRepository;

    public ToDoMainViewModel(Application application) {
        mLocalToDoRepository = new LocalToDoRepository(application);
    }

    public LiveData<List<ToDo>> getAllToDos() {
        return mLocalToDoRepository.getAllToDos();
    }
}
