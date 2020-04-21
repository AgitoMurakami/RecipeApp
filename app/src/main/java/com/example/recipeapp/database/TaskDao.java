package com.example.recipeapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.recipeapp.classes.Recipe;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM recipe ORDER BY id")
    List<Recipe> loadAllTasks();

    @Insert
    void insertTask(Recipe recipeEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(Recipe recipeEntry);

    @Delete
    void deleteTask(Recipe recipeEntry);

    @Query("SELECT * FROM recipe WHERE id =:id")
    Recipe loadFavById(int id);
}
