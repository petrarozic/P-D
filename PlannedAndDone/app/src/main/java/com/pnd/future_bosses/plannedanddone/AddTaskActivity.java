package com.pnd.future_bosses.plannedanddone;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTaskActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    

    boolean type = true; // true za deadline, false za planned
    int dayFinal, monthFinal, yearFinal, hourFinal, minuteFinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        Bundle extras = getIntent().getExtras();
        String userName;

        if (extras != null) {
            userName = extras.getString("FLAG");

            if (userName == "edit") {
                
            }
        }

        Spinner priority = (Spinner)findViewById(R.id.prioritySpinner);
        String[] items1 = new String[]{"-", "1", "2", "3"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        priority.setAdapter(adapter1);

        Spinner category = (Spinner)findViewById(R.id.categorySpinner);
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

                        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int i, int i1, int i2)
                                    {
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
                                }, year, month, day);
                        datePickerDialog.show();
                    }
                });

        (findViewById(R.id.b_p)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int day, month, year;
                        type = false;
                        Calendar c = Calendar.getInstance();
                        year = c.get(Calendar.YEAR);
                        month = c.get(Calendar.MONTH);
                        day = c.get(Calendar.DAY_OF_MONTH);

                        DatePickerDialog datePickerDialog = new DatePickerDialog(AddTaskActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int i, int i1, int i2)
                                    {
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
                                }, year, month, day);
                        datePickerDialog.show();
                    }
                });
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


    public void cancel(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void save(View view) {

        EditText nameLabel = (EditText)findViewById(R.id.name);
        String name = nameLabel.getText().toString();

        if(name.equals(""))
        {
            new android.support.v7.app.AlertDialog.Builder(this)
                    .setTitle("Notification")
                    .setMessage("You can not save an empty task.")
                    .setPositiveButton(android.R.string.yes, null).show();

        }
        else
        {
            TextView deadlineLabel = (TextView) findViewById(R.id.deadlineView);
            String deadline_ = deadlineLabel.getText().toString();
            String deadline = "";
            if(!deadline_.equals(""))
               deadline = deadline_.substring(6,10) + deadline_.substring(3,5) + deadline_.substring(0,2) + deadline_.substring(12,14) + deadline_.substring(15,17);

            TextView plannedLabel = (TextView) findViewById(R.id.plannedView);
            String planned_ = plannedLabel.getText().toString();
            String planned = "";
            if(!planned_.equals(""))
                planned = planned_.substring(6,10) + planned_.substring(3,5) + planned_.substring(0,2) + planned_.substring(12,14) + planned_.substring(15,17);

            int priority = 0;
            Spinner spinner1 = (Spinner)findViewById(R.id.prioritySpinner);
            String priority_ = spinner1.getSelectedItem().toString();
            if(!priority_.equals("-"))
                priority = Integer.parseInt(priority_);

            insertTask(name, planned, deadline, priority, 0, 0);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    public boolean insertTask (String ime, String time, String deadline, int priority, int category, int done){
        ContentValues values = new ContentValues();
        values.put("name", ime);
        values.put("time", time);
        values.put("deadline", deadline);
        values.put("priority", priority);
        values.put("category", category);
        values.put("done", done);

        Uri uri = getContentResolver().insert(
                Uri.parse("content://hr.math.provider.contprov/task"), values);

        return true;
    }
}
