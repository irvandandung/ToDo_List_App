package com.mercubuana.todoapp.repositories.room.interfaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.mercubuana.todoapp.entities.ToDo;

import java.util.List;

@Dao
public interface ToDoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ToDo toDo);

    @Update()
    void update(ToDo toDo);

    @Delete()
    void delete(ToDo toDo);

    @Query("Select * from todo ORDER BY id ASC")
    LiveData<List<ToDo>> getAllToDos();
}
