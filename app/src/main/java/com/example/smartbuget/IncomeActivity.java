package com.example.smartbuget;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.smartbuget.databinding.ActivityIncomeBinding;
import com.example.smartbuget.databinding.ActivityMainBinding;
import com.example.smartbuget.databinding.ActivityStatBinding;

public class IncomeActivity extends AppCompatActivity {
    private ActivityIncomeBinding binding;
    Context context = IncomeActivity.this;
    DBHelper dbHelper = new DBHelper(context);
    Intent main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityIncomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        main = new Intent(this, MainActivity.class);

        binding.buttonBackI.setOnClickListener(v -> startActivity(main));
        binding.buttonAddIncome.setOnClickListener(v -> {
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            float currentBalance = sharedPreferences.getFloat("balance", 0);
            float amountToAdd = Float.parseFloat(String.valueOf(binding.editTextIncome.getText()));
            float updatedBalance = currentBalance + amountToAdd;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("balance", updatedBalance);
            editor.apply();
            dbHelper.addIncome(Double.parseDouble(String.valueOf(binding.editTextIncome.getText())));
        });
    }
}