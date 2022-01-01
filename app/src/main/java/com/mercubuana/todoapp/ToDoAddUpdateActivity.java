package com.mercubuana.todoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ActivityToDoAddUpdateBinding;
import com.mercubuana.todoapp.entities.ToDo;
import com.mercubuana.todoapp.fragments.DatePickerFragment;
import com.mercubuana.todoapp.fragments.TimePickerFragment;
import com.mercubuana.todoapp.helpers.DateHelper;
import com.mercubuana.todoapp.viewmodels.ToDoInsertUpdateDeleteViewModel;

public class ToDoAddUpdateActivity extends AppCompatActivity implements DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener{
    public static final String EXTRA_TODO = "extra_todo";
    private final int ALERT_DIALOG_CLOSE = 10;
    private final int ALERT_DIALOG_DELETE = 20;

    private boolean isEdit = false;
    private ToDo toDo;

    private ToDoInsertUpdateDeleteViewModel toDoInsertUpdateDeleteViewModel;
    private ActivityToDoAddUpdateBinding binding;
    private ToDoAlarmReceiver toDoAlarmReceiver;

    private final static String DATE_PICKER_TAG = "DatePicker";
    private final static String TIME_PICKER_ONCE_TAG = "TimePickerOnce";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toDoAlarmReceiver = new ToDoAlarmReceiver();
        binding = ActivityToDoAddUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toDoInsertUpdateDeleteViewModel = obtainViewModel(ToDoAddUpdateActivity.this);

        toDo = getIntent().getParcelableExtra(EXTRA_TODO);
        if (toDo != null) {
            isEdit = true;
        } else {
            toDo = new ToDo();
        }

        String actionBarTitle;
        String btnTitle;

        if (isEdit) {
            actionBarTitle = getString(R.string.change);
            btnTitle = getString(R.string.update);

            if (toDo != null) {
                binding.edtTitle.setText(toDo.getTitle());
                binding.edtDescription.setText(toDo.getDescription());
                binding.tvOnceTime.setText(toDo.getTimeAlarm());
                binding.tvOnceDate.setText(toDo.getDateAlarm());
            }
        } else {
            actionBarTitle = getString(R.string.add);
            btnTitle = getString(R.string.save);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        binding.btnOnceDate.setOnClickListener(view -> {
            DatePickerFragment datePickerFragment = new DatePickerFragment();
            datePickerFragment.show(getSupportFragmentManager(), DATE_PICKER_TAG);
        });

        binding.btnOnceTime.setOnClickListener(v -> {
            TimePickerFragment timePickerFragmentOne = new TimePickerFragment();
            timePickerFragmentOne.show(getSupportFragmentManager(), TIME_PICKER_ONCE_TAG);
        });

        binding.btnSubmit.setText(btnTitle);

        binding.btnSubmit.setOnClickListener(view -> {
            String title = binding.edtTitle.getText().toString().trim();
            String description = binding.edtDescription.getText().toString().trim();
            String dateAlarm = (String) binding.tvOnceDate.getText();
            String timeAlarm = (String) binding.tvOnceTime.getText();

            if (title.isEmpty()) {
                binding.edtTitle.setError(getString(R.string.empty));
            } else if (description.isEmpty()) {
                binding.edtDescription.setError(getString(R.string.empty));
            } else {
                if(!DateHelper.isDateInvalid(toDo.getDateAlarm(), "yyyy-MM-dd") && !DateHelper.isDateInvalid(toDo.getTimeAlarm(), "HH:mm")) {
                    toDoAlarmReceiver.cancelOneTimeAlarm(this, toDo.getDateAlarm(), toDo.getTimeAlarm());
                }
                toDo.setTitle(title);
                toDo.setDescription(description);
                toDo.setDateAlarm(dateAlarm);
                toDo.setTimeAlarm(timeAlarm);

                if(!DateHelper.isDateInvalid(dateAlarm, "yyyy-MM-dd") && !DateHelper.isDateInvalid(timeAlarm, "HH:mm")) {
                    toDoAlarmReceiver.setOneTimeAlarm(this, ToDoAlarmReceiver.TYPE_ONE_TIME, dateAlarm, timeAlarm, title, description);
                }

                if (isEdit) {
                    toDoInsertUpdateDeleteViewModel.update(toDo);
                    showToast(getString(R.string.changed));
                } else {
                    toDo.setDateInput(DateHelper.getCurrentDate());
                    toDoInsertUpdateDeleteViewModel.insert(toDo);
                    showToast(getString(R.string.added));
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @NonNull
    private static ToDoInsertUpdateDeleteViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        return new ViewModelProvider(activity, factory).get(ToDoInsertUpdateDeleteViewModel.class);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (isEdit) {
            getMenuInflater().inflate(R.menu.menu_form, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_delete) {
            showAlertDialog(ALERT_DIALOG_DELETE);
        } else if (item.getItemId() == android.R.id.home) {
            showAlertDialog(ALERT_DIALOG_CLOSE);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        showAlertDialog(ALERT_DIALOG_CLOSE);
        super.onBackPressed();
    }

    private void showAlertDialog(int type) {
        final boolean isDialogClose = type == ALERT_DIALOG_CLOSE;
        String dialogTitle, dialogMessage;

        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel);
            dialogMessage = getString(R.string.message_cancel);
        } else {
            dialogMessage = getString(R.string.message_delete);
            dialogTitle = getString(R.string.delete);
        }

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle(dialogTitle);

        alertDialogBuilder
                .setMessage(dialogMessage)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.yes),(dialog, id) -> {
                    if (!isDialogClose) {
                        if(!DateHelper.isDateInvalid(toDo.getDateAlarm(), "yyyy-MM-dd") && !DateHelper.isDateInvalid(toDo.getTimeAlarm(), "HH:mm")) {
                            toDoAlarmReceiver.cancelOneTimeAlarm(this, toDo.getDateAlarm(), toDo.getTimeAlarm());
                        }
                        toDoInsertUpdateDeleteViewModel.delete(toDo);
                        showToast(getString(R.string.deleted));
                    }
                    finishAndRemoveTask();
                })
                .setNegativeButton(getString(R.string.no), (dialog, id) -> dialog.cancel());
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public void onDialogDateSet(String tag, int year, int month, int dayOfMonth) {
        binding.tvOnceDate.setText(DateHelper.dateFormat(year, month, dayOfMonth));
    }

    @Override
    public void onDialogTimeSet(String tag, int hourOfDay, int minute) {
        binding.tvOnceTime.setText(DateHelper.timeFormat(hourOfDay, minute));
    }
}