package com.pnd.future_bosses.plannedanddone;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.CursorLoader;
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

        listAllCategories();
    }

    public void addNewCategory(View view) {
        EditText editText = (EditText) findViewById(R.id.editText2);
        String text = editText.getText().toString();

        if(!text.equals("")){

            /*
            db1.open();
            db1.insertCategory(text);
            Cursor c = db1.getAllCategories();
            DisplayContact(c);
            db1.close();
            */
            ContentValues values = new ContentValues();
            values.put("name", text);

            Uri uri = getContentResolver().insert(
                    Uri.parse("content://hr.math.provider.contprov/category"), values);

            listAllCategories();
        }

    }

    public void listAllCategories(){

        Uri table = Uri.parse( "content://hr.math.provider.contprov/category");

        ListView list = (ListView)findViewById(R.id.listView);
        arrayList = new ArrayList<String>();
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
                DisplayContact(c);

                arrayList.add(c.getString(c.getColumnIndex(DataBase.CATEGORY_NAME)));
                Toast.makeText(getApplicationContext(), c.getString(c.getColumnIndex(DataBase.CATEGORY_NAME)), Toast.LENGTH_SHORT).show();

            } while (c.moveToNext());
        }
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.category_item_layout, arrayList);
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

    public void backToMain(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
