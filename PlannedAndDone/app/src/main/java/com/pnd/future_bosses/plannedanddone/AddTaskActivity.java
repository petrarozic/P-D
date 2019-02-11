package com.pnd.future_bosses.plannedanddone;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    

    boolean type = true; // true za deadline, false za planned
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

        (findViewById(R.id.b_d))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int day, month, year;
                        type = true;
                        Calendar c = Calendar.getInstance();
                        year = c.get(Calendar.YEAR);
                        month = c.get(Calendar.MONTH);
                        day = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, AddTaskActivity.this, day, month, year);
                        datePickerDialog.show();
                    }
                });

        (findViewById(R.id.b_p))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int day, month, year;
                        type = false;
                        Calendar c = Calendar.getInstance();
                        year = c.get(Calendar.YEAR);
                        month = c.get(Calendar.MONTH);
                        day = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this, AddTaskActivity.this, day, month, year);
                        datePickerDialog.show();
                    }
                });


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        edittext_date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        int  hour, minute;

        yearFinal = i;
        monthFinal = i1;
        dayFinal = i2;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddTaskActivity.this, AddTaskActivity.this, hour, minute, true);
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

        hourFinal = i;
        minuteFinal = i1;

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, yearFinal);
        cal.set(Calendar.MONTH, monthFinal);
        cal.set(Calendar.DAY_OF_MONTH, dayFinal);
        cal.set(Calendar.HOUR_OF_DAY, hourFinal);
        cal.set(Calendar.MINUTE, minuteFinal);
        Date date = cal.getTime();

        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String result = df.format("dd/MM/yyyy, HH:mm", date).toString();

        if(type)
        {
            TextView deadline = (TextView) findViewById(R.id.deadlineView);
            deadline.setText(result);

        }
        else
        {
            TextView planned = (TextView) findViewById(R.id.plannedView);
            planned.setText(result);
        }
    }


}
