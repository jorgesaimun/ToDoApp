package com.example.todolist.Model;

import java.io.Serializable;

public class ToDoModel implements Serializable {

    private int id;
    private String task;
    private boolean completed;

    public ToDoModel() {
        // Default constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public boolean getStatus() {
        return completed;
    }

    public void setStatus(boolean b) {
        this.completed = b;
    }
}
