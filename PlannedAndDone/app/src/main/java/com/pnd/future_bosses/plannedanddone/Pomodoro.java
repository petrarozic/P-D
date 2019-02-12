package com.pnd.future_bosses.plannedanddone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Pomodoro extends AppCompatActivity {

    int timeMin = 0;
    int timeSec = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);
    }

    public void pomoLong(View view) {
        timeMin = 10;
        startTimer();
    }

    public void pomoShort(View view) {
        timeMin = 05;
        startTimer();
    }

    public void pomoWork(View view) {
        timeMin = 25;
        startTimer();
    }
    private void startTimer(){
        TextView min = (TextView)findViewById(R.id.minutes);
        TextView sec = (TextView)findViewById(R.id.seconds);
        String mins, secs;

        if(timeMin<10){
            //nalijepi 0 na pocetak
            mins = "0"+timeMin;
        }
        else mins = Integer.toString(timeMin);
        if(timeSec<10){
            //nalijepi 0 na pocetak
            secs = "0"+timeSec;
        }
        else secs = Integer.toString(timeSec);

        min.setText(mins);
        sec.setText(secs);
        //odbrojavanje -> svaku sekundu promijeni stanje sekundi, svaku min ->stanje minuta
        //prekopirati ono s timerom
    }

}
