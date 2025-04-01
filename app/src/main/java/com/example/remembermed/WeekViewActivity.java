package com.example.remembermed;

import static com.example.remembermed.CalendarUtils.daysInWeekArray;
import static com.example.remembermed.CalendarUtils.monthYearFromDate;
import static com.example.remembermed.CalendarUtils.selectedDate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.util.ArrayList;

public class WeekViewActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener{

    private TextView txtMonthYear;
    private RecyclerView calendarRecView;

    private ListView eventListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_weekly_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initWidgets();
        setWeekView();
    }

    private void setWeekView() {
        txtMonthYear.setText(monthYearFromDate(selectedDate));
        ArrayList<LocalDate> days = daysInWeekArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(days, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecView.setLayoutManager(layoutManager);
        calendarRecView.setAdapter(calendarAdapter);
        setEventAdapter();
    }



    private void initWidgets() {
        calendarRecView = findViewById(R.id.calendarRecView);
        txtMonthYear = findViewById(R.id.txtMonthDay);
        eventListView = findViewById(R.id.eventListView);
    }

    public void prevWeekAction(View view) {
        selectedDate = selectedDate.minusWeeks(1);
        setWeekView();
    }

    public void nextWeekAction(View view) {
        selectedDate = selectedDate.plusWeeks(1);
        setWeekView();
    }

    public void newEventAction(View view) {
        startActivity(new Intent(this, EventEditActivity.class));
    }

    @Override
    public void onItemClick(int position, LocalDate date) {
        selectedDate = date;
        setWeekView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setEventAdapter();
    }

    private void setEventAdapter() {
        ArrayList<Event> dailyEvents = Event.eventsForDate(selectedDate);

        EventAdapter eventAdapter = new EventAdapter(getApplicationContext(), dailyEvents);

        eventListView.setAdapter(eventAdapter);
    }

    public void dailyAction(View view) {
    }
}