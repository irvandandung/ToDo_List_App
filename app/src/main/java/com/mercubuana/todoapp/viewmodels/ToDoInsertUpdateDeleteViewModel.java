package com.mercubuana.todoapp.viewmodels;

import android.app.Application;

import androidx.lifecycle.ViewModel;

import com.mercubuana.todoapp.entities.ToDo;
import com.mercubuana.todoapp.repositories.LocalToDoRepository;

public class ToDoInsertUpdateDeleteViewModel extends ViewModel {
    private final LocalToDoRepository mLocalToDoRepository;

    public ToDoInsertUpdateDeleteViewModel(Application application) {
        mLocalToDoRepository = new LocalToDoRepository(application);
    }

    public void insert(ToDo toDo) {
        mLocalToDoRepository.insert(toDo);
    }

    public void update(ToDo toDo) {
        mLocalToDoRepository.update(toDo);
    }

    public void delete(ToDo toDo) {
        mLocalToDoRepository.delete(toDo);
    }
}
