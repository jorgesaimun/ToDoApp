package com.example.todolist.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.MainActivity;
import com.example.todolist.Model.ToDoModel;
import com.example.todolist.R;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private List<ToDoModel> list;
    private MainActivity activity;

    public ToDoAdapter(MainActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ToDoAdapter.ViewHolder holder, int position) {
        ToDoModel item = list.get(position);
        holder.task.setText(item.getTask());
        holder.task.setChecked(item.getStatus());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setTasks(List<ToDoModel> toDoList) {
        this.list = toDoList;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox task;

        ViewHolder(View view) {
            super(view);
            task = view.findViewById(R.id.toDocheckboxId);

            // Set listener for checkbox click
            task.setOnCheckedChangeListener((buttonView, isChecked) -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    // Update status of task at this position
                    MainActivity activity = (MainActivity) view.getContext();
                    activity.updateTaskStatus(position, isChecked);
                }
            });
        }
    }
}
