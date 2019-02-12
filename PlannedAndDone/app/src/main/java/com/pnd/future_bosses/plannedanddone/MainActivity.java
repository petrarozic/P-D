package com.pnd.future_bosses.plannedanddone;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public DBAdapter db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        //        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
        //toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        db = new DBAdapter(this);

        //********************************************
        //STVARANJE ZADATAKA:
        //********************************************
        ContentValues values = new ContentValues();
        values.put("name", "zad1");
        values.put("time", "VRIJEME1");
        values.put("deadline", "KRAJNJE_VRIJEME1");
        values.put("priority", 1);
        values.put("category", 0);
        values.put("done", 0);


        Uri uri = getContentResolver().insert(
                Uri.parse("content://hr.math.provider.contprov/task"), values);

        ContentValues values1 = new ContentValues();
        values1.put("name", "zad2");
        values1.put("time", "VRIJEME2");
        values1.put("deadline", "KRAJNJE_VRIJEME2");
        values1.put("priority", 2);
        values1.put("category", 0);
        values1.put("done", 0);

        Uri uri2 = getContentResolver().insert(
                Uri.parse("content://hr.math.provider.contprov/task"), values1);

        values = new ContentValues();
        values.put("name", "zad3");
        values.put("time", "VRIJEME3");
        values.put("deadline", "KRAJNJE_VRIJEME3");
        values.put("priority", 2);
        values.put("category", 0);
        values.put("done", 1);

        uri = getContentResolver().insert(
                Uri.parse("content://hr.math.provider.contprov/task"), values);


        //********************************************
        //DOHVATI ZADATKE:
        //********************************************
        Cursor c;
        Uri table = Uri.parse( "content://hr.math.provider.contprov/task");
        if (android.os.Build.VERSION.SDK_INT <11) {
            c = managedQuery(table, null, null, null, null);
        } else {
            CursorLoader cursorLoader = new CursorLoader(this,table, null, null, null, null);
            c = cursorLoader.loadInBackground();
        }

        if (c.moveToFirst()) {
            do{
                DisplayTask(c);
            } while (c.moveToNext());
        }

    }


    @Override
    public void onResume() {
        super.onResume();

        //*********************
        //DOHVATI SVE ZADATKE
        //*********************
        Cursor c;
        Uri table = Uri.parse( "content://hr.math.provider.contprov/task");
        if (android.os.Build.VERSION.SDK_INT <11) {
            c = managedQuery(table, null, null, null, null);
        } else {
            CursorLoader cursorLoader = new CursorLoader(this,table, null, null, null, null);
            c = cursorLoader.loadInBackground();
        }

        //*********************
        //PRIKAZI ZADATKE U MAIN_AC
        //AKTIVNI & ZAVRSENI
        //*********************
        LinearLayout plannedTasks = (LinearLayout)findViewById(R.id.plannedTasksLayout);
        LinearLayout doneTasks = (LinearLayout)findViewById(R.id.doneTasksLayout);

        if (c.moveToFirst()) {
            do{
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View taskLayout = inflater.inflate(R.layout.listview_item, null);

                TextView taskName = (TextView) taskLayout.findViewById(R.id.taskName);
                taskName.setText(c.getString(c.getColumnIndex(DataBase.TASK_NAME)));

                TextView taskTime = (TextView) taskLayout.findViewById(R.id.taskDate);
                taskTime.setText(c.getString(c.getColumnIndex(DataBase.TASK_TIME)));

                TextView taskDeadline = (TextView) taskLayout.findViewById(R.id.taskDeadline);
                taskTime.setText(c.getString(c.getColumnIndex(DataBase.TASK_DEADLINE)));

                ImageView priorityImg = (ImageView) taskLayout.findViewById((R.id.priorityImg));
                switch (c.getInt(4)){
                    case 1 :
                        priorityImg.setImageResource(R.drawable.crveni);
                        break;
                    case 2:
                        priorityImg.setImageResource(R.drawable.zuti);
                        break;
                    case 3:
                        priorityImg.setImageResource(R.drawable.zeleni);
                        break;
                    default:
                        priorityImg.setImageResource(R.drawable.sivi);
                        break;
                }

                switch (c.getInt(c.getColumnIndex(DataBase.TASK_DONE))){
                    case 1 :
                        doneTasks.addView(taskLayout);
                        break;
                    case 0 :
                        plannedTasks.addView(taskLayout);
                        break;
                    default:
                        break;
                }

            } while (c.moveToNext());
        }

        Toast.makeText(this, "ZAVRSIO onResume", Toast.LENGTH_LONG).show();
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_category) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }


    // Otvaramo filter/sort menu
    public void SortBy(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(Gravity.LEFT);
    }

    public void FilterBy(View view) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.openDrawer(Gravity.LEFT);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_deadlineDown) {
            // Handle the camera action
        } else if (id == R.id.nav_deadlineUp) {

        } else if (id == R.id.nav_plannedDown) {

        } else if (id == R.id.nav_plannedUp) {

        } else if (id == R.id.nav_priorityDown) {

        } else if (id == R.id.nav_priorityUp) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void deleteAllDone(MenuItem item) {
        new AlertDialog.Builder(this)
                .setTitle("Title")
                .setMessage("Do you really want to delete all completed tasks?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        db.open();
                        db.deleteAllCopmletedTasks();
                        db.close();
                        Toast.makeText(MainActivity.this, "Completed tasks have ben deleted successfully!", Toast.LENGTH_SHORT).show();
                    }})
                .setNegativeButton(android.R.string.no, null).show();
    }




    //funkcija za ispis
    public void DisplayTask(Cursor c)
    {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Name: " + c.getString(1) + "\n" +
                        "time:  " + c.getString(2) + "\n" +
                        "deadline: " + c.getString(3) + "\n" +
                        "category: " + c.getString(4) + "\n" +
                        "priority  " + c.getString(5) +
                        "Name: " + c.getString(1) + "\n" ,
                        //"time:  " + c.getString(2),
                Toast.LENGTH_LONG).show();
    }

    //uredi kategorije
    public void updateCategoryClick(MenuItem item) {
        Intent i = new Intent(MainActivity.this, EditCategories.class);
        Bundle b = new Bundle();
        b.putSerializable("EXTRA_MESSAGE",db);
        i.putExtras(b);
        //i.putExtra(&quot;DBAdapterObject&quot;, db);
        startActivity(i);
    }
}

