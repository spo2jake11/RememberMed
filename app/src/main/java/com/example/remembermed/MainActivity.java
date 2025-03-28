package com.example.remembermed;

import static com.example.remembermed.ui.theme.CalendarUtils.daysInMonthArray;
import static com.example.remembermed.ui.theme.CalendarUtils.monthYearFromDate;
import static com.example.remembermed.ui.theme.CalendarUtils.selectedDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Grid;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.remembermed.ui.theme.CalendarUtils;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{


    private TextView txtMonthYear;
    private RecyclerView calendarRecView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
    }

    private void setMonthView() {
        txtMonthYear.setText(monthYearFromDate(selectedDate));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecView.setLayoutManager(layoutManager);
        calendarRecView.setAdapter(calendarAdapter);
    }

    private void initWidgets() {
        calendarRecView = findViewById(R.id.calendarRecView);
        txtMonthYear = findViewById(R.id.txtMonthDay);
    }

    public void prevMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText) {
        if(dayText.equals("")){
            String message = "Selected date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

    public void weeklyAction(View view) {
        startActivity(new Intent(this, WeeklyViewActivity.class));
    }
}