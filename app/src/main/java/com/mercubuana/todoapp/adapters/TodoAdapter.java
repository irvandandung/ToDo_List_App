package com.mercubuana.todoapp.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemTodoBinding;
import com.mercubuana.todoapp.ToDoAddUpdateActivity;
import com.mercubuana.todoapp.entities.ToDo;
import com.mercubuana.todoapp.helpers.DateHelper;
import com.mercubuana.todoapp.helpers.ToDoDiffCallback;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ToDoViewHolder> {
    private final ArrayList<ToDo> listToDo = new ArrayList<>();

    public void setListToDo(List<ToDo> listToDo) {
        final ToDoDiffCallback diffCallback = new ToDoDiffCallback(this.listToDo, listToDo);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        this.listToDo.clear();
        this.listToDo.addAll(listToDo);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @Override
    public ToDoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTodoBinding binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ToDoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ToDoViewHolder holder, int position) {
        holder.bind(listToDo.get(position));
    }

    @Override
    public int getItemCount() {
        return listToDo.size();
    }

    static class ToDoViewHolder extends RecyclerView.ViewHolder {
        final ItemTodoBinding binding;
        ToDoViewHolder(ItemTodoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @SuppressLint("SetTextI18n")
        public void bind(ToDo toDo) {
            binding.tvItemTitle.setText(toDo.getTitle());
            binding.tvItemDescription.setText(toDo.getDescription());
            binding.tvItemDate.setText(toDo.getDateInput());
            if (!DateHelper.isDateInvalid(toDo.getDateAlarm(), "yyyy-MM-dd") && !DateHelper.isDateInvalid(toDo.getTimeAlarm(), "HH:mm")) {
                binding.tvAlarmTime.setText(toDo.getDateAlarm()+" "+toDo.getTimeAlarm());
            }
            binding.cvItemTodo.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), ToDoAddUpdateActivity.class);
                intent.putExtra(ToDoAddUpdateActivity.EXTRA_TODO, toDo);
                v.getContext().startActivity(intent);
            });
        }
    }
}
