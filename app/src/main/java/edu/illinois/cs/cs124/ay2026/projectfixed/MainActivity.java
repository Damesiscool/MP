package edu.illinois.cs.cs124.ay2026.projectfixed;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.illinois.cs.cs124.ay2026.projectfixed.database.AppDatabase;

public class MainActivity extends AppCompatActivity {

    private TextView tvMonthLabel;
    private TextView tvSavingsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMonthLabel = findViewById(R.id.tvMonthLabel);
        tvSavingsMessage = findViewById(R.id.tvSavingsMessage);

        Button btnAddTransaction = findViewById(R.id.btnAddTransaction);
        btnAddTransaction.setOnClickListener(v ->
                startActivity(new Intent(this, AddTransactionActivity.class)));

        Button btnViewHistory = findViewById(R.id.btnViewHistory);
        btnViewHistory.setOnClickListener(v ->
                startActivity(new Intent(this, TransactionHistoryActivity.class)));

        String monthName = new SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(new Date());
        tvMonthLabel.setText(monthName);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDashboard();
    }

    private void updateDashboard() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long startOfMonth = cal.getTimeInMillis();

        AppDatabase db = AppDatabase.getInstance(this);
        double monthTotal = db.transactionDao().getTotalSpentSince(startOfMonth);

        SharedPreferences prefs = getSharedPreferences("budget_prefs", MODE_PRIVATE);
        float baseline = prefs.getFloat("baseline_monthly_total", 0f);

        if (baseline > 0) {
            double saved = baseline - monthTotal;
            if (saved >= 0) {
                tvSavingsMessage.setText(String.format(Locale.getDefault(),
                        "You've spent $%.2f this month.\nYou've saved $%.2f compared to your baseline!", monthTotal, saved));
            } else {
                tvSavingsMessage.setText(String.format(Locale.getDefault(),
                        "You've spent $%.2f this month.\nYou're $%.2f over your baseline — check your budgets!", monthTotal, -saved));
            }
        } else {
            tvSavingsMessage.setText(String.format(Locale.getDefault(),
                    "You've spent $%.2f this month.", monthTotal));
        }
    }
}
