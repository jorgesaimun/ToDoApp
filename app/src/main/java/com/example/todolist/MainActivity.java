// MainActivity.java

package com.example.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.Utils.AddNewTask;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskAdapter;
    private List<ToDoModel> taskList = new ArrayList<>();
    private static final int ADD_TASK_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskRecyclerView = findViewById(R.id.taskRecyclerViewId);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskAdapter = new ToDoAdapter(this);
        taskRecyclerView.setAdapter(taskAdapter);

        loadTasks();

        // Button for adding new task
        FloatingActionButton addBtn = findViewById(R.id.fabId);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNewTask.class);
                startActivityForResult(intent, ADD_TASK_REQUEST);
            }
        });

        // Swipe functionality for RecyclerView items
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                if (direction == ItemTouchHelper.LEFT) {
                    // Swipe left: Delete action
                    deleteTask(position);
                } else if (direction == ItemTouchHelper.RIGHT) {
                    // Swipe right: Edit action
                    editTask(position);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(taskRecyclerView);
    }

    private void editTask(int position) {
        // Add your edit task logic here
    }

    private void deleteTask(int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to delete?");
        alertDialogBuilder.setTitle(" Confirm Delete");
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "delete successfully", Toast.LENGTH_SHORT).show();
                taskList.remove(position);
                saveTasks();
            }
        });
        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
                loadTasks();
            }
        });

        alertDialogBuilder.show();
        // Add your delete task logic here
    }

    private void loadTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("todo_prefs", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("tasks", null);
        Type type = new TypeToken<List<ToDoModel>>() {
        }.getType();
        taskList = gson.fromJson(json, type);
        if (taskList == null) {
            taskList = new ArrayList<>();
        }
        //sortTasks(); // Sort tasks after loading
        taskAdapter.setTasks(taskList);
    }

//    private void sortTasks() {
//        Collections.sort(taskList, new Comparator<ToDoModel>() {
//            @Override
//            public int compare(ToDoModel task1, ToDoModel task2) {
//                return task1.getTask().compareToIgnoreCase(task2.getTask());
//            }
//        });
//    }

    private void saveTasks() {
        SharedPreferences sharedPreferences = getSharedPreferences("todo_prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(taskList);
        editor.putString("tasks", json);
        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_TASK_REQUEST && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("newTask")) {
                ToDoModel newTask = (ToDoModel) data.getSerializableExtra("newTask");
                taskList.add(newTask);
                //sortTasks(); // Sort tasks after adding a new task
                taskAdapter.notifyDataSetChanged();
                saveTasks();
            }
        }
    }

    public void updateTaskStatus(int position, boolean status) {
        ToDoModel task = taskList.get(position);
        task.setStatus(status);
        saveTasks();
    }
}
