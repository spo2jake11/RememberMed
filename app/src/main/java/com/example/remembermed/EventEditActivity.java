package com.example.remembermed;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class EventEditActivity extends AppCompatActivity {

    private Button btnTime;

    private TextView viewTime;

    int hour, minute;

    private EditText eventNameET;
    private TextView viewDate;

    private LocalTime time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initWidgets();
        viewDate.setText("Date: " + CalendarUtils.formattedDate(CalendarUtils.selectedDate));
    }

    private void initWidgets() {
        eventNameET = findViewById(R.id.eventNameET);
        viewDate = findViewById(R.id.viewDate);
        btnTime = findViewById(R.id.btnTime);
        viewTime = findViewById(R.id.viewTime);
    }

    public void newTimeSet(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                time = LocalTime.of(hour, minute);
                viewTime.setText(CalendarUtils.formattedTime(time));
            }
        };



        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour, minute, false);
        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void saveEventAction(View view) {
        String eventName = eventNameET.getText().toString();
        Event newEvent = new Event(eventName, CalendarUtils.selectedDate, time);
        Event.eventsList.add(newEvent);
        finish();
    }
}