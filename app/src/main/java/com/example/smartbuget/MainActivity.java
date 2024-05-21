package com.example.smartbuget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartbuget.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    Intent first;
    Intent stat;
    Intent income;
    Intent expanse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        first = new Intent(MainActivity.this, FirstActivity.class);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        if (isFirstRun) {
            startActivity(first);
        } else {

            stat = new Intent(MainActivity.this, StatActivity.class);
            income = new Intent(MainActivity.this, IncomeActivity.class);
            expanse = new Intent(MainActivity.this, ExpenseActivity.class);

            binding.textViewBalance.setText(sharedPreferences.getFloat("balance", 0) + " RUB");
            binding.buttonViewExpanse.setOnClickListener(v -> startActivity(expanse));
            binding.buttonViewIncome.setOnClickListener(v -> startActivity(income));
            binding.buttonViewStatistics.setOnClickListener(v -> startActivity(stat));
        }
    }
}