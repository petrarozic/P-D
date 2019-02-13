package com.pnd.future_bosses.plannedanddone;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContentResolverCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.ContextThemeWrapper;
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

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity
        //implements NavigationView.OnNavigationItemSelectedListener
{

    public DBAdapter db;
    List<Integer> taskID;
    final public int notificationID = 13;
    AlarmManager am;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // notifikacije :
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Intent intent1 = new Intent(MainActivity.this, NotificationBuilder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        int curHr = now.get(Calendar.HOUR_OF_DAY);
        int curMin = now.get(Calendar.MINUTE);
        if ((curHr > 9)||(curHr == 9 && curMin > 0))
        {
                calendar.add(Calendar.DATE, 1);
        }


        am = (AlarmManager) MainActivity.this.getSystemService(MainActivity.this.ALARM_SERVICE);
        am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);


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


        //za onaj dio koji je zakomentiran i ne koristimo :)
        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);


        //********************************************
        //STVARANJE ZADATAKA:
        //********************************************
        //insertTask("zad1", "VRIJEME1", "KRAJNJE_VRIJEME1", 1, 0, 0);
        //insertTask("zad2", "VRIJEME2", "KRAJNJE_VRIJEME2", 2, 0, 0);
        //insertTask("zad3", "VRIJEME3", "KRAJNJE_VRIJEME3", 3, 0, 1);

        //********************************************
        //DOHVATI ZADATKE:
        //********************************************
        Cursor c;
        Uri table = Uri.parse("content://hr.math.provider.contprov/task");
        if (android.os.Build.VERSION.SDK_INT < 11) {
            c = managedQuery(table, null, null, null, null);
        } else {
            CursorLoader cursorLoader = new CursorLoader(this, table, null, null, null, null);
            c = cursorLoader.loadInBackground();
        }


        // Punjenje za probu : Category
        // dodaj kategoriju
        ContentValues values = new ContentValues();
        values.clear();
        values.put("name", "Home");
        Uri uri2 = getContentResolver().insert(
                Uri.parse("content://hr.math.provider.contprov/category"), values);

        Uri table2 = Uri.parse(
                "content://hr.math.provider.contprov/category");
        // Ispis
        if (android.os.Build.VERSION.SDK_INT < 11) {
            //---before Honeycomb---
            c = managedQuery(table2, null, null, null, null);
        } else {
            //---Honeycomb and later---
            CursorLoader cursorLoader = new CursorLoader(
                    this,
                    table2, null, null, null, null);
            c = cursorLoader.loadInBackground();
        }
