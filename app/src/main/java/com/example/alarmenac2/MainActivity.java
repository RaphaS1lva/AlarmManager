package com.example.alarmenac2;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    private ToggleButton toggleButton;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    @SuppressLint("ServiceCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timePicker = findViewById(R.id.timepicker);
        toggleButton = findViewById(R.id.togglebtn);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()) {
                    Toast.makeText(MainActivity.this, "Alarme Adicionado", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent(MainActivity.this, AlarmReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, i, 0);

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                    calendar.set(Calendar.MINUTE, timePicker.getHour());

                    long time = calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000);

                    if (System.currentTimeMillis() > time) {
                        if (Calendar.AM_PM == 0) {
                            time = time + (1000 * 60 * 60 * 12);
                        } else {
                            time = time + (1000 * 60 * 60 * 24);
                        }
                    }
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time,0, pendingIntent);
                }

                else {
                    alarmManager.cancel(pendingIntent);
                    Toast.makeText(MainActivity.this, "Alarme Deletado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
