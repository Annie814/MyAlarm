package com.hackathon.myalarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Calendar;

import static android.provider.ContactsContract.CommonDataKinds.Organization.TITLE;

@Entity(tableName = "alarm_table")
public class Alarm {

//    @PrimaryKey
        @NonNull
        private int alarmId;

        private int hour, minute;
        private boolean started, recurring;
        private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;
        private String title;

//        public Alarm(int alarmId, int hour, int minute, String title, boolean started,
//                     boolean recurring, boolean monday, boolean tuesday, boolean wednesday,
//                     boolean thursday, boolean friday, boolean saturday, boolean sunday)

    public Alarm (int alarmId, int hour, int minute, String title, boolean started) {
            this.alarmId = alarmId;
            this.hour = hour;
            this.minute = minute;
            this.started = started;

//            this.recurring = recurring;

//            this.monday = monday;
//            this.tuesday = tuesday;
//            this.wednesday = wednesday;
//            this.thursday = thursday;
//            this.friday = friday;
//            this.saturday = saturday;
//            this.sunday = sunday;

            this.title = title;
        }

        public void schedule(Context context) {
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
//            intent.putExtra(RECURRING, recurring);
//            intent.putExtra(MONDAY, monday);
//            intent.putExtra(TUESDAY, tuesday);
//            intent.putExtra(WEDNESDAY, wednesday);
//            intent.putExtra(THURSDAY, thursday);
//            intent.putExtra(FRIDAY, friday);
//            intent.putExtra(SATURDAY, saturday);
//            intent.putExtra(SUNDAY, sunday);

            intent.putExtra(TITLE, title);

            PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            // if alarm time has already passed, increment day by 1
            if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
            }
            String toastText = String.format("Recurring Alarm %s scheduled for %s at %02d:%02d", title, hour, minute, alarmId);
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            final long RUN_DAILY = 24 * 60 * 60 * 1000;
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    RUN_DAILY,
                    alarmPendingIntent
            );

//            if (!recurring) {
//                String toastText = null;
//                try {
//                    toastText = String.format("One Time Alarm %s scheduled for %s at %02d:%02d", title, DayUtil.toDay(calendar.get(Calendar.DAY_OF_WEEK)), hour, minute, alarmId);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();
//
//                alarmManager.setExact(
//                        AlarmManager.RTC_WAKEUP,
//                        calendar.getTimeInMillis(),
//                        alarmPendingIntent
//                );
//            } else {
//                String toastText = String.format("Recurring Alarm %s scheduled for %s at %02d:%02d", title, getRecurringDaysText(), hour, minute, alarmId);
//                Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();
//
//                final long RUN_DAILY = 24 * 60 * 60 * 1000;
//                alarmManager.setRepeating(
//                        AlarmManager.RTC_WAKEUP,
//                        calendar.getTimeInMillis(),
//                        RUN_DAILY,
//                        alarmPendingIntent
//                );

            this.started = true;
        }
    }

