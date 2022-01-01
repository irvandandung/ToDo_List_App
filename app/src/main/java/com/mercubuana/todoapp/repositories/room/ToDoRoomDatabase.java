package com.mercubuana.todoapp.repositories.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mercubuana.todoapp.entities.ToDo;
import com.mercubuana.todoapp.repositories.room.interfaces.ToDoDao;

@Database(entities = {ToDo.class}, version = 2, exportSchema = false)
public abstract class ToDoRoomDatabase extends RoomDatabase {
    public abstract ToDoDao toDoDao();

    private static volatile ToDoRoomDatabase INSTANCE;

    public static ToDoRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ToDoRoomDatabase.class) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ToDoRoomDatabase.class, "todo_database").build();
            }
        }

        return  INSTANCE;
    }
}
