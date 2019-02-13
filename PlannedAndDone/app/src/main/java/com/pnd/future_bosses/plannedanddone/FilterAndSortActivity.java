package com.pnd.future_bosses.plannedanddone;

import android.content.Intent;
import java.util.Calendar;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class FilterAndSortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_and_sort);
    }


    public void searchAndSort(View view){

        Log.e("Usao sam","");
        String where = null;
        String priority = null;
        String deadline = null;
        String plannedTime = null;
        String category = null;

        //PRIORITY
        CheckBox checkBox = (CheckBox)this.findViewById(R.id.priorityHighest);
        if (checkBox.isChecked()) priority =  DataBase.TASK_PRIORITY + "= 1";

        checkBox = (CheckBox)this.findViewById(R.id.priorityMedium);
        if (checkBox.isChecked())
            if( priority == null)  priority =  DataBase.TASK_PRIORITY + "= 2";
                else priority +=  " OR " + DataBase.TASK_PRIORITY + "= 2";

        checkBox = (CheckBox)this.findViewById(R.id.priorityLowest);
        if (checkBox.isChecked())
            if( priority == null)  priority =  DataBase.TASK_PRIORITY + "= 3";
            else priority +=  " OR " + DataBase.TASK_PRIORITY + "= 3";


        Calendar calendar= Calendar.getInstance();
        String datum = null;

        //DEADLINE
        checkBox = (CheckBox)this.findViewById(R.id.deadlineDay);
        if (checkBox.isChecked()) {
            //slaganje datuma..
            datum = null;

            String year = String.valueOf(calendar.get(Calendar.YEAR));
            datum = year;

            int month = calendar.get(Calendar.MONTH)+1;
            if (month < 10) datum += "0" +  String.valueOf(month);
            else datum += String.valueOf(month);

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (day < 10) datum += "0" +  String.valueOf(day);
            else  datum = String.valueOf(day);

            deadline =  DataBase.TASK_DEADLINE + " LIKE '" + datum + "%'";
        }

        checkBox = (CheckBox)this.findViewById(R.id.deadlineMonth);
        if (checkBox.isChecked()){
            //slaganje datuma..
            datum = null;

            String year = String.valueOf(calendar.get(Calendar.YEAR));
            datum = year;

            int month = calendar.get(Calendar.MONTH)+1;
            if (month < 10) datum += "0" +  String.valueOf(month);
            else datum += String.valueOf(month);

            if (deadline == null) deadline =  DataBase.TASK_DEADLINE + " LIKE '" + datum + "%'";
                else deadline += " OR " + DataBase.TASK_DEADLINE + " LIKE '" + datum + "%'";
        }


        //PLANNED
        checkBox = (CheckBox)this.findViewById(R.id.plannedDay);
        if (checkBox.isChecked()) {
            //slaganje datuma..
            datum = null;

            String year = String.valueOf(calendar.get(Calendar.YEAR));
            datum = year;

            int month = calendar.get(Calendar.MONTH)+1;
            if (month < 10) datum += "0" +  String.valueOf(month);
            else datum += String.valueOf(month);

            int day = calendar.get(Calendar.DAY_OF_MONTH);
            if (day < 10) datum += "0" +  String.valueOf(day);
            else  datum = String.valueOf(day);

            plannedTime =  DataBase.TASK_TIME + " LIKE '" + datum + "%'";
        }

        checkBox = (CheckBox)this.findViewById(R.id.plannedMonth);
        if (checkBox.isChecked()){
            //slaganje datuma..
            datum = null;

            String year = String.valueOf(calendar.get(Calendar.YEAR));
            datum = year;

            int month = calendar.get(Calendar.MONTH)+1;
            if (month < 10) datum += "0" +  String.valueOf(month);
            else datum += String.valueOf(month);

            if (plannedTime == null) plannedTime =  DataBase.TASK_TIME + " LIKE '" + datum + "%'";
            else plannedTime += " OR " + DataBase.TASK_TIME + " LIKE '" + datum + "%'";
        }


        //KREIRANJE UVIJETA ZA UPIT
        if(priority != null) where = "( " + priority +" )";

        if (deadline != null){
            if (where == null) where = "( " + deadline +" )";
                else where += " AND ( " + deadline +" )";
        }

        if (plannedTime != null){
            if (where == null) where = "( " + plannedTime +" )";
            else where += " AND ( " + plannedTime +" )";
        }


        //SORT..
        String sortBy = null;
        RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.sortBy);
        int checkedRadio = radioGroup.getCheckedRadioButtonId();

        switch (checkedRadio){
            case R.id.deadlineUp:
                sortBy = DataBase.TASK_DEADLINE + " ASC";
                break;
            case R.id.deadlineDown:
                sortBy = DataBase.TASK_DEADLINE + " DESC";
                break;
            case R.id.plannedTimeUp:
                sortBy = DataBase.TASK_TIME + " ASC";
                break;
            case R.id.plannedTimeDown:
                sortBy = DataBase.TASK_TIME + " DESC";
                break;
            case R.id.priorityDown:
                sortBy = DataBase.TASK_PRIORITY + " ASC";
                break;
            case R.id.priorityUp:
                sortBy = DataBase.TASK_PRIORITY + " DESC";
                break;
            default:
                sortBy = DataBase.TASK_TIME + " ASC";
        }

        Log.e("ERROR- where", where);
        Log.e("ERROR- sortBy", sortBy);


        //KREIRANJE UPITA
        Uri table = Uri.parse("content://hr.math.provider.contprov/task");
        Cursor c = getContentResolver().query(table,
                new String[]{DataBase.TASK_NAME, DataBase.TASK_TIME, DataBase.TASK_DEADLINE, DataBase.TASK_PRIORITY, DataBase.TASK_CATEGORY, DataBase.TASK_DONE},
                where, null, sortBy);


        //ISPISI IH U MAIN_AC.....



        Intent intent = new Intent(FilterAndSortActivity.this, MainActivity.class);
        startActivity(intent);

    }
}
