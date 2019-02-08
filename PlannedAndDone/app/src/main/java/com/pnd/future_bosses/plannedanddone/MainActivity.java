package com.pnd.future_bosses.plannedanddone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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


        //---add a contact---
        db.open();
        long id = db.insertTask("Šetnja psa", "12.3.2019.", "hhh", 1, 3, 1);
        id = db.insertTask("Učiti izračunljivost", "9.2.2019", "hhsdfh", 3, 4, 0);
        id = db.insertTask("Projekti", "9.2.2019", "hhsdfh", 5, 4, 0);


        Cursor cu = db.getAllTasks();


        if (cu.moveToFirst())
        {
            do {
                DisplayTask(cu);
            } while (cu.moveToNext());
        }
        db.close();

    }


    @Override
    public void onResume() {
        super.onResume();
        db.open();

        Cursor CplannedTasks = db.getAllTasks();

        LinearLayout plannedTasks = (LinearLayout)findViewById(R.id.plannedTasksLayout);

        if (CplannedTasks.moveToFirst()){
            do{
                // dohvati definirani xml layout za task
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View taskLayout = inflater.inflate(R.layout.listview_item, null);

                TextView taskName = (TextView) taskLayout.findViewById(R.id.taskName);
                taskName.setText(CplannedTasks.getString(1));

                TextView taskTime = (TextView) taskLayout.findViewById(R.id.taskDate);
                taskTime.setText(CplannedTasks.getString(2));

                TextView taskDeadline = (TextView) taskLayout.findViewById(R.id.taskDeadline);
                taskTime.setText(CplannedTasks.getString(3));

                ImageView priorityImg = (ImageView) taskLayout.findViewById((R.id.priorityImg));
                switch (CplannedTasks.getInt(4)){
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

                plannedTasks.addView(taskLayout);

            }while(CplannedTasks.moveToNext());
        }

        Toast.makeText(this,
                "U onResume",
                Toast.LENGTH_LONG).show();
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
                        "priority  " + c.getString(5),
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

