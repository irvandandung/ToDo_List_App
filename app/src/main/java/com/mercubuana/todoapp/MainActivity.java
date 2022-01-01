package com.mercubuana.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.mercubuana.todoapp.adapters.TodoAdapter;
import com.mercubuana.todoapp.viewmodels.ToDoMainViewModel;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private TodoAdapter adapter;

    public ActionBarDrawerToggle actionBarDrawerToggle;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ToDoMainViewModel mainViewModel = obtainViewModel(MainActivity.this);
        mainViewModel.getAllToDos().observe(this, toDo -> {
            if(toDo != null && toDo.size() > 0) {
                binding.emptyState.setVisibility(View.GONE);
                binding.rvTodo.setVisibility(View.VISIBLE);
                adapter.setListToDo(toDo);
            } else {
                binding.rvTodo.setVisibility(View.GONE);
                binding.emptyState.setVisibility(View.VISIBLE);
            }
        });

        adapter = new TodoAdapter();

        binding.rvTodo.setLayoutManager(new LinearLayoutManager(this));
        binding.rvTodo.setHasFixedSize(true);
        binding.rvTodo.setAdapter(adapter);

        binding.fabAdd.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ToDoAddUpdateActivity.class);
            startActivity(intent);
        });

        binding.fabAdd2.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ToDoAddUpdateActivity.class);
            startActivity(intent);
        });
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, binding.myDrawerLayout, R.string.nav_open, R.string.nav_close);
        binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        binding.navView.setNavigationItemSelectedListener(this);

    }

    @NonNull
    private static ToDoMainViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return new ViewModelProvider(activity, factory).get(ToDoMainViewModel.class);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_todo:
                Intent intent = new Intent(this, MainActivity.class);
                this.startActivity(intent);
                this.finish();
                break;
            case R.id.nav_about:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                this.startActivity(intentAbout);
                this.finish();
                break;
            case R.id.nav_out:
                finishAndRemoveTask();
                break;
        }
        return true;
    }
}