package com.example.smartbuget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anychart.AnyChart;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.chart.common.listener.Event;
import com.anychart.chart.common.listener.ListenersInterface;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.smartbuget.databinding.ActivityStatBinding;

import java.util.ArrayList;

public class StatActivity extends AppCompatActivity {
    private ActivityStatBinding binding;
    private ArrayList<String> transactionList = new ArrayList<>();
    Context context = StatActivity.this;
    DBHelper dbHelper = new DBHelper(context);
    Intent main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);

        main = new Intent(StatActivity.this, MainActivity.class);

        binding.buttonBackS.setOnClickListener(v -> startActivity(main));
        Cursor cursor = dbHelper.getAllTransactions();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                double amount = cursor.getDouble(cursor.getColumnIndex(DBHelper.COLUMN_AMOUNT));
                int type = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_TYPE));
                String transactionInfo;
                if (type == 0) {
                    transactionInfo = String.valueOf(amount);
                } else {
                    transactionInfo = "-" + amount;
                }
                transactionList.add(transactionInfo);
            }
            cursor.close();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, transactionList);
        binding.listViewTransactions.setAdapter(adapter);
        ArrayList<Double> amounts = new ArrayList<>();
        Cursor cursor1 = dbHelper.getRecentTransactions(20);
        if (cursor1 != null) {
            while (cursor1.moveToNext()) {
                double amount = cursor1.getDouble(cursor1.getColumnIndex(DBHelper.COLUMN_AMOUNT));
                amounts.add(amount);
            }
            cursor1.close();
        }
        Pie pie = AnyChart.pie();
        ArrayList<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry("Продукты", sharedPreferences.getFloat("products", 0)));
        data.add(new ValueDataEntry("Жилищные расходы", sharedPreferences.getFloat("housing", 0)));
        data.add(new ValueDataEntry("Транспорт", sharedPreferences.getFloat("transport", 0)));
        data.add(new ValueDataEntry("Медицина", sharedPreferences.getFloat("medicine", 0)));
        data.add(new ValueDataEntry("Развлечения", sharedPreferences.getFloat("entertainment", 0)));
        data.add(new ValueDataEntry("Одежда и обувь", sharedPreferences.getFloat("cloth", 0)));
        data.add(new ValueDataEntry("Образование", sharedPreferences.getFloat("education", 0)));
        data.add(new ValueDataEntry("Долги", sharedPreferences.getFloat("debts", 0)));

        pie.data(data);

        pie.title("Структура ваших расходов:");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text("Категории")
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        binding.anyChartView.setChart(pie);
    }
}