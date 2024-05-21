package com.example.smartbuget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartbuget.databinding.ActivityFirstBinding;

public class FirstActivity extends AppCompatActivity {
    Intent main;
    private ActivityFirstBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFirstBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        main = new Intent(FirstActivity.this, MainActivity.class);
        binding.button.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            float balance = Float.parseFloat(String.valueOf(binding.editTextText.getText()));
            editor.putFloat("balance", balance);
            editor.putFloat("products", 0);
            editor.putFloat("housing", 0);
            editor.putFloat("transport", 0);
            editor.putFloat("medicine", 0);
            editor.putFloat("entertainment", 0);
            editor.putFloat("cloth", 0);
            editor.putFloat("education", 0);
            editor.putFloat("debts", 0);
            editor.apply();
            editor.putBoolean("isFirstRun", false);
            editor.apply();
            startActivity(main);
        });
    }
}