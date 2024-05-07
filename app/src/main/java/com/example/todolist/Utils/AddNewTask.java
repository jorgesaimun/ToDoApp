// AddNewTask.java

package com.example.todolist.Utils;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;

public class AddNewTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_tasklayout);

        EditText editText = findViewById(R.id.newTaskId);
        Button saveBtn = findViewById(R.id.saveId);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskText = editText.getText().toString().trim();
                if (!taskText.isEmpty()) {
                    ToDoModel newTask = new ToDoModel();
                    newTask.setTask(taskText);
                    newTask.setStatus(false);
                    Intent intent = new Intent();
                    intent.putExtra("newTask", newTask);
                    setResult(RESULT_OK, intent);
                    Toast.makeText(AddNewTask.this, "New task added", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddNewTask.this, "Please enter a task", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
