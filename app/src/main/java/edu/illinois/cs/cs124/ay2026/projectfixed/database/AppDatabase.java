package edu.illinois.cs.cs124.ay2026.projectfixed.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import edu.illinois.cs.cs124.ay2026.projectfixed.model.Category;
import edu.illinois.cs.cs124.ay2026.projectfixed.model.Transaction;

@Database(entities = {Category.class, Transaction.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CategoryDao categoryDao();
    public abstract TransactionDao transactionDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "budget_db")
                            .allowMainThreadQueries()
                            .addCallback(new RoomDatabase.Callback() {
                                @Override
                                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                    super.onCreate(db);
                                    String[] defaultCategories = {
                                            "Food & Dining",
                                            "Groceries",
                                            "Rent & Housing",
                                            "Utilities",
                                            "Transportation",
                                            "Textbooks & School Supplies",
                                            "Entertainment & Social",
                                            "Health & Personal Care",
                                            "Clothing",
                                            "Miscellaneous"
                                    };
                                    for (String name : defaultCategories) {
                                        db.execSQL(
                                                "INSERT INTO categories (name, monthlyBudget, isPriority) VALUES (?, ?, ?)",
                                                new Object[]{name, 0.0, 0}
                                        );
                                    }
                                }
                            })
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
