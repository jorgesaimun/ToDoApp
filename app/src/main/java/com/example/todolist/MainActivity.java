package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.todolist.Adapter.ToDoAdapter;
import com.example.todolist.Model.ToDoModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView taskRecyclerView;
    private ToDoAdapter taskAdapter;

    FloatingActionButton addBtn;
    private List<ToDoModel> taskList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addBtn = findViewById(R.id.fabId);

        taskRecyclerView = findViewById(R.id.taskRecyclerViewId);

        taskRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        taskAdapter = new ToDoAdapter(this);

        taskRecyclerView.setAdapter(taskAdapter);

        ToDoModel task = new ToDoModel();
        task.setTask("this is the first task");
        task.setStatus(0);
        task.setId(1);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "New Task Added", Toast.LENGTH_SHORT).show();
                ToDoModel newTask = new ToDoModel();
                newTask.setTask("New Task"); // Set a default task name
                newTask.setStatus(0); // Set default status
                newTask.setId(taskList.size() + 1); // Increment ID
                taskList.add(newTask);
                taskAdapter.notifyDataSetChanged(); // Notify adapter about the new data
            }
        });

        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);
        taskList.add(task);

        taskAdapter.setTasks(taskList);


    }
}