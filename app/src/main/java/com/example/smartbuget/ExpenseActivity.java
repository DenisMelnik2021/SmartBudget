package com.example.smartbuget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartbuget.databinding.ActivityExpenseBinding;

import java.util.ArrayList;

public class ExpenseActivity extends AppCompatActivity {
    private ActivityExpenseBinding binding;
    Context context = ExpenseActivity.this;
    DBHelper dbHelper = new DBHelper(context);
    Intent main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExpenseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        main = new Intent(ExpenseActivity.this, MainActivity.class);

        binding.buttonBackE.setOnClickListener(v -> startActivity(main));
        binding.buttonAddExpense.setOnClickListener(v -> {
            float currentBalance = sharedPreferences.getFloat("balance", 0);
            float amountToAdd = Float.parseFloat(String.valueOf(binding.editTextExpense.getText()));
            float updatedBalance = currentBalance - amountToAdd;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putFloat("balance", updatedBalance);
            float currentProducts = sharedPreferences.getFloat(sharedPreferences.getString("cat", ""), 0);
            float updatedProducts = currentProducts + amountToAdd;
            editor.putFloat(sharedPreferences.getString("cat", ""), updatedProducts);
            editor.apply();
            dbHelper.addExpense(Double.parseDouble(String.valueOf(binding.editTextExpense.getText())));
        });
        binding.spinnerExpenseCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedValue = (String) parentView.getItemAtPosition(position);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                switch (selectedValue) {
                    case "Продукты": {
                        editor.putString("cat", "products");
                        break;
                    }
                    case "Жилищные расходы": {
                        editor.putString("cat", "housing");
                        break;
                    }
                    case "Транспорт": {
                        editor.putString("cat", "transport");
                        break;
                    }
                    case "Медицина": {
                        editor.putString("cat", "medicine");
                        break;
                    }
                    case "Развлечения": {
                        editor.putString("cat", "entertainment");
                        break;
                    }
                    case "Одежда и обувь": {
                        editor.putString("cat", "cloth");
                        break;
                    }
                    case "Образование": {
                        editor.putString("cat", "education");
                        break;
                    }
                    case "Долги": {
                        editor.putString("cat", "debts");
                        break;
                    }
                }
                editor.apply();
                Toast.makeText(getApplicationContext(), "Выбрана категория: " + selectedValue, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(), "Выберите категорию" , Toast.LENGTH_SHORT).show();
            }
        });
    }
}