package edu.illinois.cs.cs124.ay2026.projectfixed;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.illinois.cs.cs124.ay2026.projectfixed.database.AppDatabase;
import edu.illinois.cs.cs124.ay2026.projectfixed.model.Category;
import edu.illinois.cs.cs124.ay2026.projectfixed.model.Transaction;

public class TransactionHistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView tvEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Transaction History");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.recyclerView);
        tvEmpty = findViewById(R.id.tvEmpty);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadTransactions();
    }

    private void loadTransactions() {
        AppDatabase db = AppDatabase.getInstance(this);

        List<Category> categories = db.categoryDao().getAllCategories();
        Map<Integer, String> categoryNames = new HashMap<>();
        for (Category c : categories) {
            categoryNames.put(c.id, c.name);
        }

        List<Transaction> transactions = db.transactionDao().getAllTransactions();

        if (transactions.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            recyclerView.setAdapter(new TransactionAdapter(transactions, categoryNames));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
