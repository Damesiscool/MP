package edu.illinois.cs.cs124.ay2026.projectfixed.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "transactions")
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public double amount;
    public int categoryId;
    public long date;
    public String note;
}