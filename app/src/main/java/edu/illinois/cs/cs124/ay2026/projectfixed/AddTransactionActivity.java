package edu.illinois.cs.cs124.ay2026.projectfixed;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import edu.illinois.cs.cs124.ay2026.projectfixed.database.AppDatabase;
import edu.illinois.cs.cs124.ay2026.projectfixed.model.Category;
import edu.illinois.cs.cs124.ay2026.projectfixed.model.Transaction;

public class AddTransactionActivity extends AppCompatActivity {

    private EditText etAmount;
    private Spinner spinnerCategory;
    private Button btnDate;
    private EditText etNote;

    private List<Category> categories;
    private Calendar selectedDate;
    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Add Transaction");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        etAmount = findViewById(R.id.etAmount);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        btnDate = findViewById(R.id.btnDate);
        etNote = findViewById(R.id.etNote);
        Button btnSave = findViewById(R.id.btnSave);

        selectedDate = Calendar.getInstance();
        updateDateButton();

        AppDatabase db = AppDatabase.getInstance(this);
        categories = db.categoryDao().getAllCategories();

        String[] categoryNames = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            categoryNames[i] = categories.get(i).name;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, categoryNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

        btnDate.setOnClickListener(v -> showDatePicker());
        btnSave.setOnClickListener(v -> saveTransaction());
    }

    private void showDatePicker() {
        int year = selectedDate.get(Calendar.YEAR);
        int month = selectedDate.get(Calendar.MONTH);
        int day = selectedDate.get(Calendar.DAY_OF_MONTH);

        new DatePickerDialog(this, (view, y, m, d) -> {
            selectedDate.set(y, m, d);
            updateDateButton();
        }, year, month, day).show();
    }

    private void updateDateButton() {
        btnDate.setText(DATE_FORMAT.format(selectedDate.getTime()));
    }

    private void saveTransaction() {
        String amountStr = etAmount.getText().toString().trim();
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show();
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid amount", Toast.LENGTH_SHORT).show();
            return;
        }

        if (amount <= 0) {
            Toast.makeText(this, "Amount must be greater than zero", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedIndex = spinnerCategory.getSelectedItemPosition();
        Category category = categories.get(selectedIndex);

        Transaction transaction = new Transaction();
        transaction.amount = amount;
        transaction.categoryId = category.id;
        transaction.date = selectedDate.getTimeInMillis();
        transaction.note = etNote.getText().toString().trim();

        AppDatabase.getInstance(this).transactionDao().insert(transaction);
        Toast.makeText(this, "Transaction saved", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
