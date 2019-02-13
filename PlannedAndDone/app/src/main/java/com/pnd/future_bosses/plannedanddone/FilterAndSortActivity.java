package com.pnd.future_bosses.plannedanddone;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FilterAndSortActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_and_sort);

        LinearLayout layoutCategories = (LinearLayout) findViewById(R.id.Categories);
        layoutCategories.removeAllViews();
        Uri table = Uri.parse( "content://hr.math.provider.contprov/category");

        Cursor c;
        if (android.os.Build.VERSION.SDK_INT <11) {
            //---before Honeycomb---
            c = managedQuery(table, null, null, null, null);
        } else {
            //---Honeycomb and later---
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    table, null, null, null, null);
            c = cursorLoader.loadInBackground();
        }
        if (c.moveToFirst())
        {
            do {
                CheckBox newCategory = new CheckBox(this);
                newCategory.setText(c.getString(1));
                newCategory.setTag(c.getInt(0));
                layoutCategories.addView(newCategory);

            } while (c.moveToNext());
        }
        else{
            TextView newCategory = new TextView(this);
            newCategory.setText("You don't have any categories yet.");
            layoutCategories.addView(newCategory);
        }
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
