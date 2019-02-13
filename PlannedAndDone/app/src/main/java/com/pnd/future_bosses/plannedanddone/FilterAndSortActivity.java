package com.pnd.future_bosses.plannedanddone;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.Toast;

public class FilterAndSortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_and_sort);
    }


    public void searchAndSort(View view){
        Toast.makeText(this, "SORTIRAM I FILTRIRAM", Toast.LENGTH_LONG).show();

        //postavljanje sorta

        //postavljanje filtera

        /*
        String where = null;
        String priority = null;
        CheckBox checkBox = (CheckBox)this.findViewById(R.id.priorityHighest);
        if (checkBox.isChecked()) priority +=  DataBase.TASK_PRIORITY + "= 1";
        checkBox = (CheckBox)this.findViewById(R.id.priorityMedium);
        if (checkBox.isChecked())
            if( priority == null)  priority +=  DataBase.TASK_PRIORITY + "= 2";
                else priority +=  DataBase.TASK_PRIORITY + "= 1";

        */


        Intent intent = new Intent(FilterAndSortActivity.this, MainActivity.class);
        startActivity(intent);

    }
}
