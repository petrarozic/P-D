package com.pnd.future_bosses.plannedanddone;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class EditCategories extends AppCompatActivity {


    DBAdapter db1;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categories);

        Bundle bundle = getIntent().getExtras();
        db1 = (DBAdapter) bundle.getSerializable("EXTRA_MESSAGE");

        db1.open();
        db1.insertCategory("kuća");
        db1.insertCategory("posao");


        Cursor cu = db1.getAllCategories();


        if (cu.moveToFirst())
        {
            do {
                DisplayContact(cu);
            } while (cu.moveToNext());
        }
        db1.close();
        listAllCategories();

    }

    public void addNewCategory(View view) {
        EditText editText = (EditText) findViewById(R.id.editText2);
        String text = editText.getText().toString();

        if(!text.equals("")){
            db1.open();
            db1.insertCategory(text);
            Cursor c = db1.getAllCategories();
            DisplayContact(c);
            db1.close();
        }

    }

    public void listAllCategories(){
        db1.open();
        //getati sve kategorije
        Cursor c = db1.getAllCategories();
        //getati listu
        ListView list = (ListView)findViewById(R.id.listView);
        arrayList = new ArrayList<String>();


        if (c.moveToFirst())
        {
            do {
                DisplayContact(c);

                arrayList.add(c.getString(1));

            } while (c.moveToNext());
        }
        db1.close();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.activity_list_item ,arrayList);
        list.setAdapter(adapter);
    }

    //funkcija za ispis
    public void DisplayContact(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Name: " + c.getString(1) + "\n"
                        ,
                Toast.LENGTH_LONG).show();
    }

}
