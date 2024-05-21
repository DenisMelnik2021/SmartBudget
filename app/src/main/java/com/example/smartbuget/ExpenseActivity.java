package com.example.smartbuget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartbuget.databinding.ActivityExpenseBinding;

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
                        float currentProducts = sharedPreferences.getFloat("products", 0);
                        String amountString = String.valueOf(binding.editTextExpense.getText());
                        float amountToAdd = amountString.isEmpty() ? 0 : Float.parseFloat(amountString);
                        float updatedProducts = currentProducts + amountToAdd;
                        editor.putFloat("products", updatedProducts);
                        break;
                    }
                    case "Жилищные расходы": {
                        float currentHousing = sharedPreferences.getFloat("housing", 0);
                        String amountString = String.valueOf(binding.editTextExpense.getText());
                        float amountToAdd = amountString.isEmpty() ? 0 : Float.parseFloat(amountString);
                        float updatedProducts = currentHousing + amountToAdd;
                        editor.putFloat("housing", updatedProducts);
                        break;
                    }
                    case "Транспорт": {
                        float currentTransport = sharedPreferences.getFloat("transport", 0);
                        String amountString = String.valueOf(binding.editTextExpense.getText());
                        float amountToAdd = amountString.isEmpty() ? 0 : Float.parseFloat(amountString);
                        float updatedProducts = currentTransport + amountToAdd;
                        editor.putFloat("transport", updatedProducts);
                        break;
                    }
                    case "Медицина": {
                        float currentMedicine = sharedPreferences.getFloat("medicine", 0);
                        String amountString = String.valueOf(binding.editTextExpense.getText());
                        float amountToAdd = amountString.isEmpty() ? 0 : Float.parseFloat(amountString);
                        float updatedProducts = currentMedicine + amountToAdd;
                        editor.putFloat("medicine", updatedProducts);
                        break;
                    }
                    case "Развлечения": {
                        float currentEntertainment = sharedPreferences.getFloat("entertainment", 0);
                        String amountString = String.valueOf(binding.editTextExpense.getText());
                        float amountToAdd = amountString.isEmpty() ? 0 : Float.parseFloat(amountString);
                        float updatedProducts = currentEntertainment + amountToAdd;
                        editor.putFloat("entertainment", updatedProducts);
                        break;
                    }
                    case "Одежда и обувь": {
                        float currentCloth = sharedPreferences.getFloat("cloth", 0);
                        String amountString = String.valueOf(binding.editTextExpense.getText());
                        float amountToAdd = amountString.isEmpty() ? 0 : Float.parseFloat(amountString);
                        float updatedProducts = currentCloth + amountToAdd;
                        editor.putFloat("cloth", updatedProducts);
                        break;
                    }
                    case "Образование": {
                        float currenteEducation = sharedPreferences.getFloat("education", 0);
                        String amountString = String.valueOf(binding.editTextExpense.getText());
                        float amountToAdd = amountString.isEmpty() ? 0 : Float.parseFloat(amountString);
                        float updatedProducts = currenteEducation + amountToAdd;
                        editor.putFloat("education", updatedProducts);
                        break;
                    }
                    case "Долги": {
                        float currenteDebts = sharedPreferences.getFloat("debts", 0);
                        String amountString = String.valueOf(binding.editTextExpense.getText());
                        float amountToAdd = amountString.isEmpty() ? 0 : Float.parseFloat(amountString);
                        float updatedProducts = currenteDebts + amountToAdd;
                        editor.putFloat("debts", updatedProducts);
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