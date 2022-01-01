package com.mercubuana.todoapp.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mercubuana.todoapp.entities.ToDo;
import com.mercubuana.todoapp.repositories.room.ToDoRoomDatabase;
import com.mercubuana.todoapp.repositories.room.interfaces.ToDoDao;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LocalToDoRepository {
    private final ToDoDao mToDoDao;
    private final ExecutorService executorService;

    public LocalToDoRepository(Application application) {
        executorService = Executors.newSingleThreadExecutor();
        ToDoRoomDatabase db = ToDoRoomDatabase.getDatabase(application);
        mToDoDao = db.toDoDao();
    }

    public LiveData<List<ToDo>> getAllToDos() {
        return mToDoDao.getAllToDos();
    }

    public void insert(final ToDo toDo) {
        executorService.execute(() -> mToDoDao.insert(toDo));
    }

    public void update(final ToDo toDo) {
        executorService.execute(() -> mToDoDao.update(toDo));
    }

    public void delete(final ToDo toDo) {
        executorService.execute(() -> mToDoDao.delete(toDo));
    }
}
