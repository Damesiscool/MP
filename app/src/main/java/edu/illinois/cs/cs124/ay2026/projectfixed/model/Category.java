package edu.illinois.cs.cs124.ay2026.projectfixed.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "categories")
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public double monthlyBudget;
    public boolean isPriority;
}