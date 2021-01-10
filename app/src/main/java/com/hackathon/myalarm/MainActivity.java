package com.hackathon.myalarm;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;


public class MainActivity extends Activity {
    private TimePicker alarmTime;
    private TextView currentTime;
    private Calendar calendar;
    private String format = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alarmTime = (TimePicker) findViewById(R.id.timePicker1);
        currentTime = (TextView) findViewById(R.id.textView1);
        calendar = Calendar.getInstance();
        final Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);
        showTime(hour, min);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (currentTime.getText().toString().equals(AlarmTime())) {
                    r.play();
                } else {
                    r.stop();
                }
            }
        }, 0, 1000);
    }


    public String AlarmTime() {
        Integer alarmHours = alarmTime.getCurrentHour();
        Integer alarmMinutes  = alarmTime.getCurrentMinute();
        String stringAlarmMinutes;

        if (alarmMinutes<10) {
            stringAlarmMinutes = "0";
            stringAlarmMinutes = stringAlarmMinutes.concat(alarmMinutes.toString());
        } else {
            stringAlarmMinutes = alarmMinutes.toString();
        }

        String stringAlarmTime = null;
        if(alarmHours > 12) {
            alarmHours = alarmHours - 12;
            stringAlarmTime = alarmHours.toString().concat(":").concat(stringAlarmMinutes).
                    concat(" PM");
        }else{
            stringAlarmTime = alarmHours.toString().concat(":").concat(stringAlarmMinutes).
                    concat(" AM");
        }

        return stringAlarmTime;

    }

    public void setCurrentTime(View view) {
        int hour = alarmTime.getCurrentHour();
        int min = alarmTime.getCurrentMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        currentTime.setText(new StringBuilder().append(hour).append(" : ").append(min)
                .append(" ").append(format));
    }


}
