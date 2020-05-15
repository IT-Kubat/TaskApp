package com.example.taskapp.room;

import android.widget.TabHost;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.taskapp.ui.models.Task;

@Database(entities = {Task.class}, version = 1)
public abstract   class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();


}