/*
        if (c.moveToFirst()) {
            do{
                Toast.makeText(this,
                        c.getString(c.getColumnIndex(DataBase.CATEGORY_ID)) + ", " +
                                c.getString(c.getColumnIndex(DataBase.CATEGORY_NAME)),
                        Toast.LENGTH_SHORT).show();
            } while (c.moveToNext());
        }

*/

        /*
        //TOAST
        if (c.moveToFirst()) {
            do{
                DisplayTask(c);
            } while (c.moveToNext());
        }
         */

        printTasks();
    }


    public void pretraziBazu(View v) {
        Toast.makeText(this, "Klik na PRETRAZI BAZU", Toast.LENGTH_LONG).show();
        //MenuItem item = (MenuItem)findViewById(R.id.nav_deadlineDown) ;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (id == R.id.action_notifications) {

            return true;
        }


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

    /*
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

        //sljedeca dva retka otvaraju sideBar
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    */

    public void deleteAllDone(MenuItem item) {

        new AlertDialog.Builder(this)
                .setTitle("Delete tasks")
                .setMessage("Do you really want to delete all completed tasks?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Uri table = Uri.parse("content://hr.math.provider.contprov/task");
                        String where = DataBase.TASK_DONE + "=1";

                        int count = getContentResolver().delete(table, where, null);

                        if (count > 0)
                            Toast.makeText(MainActivity.this, R.string.completed_tasks_deleted_success, Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(MainActivity.this, R.string.completed_tasks_deleted_not_success, Toast.LENGTH_SHORT).show();
                        printTasks();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();

    }

    //funkcija za ispis
    public void DisplayTask(Cursor c) {
        Toast.makeText(this,
                "id: " + c.getString(0) + "\n" +
                        "Name: " + c.getString(1) + "\n" +
                        "time:  " + c.getString(2) + "\n" +
                        "deadline: " + c.getString(3) + "\n" +
                        "category: " + c.getString(4) + "\n" +
                        "priority  " + c.getString(5) +
                        "Name: " + c.getString(1) + "\n",
                //"time:  " + c.getString(2),
                Toast.LENGTH_LONG).show();
    }

    //uredi kategorije
    public void updateCategoryClick(MenuItem item) {
        Intent i = new Intent(MainActivity.this, EditCategories.class);
        startActivity(i);
    }

    public void printTasks() {
        //*********************
        //DOHVATI SVE ZADATKE
        //*********************
        Cursor c;
        taskID = new ArrayList<Integer>();
        Uri table = Uri.parse("content://hr.math.provider.contprov/task");
        if (android.os.Build.VERSION.SDK_INT < 11) {
            c = managedQuery(table, null, null, null, null);
        } else {
            CursorLoader cursorLoader = new CursorLoader(this, table, null, null, null, null);
            c = cursorLoader.loadInBackground();
        }

        //*********************
        //PRIKAZI ZADATKE U MAIN_AC
        //AKTIVNI & ZAVRSENI
        //*********************
        LinearLayout plannedTasks = (LinearLayout) findViewById(R.id.plannedTasksLayout);
        plannedTasks.removeAllViews();
        LinearLayout doneTasks = (LinearLayout) findViewById(R.id.doneTasksLayout);
        doneTasks.removeAllViews();

        if (c.moveToFirst()) {
            do {
                //DisplayTask(c);

                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View taskLayout = inflater.inflate(R.layout.listview_item, null);
                taskLayout.setTag(c.getInt(c.getColumnIndex(DataBase.TASK_ID)));
                CheckBox check = (CheckBox) taskLayout.findViewById((R.id.checkBox));
                check.setTag(c.getInt(c.getColumnIndex(DataBase.TASK_ID)));


                ImageButton editButton = (ImageButton) taskLayout.findViewById(R.id.editButton);
                editButton.setTag(c.getInt(c.getColumnIndex(DataBase.TASK_ID)));

                TextView taskName = (TextView) taskLayout.findViewById(R.id.taskName);
                taskName.setText(c.getString(c.getColumnIndex(DataBase.TASK_NAME)));

                TextView taskTime = (TextView) taskLayout.findViewById(R.id.taskDate);
                String planned_ = c.getString(c.getColumnIndex(DataBase.TASK_TIME));
                String planned = "-";
                if(!planned_.equals(""))
                     planned = planned_.substring(6,8) + "/" + planned_.substring(4,6) + "/" + planned_.substring(0,4) + ", " + planned_.substring(8,10) + ":" + planned_.substring(10,12);
                taskTime.setText(taskTime.getText() + planned);

                TextView taskDeadline = (TextView) taskLayout.findViewById(R.id.taskDeadline);
                String deadline_ = c.getString(c.getColumnIndex(DataBase.TASK_DEADLINE));
                String deadline = "-";
                if(!deadline_.equals(""))
                    deadline = deadline_.substring(6,8) + "/" + deadline_.substring(4,6) + "/" + deadline_.substring(0,4) + ", " + deadline_.substring(8,10) + ":" + deadline_.substring(10,12);
                taskDeadline.setText(taskDeadline.getText() + deadline);

                ImageView priorityImg = (ImageView) taskLayout.findViewById((R.id.priorityImg));
                switch (c.getInt(4)) {
                    case 1:
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

                switch (c.getInt(c.getColumnIndex(DataBase.TASK_DONE))) {
                    case 1:
                        editButton.setEnabled(false);
                        check.setChecked(true);
                        doneTasks.addView(taskLayout);
                        break;
                    case 0:
                        plannedTasks.addView(taskLayout);
                        break;
                    default:
                        break;
                }


                // Event handler za long click na task : brisanje zadatka
                taskLayout.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        final int id = (int) ((LinearLayout) v).getTag();
                        new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, R.style.myDialog))
                                .setTitle("Delete task")
                                .setMessage("Do you really want to delete this task?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Uri table = Uri.parse("content://hr.math.provider.contprov/task");
                                        String where = DataBase.TASK_ID + "=" + id;
                                        getContentResolver().delete(table, where, null);
                                        printTasks();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();

                        return true;
                    }

                });

            } while (c.moveToNext());
        }

        if(plannedTasks.getChildCount() == 0) {
            TextView taskName1 = new TextView(this); //) noPlannedTasks.findViewById(R.id.taskName);
            taskName1.setText("Get yourself organized and start planning your tasks!");
            taskName1.setTextSize(20);
            taskName1.setGravity(Gravity.CENTER);
            plannedTasks.addView(taskName1);
        }
        if(doneTasks.getChildCount() == 0) {
            TextView taskName2 = new TextView(this); //) noDoneTasks.findViewById(R.id.taskName);
            taskName2.setTextSize(20);
            taskName2.setText("This is where all your done tasks will be displayed.");
            taskName2.setGravity(Gravity.CENTER);

            doneTasks.addView(taskName2);

        }
    }

    public boolean insertTask(String ime, String time, String deadline, int priority, int category, int done) {
        //dodati validaciju podataka?
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


    public void editTask(View view) {
        int id = (int)((ImageButton)view).getTag();
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        intent.putExtra("FLAG", id);
        startActivity(intent);
    }

    public void checkedTask(View view) {
        int doneID = (int) ((CheckBox) view).getTag();
        boolean checked = ((CheckBox) view).isChecked();
        ContentValues values = new ContentValues();
        Uri table = Uri.parse("content://hr.math.provider.contprov/task");
        String where = DataBase.TASK_ID + "=" + doneID;
        Cursor c = getContentResolver().query(table,
                new String[]{DataBase.TASK_ID, DataBase.TASK_NAME, DataBase.TASK_TIME, DataBase.TASK_DEADLINE, DataBase.TASK_PRIORITY, DataBase.TASK_CATEGORY}, where, null, null);
        if (c.moveToFirst()) {

            values.put("name", c.getString(1));
            values.put("time", c.getString(2));
            values.put("deadline", c.getString(3));
            values.put("priority", c.getInt(4));
            values.put("category", c.getInt(5));

            if (checked) {
                values.put("done", 1);
                getContentResolver().update(table, values, where, null);
            } else {
                values.put("done", 0);
                getContentResolver().update(table, values, where, null);
            }
        }
        printTasks();
    }

    public void pomodoro(MenuItem item) {
        Intent intent = new Intent(MainActivity.this, Pomodoro.class);
        startActivity(intent);
    }


    public void filterAndSort(View view){
        Intent intent = new Intent(MainActivity.this, FilterAndSortActivity.class);
        startActivity(intent);

    }

    public void manageNotifications(MenuItem item) {
        if (item.isChecked()){
            item.setChecked(false);
            Intent intent1 = new Intent(MainActivity.this, NotificationBuilder.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            am.cancel(pendingIntent);
        }
        else {
            item.setChecked(true);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 8);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            Calendar now = Calendar.getInstance();
            now.setTimeInMillis(System.currentTimeMillis());
            int curHr = now.get(Calendar.HOUR_OF_DAY);
            int curMin = now.get(Calendar.MINUTE);
            if ((curHr > 9)||(curHr == 9 && curMin > 0))
            {
                calendar.add(Calendar.DATE, 1);
            }

            Intent intent1 = new Intent(MainActivity.this, NotificationBuilder.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,intent1, PendingIntent.FLAG_UPDATE_CURRENT);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }
    }

}

