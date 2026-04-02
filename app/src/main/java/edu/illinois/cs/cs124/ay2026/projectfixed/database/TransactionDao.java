package edu.illinois.cs.cs124.ay2026.projectfixed.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.illinois.cs.cs124.ay2026.projectfixed.model.Transaction;

@Dao
public interface TransactionDao {

    @Query("SELECT * FROM transactions ORDER BY date DESC")
    List<Transaction> getAllTransactions();

    @Query("SELECT * FROM transactions WHERE date >= :startOfMonth ORDER BY date DESC")
    List<Transaction> getTransactionsSince(long startOfMonth);

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE categoryId = :categoryId AND date >= :startOfMonth")
    double getSpentForCategory(int categoryId, long startOfMonth);

    @Query("SELECT COALESCE(SUM(amount), 0) FROM transactions WHERE date >= :startOfMonth")
    double getTotalSpentSince(long startOfMonth);

    @Insert
    void insert(Transaction transaction);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);
}
