package com.example.ctistest.db;



import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.ctistest.db.dao.TaskDao;
import com.example.ctistest.db.entity.CompanyName;


@Database(entities = {CompanyName.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}