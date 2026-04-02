package edu.illinois.cs.cs124.ay2026.projectfixed.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.illinois.cs.cs124.ay2026.projectfixed.model.Category;

@Dao
public interface CategoryDao {

    @Query("SELECT * FROM categories ORDER BY name ASC")
    List<Category> getAllCategories();

    @Query("SELECT * FROM categories WHERE id = :id")
    Category getCategoryById(int id);

    @Insert
    void insert(Category category);

    @Update
    void update(Category category);
}