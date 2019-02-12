package com.pnd.future_bosses.plannedanddone;

import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Pomodoro extends AppCompatActivity {

    String ms, ss;
    int timeMin = 0;
    int timeSec = 0;
    CountDownTimer ti;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro);
    }

    public void pomoLong(View view) {

        if(ti != null)
            ti.cancel();
        timeMin = 10;
        setTimer();
    }

    public void pomoShort(View view) {

        if(ti != null)
            ti.cancel();
        timeMin = 5;
        setTimer();
    }

    public void pomoWork(View view) {

        if(ti != null)
            ti.cancel();
        timeMin = 25;
        setTimer();
    }

    private void setTimer(){
        TextView min = (TextView)findViewById(R.id.minutes);
        TextView sec = (TextView)findViewById(R.id.seconds);
        String mins, secs;

        if(timeMin<10){
            //nalijepi 0 na pocetak
            mins = "0"+timeMin;
        }
        else mins = Integer.toString(timeMin);
        int timeMin = 0;
        if(timeSec<10){
            //nalijepi 0 na pocetak
            secs = "0"+timeSec;
        }
        else secs = Integer.toString(timeSec);

        min.setText(mins);
        sec.setText(secs);

    }

    public void resetTimer(View view) {
        //postavi timer na prije odabranu vrijednost

        if(ti != null)
            ti.cancel();
        setTimer();
    }

    public void pauseTimer(View view) {
        //zaustavi timer

        try {
            //spremi vrijeme u neku varijablu
            ti.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stratTimer(View view) {
        //provjeri je li timer pauziran i ako je pokreni ga s perthodnim vremenom
        //ti.start();

        //inace pokreni timer od zadanog vremena
        final TextView m = (TextView) findViewById( R.id.minutes );
        final TextView s = (TextView) findViewById( R.id.seconds );

        if(ti != null)
            ti.cancel();

        ti = new CountDownTimer(timeMin * 60000, 1000) { // adjust the milli seconds here

            public void onTick(long millisUntilFinished) {

                if(TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished)<10){
                    //nalijepi 0 na pocetak
                    ms = "0"+(TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished));
                }
                else ms = Long.toString(TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished));

                if(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))<10){
                    //nalijepi 0 na pocetak
                    ss = "0"+(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                }
                else ss = Long.toString(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

                m.setText(ms);
                s.setText(ss);

            }

            public void onFinish() {
                ((TextView) findViewById( R.id.col )).setTextColor(Color.RED);
                m.setTextColor(Color.RED);
                s.setTextColor(Color.RED);
            }
        }.start();
      //  Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        //v.vibrate(500);
    }
}
