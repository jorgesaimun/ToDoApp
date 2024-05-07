//package com.example.todolist.Utils;
//
//import android.content.ContentValues;
//import android.content.Context;
//import android.database.Cursor;
//import android.database.sqlite.SQLiteDatabase;
//import android.database.sqlite.SQLiteOpenHelper;
//
//import androidx.annotation.Nullable;
//
//import com.example.todolist.Model.ToDoModel;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class DataBaseHandler extends SQLiteOpenHelper {
//
//    public static final int version = 1;
//    public static final String DataBaseName = "ToDoListDataBase";
//    public static final String tableName = "ToDoTable";
//    public static final String id = "Id";
//    public static final String Task = "Task";
//    public static final String status = "Status";
//    public static final String dropTable = " drop table if exists " + tableName;
//
//    public static final String create_table = "create table " + tableName + "( " + id + "integer primary key autoincrement," + Task +
//            "text," + status + "integer)";
//
//    public DataBaseHandler(@Nullable Context context) {
//        super(context, DataBaseName, null, version);
//    }
//
//    SQLiteDatabase db;
//
//    @Override
//    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(create_table);
//    }
//
//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL(dropTable);
//        onCreate(db);
//    }
//
//    public void openDataBase() {
//        db = this.getWritableDatabase();
//    }
//
//    public void insertTask(ToDoModel task) {
//        ContentValues cv = new ContentValues();
//        cv.put(Task, task.getTask());
//        cv.put(status, task.getStatus());
//        db.insert(tableName, null, cv);
//    }
//
//    public List<ToDoModel> getAllTasks() {
//        List<ToDoModel> taskList = new ArrayList<>();
//
//        Cursor cursor = null;
//
//        db.beginTransaction();
//        try {
//            cursor = db.query(tableName, null, null, null, null, null, null);
//            if (cursor != null) {
//                if (cursor.moveToFirst()) {
//                    do {
//                        ToDoModel task = new ToDoModel();
//                        task.setId(cursor.getInt(Math.abs(cursor.getColumnIndex(id))));
//                        task.setTask(String.valueOf(cursor.getInt(Math.abs(cursor.getColumnIndex(Task)))));
//                        task.setStatus(cursor.getInt(Math.abs(cursor.getColumnIndex(status))));
//                        taskList.add(task);
//
//                    } while (cursor.moveToNext());
//                }
//            }
//
//        } finally {
//            db.endTransaction();
//            cursor.close();
//        }
//        return taskList;
//    }
//
//    public void updataStatus(int id, int new_status) {
//        ContentValues cv = new ContentValues();
//        cv.put(status, new_status);
//        db.update(tableName, cv, id + "?", new String[]{String.valueOf(id)});
//    }
//
//    public void updateTask(int id, String new_task) {
//        ContentValues cv = new ContentValues();
//        cv.put(Task, new_task);
//        db.update(tableName, cv, id + "?", new String[]{String.valueOf(id)});
//    }
//
//    public void deleteTask(int id) {
//        db.delete(tableName, id + "?", new String[]{String.valueOf(id)});
//    }
//}
