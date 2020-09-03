package com.example.ctistest.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.ctistest.db.entity.CompanyName;

import java.util.List;


@Dao
public interface TaskDao {
    @Query("SELECT * FROM CompanyName")
    List<CompanyName> getAll();

    @Insert
    void insert(CompanyName task);

    @Delete
    void delete(CompanyName task);

    @Update
    void update(CompanyName task);
}
