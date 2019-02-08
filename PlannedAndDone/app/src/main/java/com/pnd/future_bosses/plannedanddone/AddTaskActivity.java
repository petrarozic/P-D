package com.pnd.future_bosses.plannedanddone;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    Button deadline;
    Button plan;

    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Spinner priority = (Spinner)findViewById(R.id.spinner1);
        String[] items1 = new String[]{"-", "1", "2", "3"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        priority.setAdapter(adapter1);

        Spinner category = (Spinner)findViewById(R.id.spinner2);
        String[] items2 = new String[]{"-", "1", "2", "3"};

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items2);
        category.setAdapter(adapter2);

        deadline = (Button) findViewById(R.id.b_d);
        plan = (Button) findViewById(R.id.b_p);


    }

    public void deadlinePicker(View view) {
        String timestamp = new SimpleDateFormat("yyyyMMdd").format(java.util.Calendar.getInstance().getTime());
        year = Integer.parseInt(timestamp.substring(0,3));
        month = Integer.parseInt(timestamp.substring(4,5));
        day = Integer.parseInt(timestamp.substring(5,6));

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, AddTaskActivity.this, day, month, year);
        datePickerDialog.show();
    }

    public void planPicker(View view) {
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        yearFinal = i;
        monthFinal = i1 + 1;
        dayFinal = i2;

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(java.util.Calendar.getInstance().getTime());
        hour = Integer.parseInt(timestamp.substring(10,11));
        minute = Integer.parseInt(timestamp.substring(12,13));

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, AddTaskActivity.this, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        hourFinal = i;
        minuteFinal = i1;
//textwiev pokraj kalendarica u koji se ispise vrijeme
    }
}
