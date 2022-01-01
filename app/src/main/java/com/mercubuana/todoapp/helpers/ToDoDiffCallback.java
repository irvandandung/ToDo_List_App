package com.mercubuana.todoapp.helpers;

import androidx.recyclerview.widget.DiffUtil;

import com.mercubuana.todoapp.entities.ToDo;

import java.util.List;

public class ToDoDiffCallback extends DiffUtil.Callback {
    private final List<ToDo> mOldToDoList;
    private final List<ToDo> mNewToDoList;

    public ToDoDiffCallback(List<ToDo> oldToDoList, List<ToDo> newToDoList) {
        this.mOldToDoList = oldToDoList;
        this.mNewToDoList = newToDoList;
    }

    @Override
    public int getOldListSize() {
        return mOldToDoList.size();
    }

    @Override
    public int getNewListSize() {
        return mNewToDoList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldToDoList.get(oldItemPosition).getId() == mNewToDoList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final ToDo oldEmployee = mOldToDoList.get(oldItemPosition);
        final ToDo newEmployee = mNewToDoList.get(newItemPosition);
        return oldEmployee.getTitle().equals(newEmployee.getTitle()) && oldEmployee.getDescription().equals(newEmployee.getTitle());
    }
}
