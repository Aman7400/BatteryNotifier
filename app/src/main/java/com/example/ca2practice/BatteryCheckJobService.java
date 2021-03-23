package com.example.ca2practice;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BatteryCheckJobService extends JobService {


    private Context context;
    Intent i;
    PendingIntent pi;


    @Override
    public boolean onStartJob(JobParameters params) {


        context = getApplicationContext();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, filter);





        /* Check if Battery Level is 98 and Device is  Charging to raise Alarm and Notification */

        if (getBatteryLevel(batteryStatus) == 100 && isCharging(batteryStatus)) {
            setAlarm();
            displayNotification("Battery is 98%");


        }

        /* Check if Battery Level is less than 20 and Device is not Charging to raise Alarm and Notification */


        if (getBatteryLevel(batteryStatus) <= 20 && !isCharging(batteryStatus)) {
            setAlarm();
            displayNotification("Battery getting low");
        }


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }


    /* Create A Notification */

    public void displayNotification(String msg) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel nc = new NotificationChannel("myChannel", "My Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager nm = getSystemService(NotificationManager.class);
            nm.createNotificationChannel(nc);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "myChannel")
                .setContentText(msg)
                .setSmallIcon(R.drawable.ic_baseline_battery_alert_24)
                .setContentTitle("Battery Helper")
                .setAutoCancel(true);

        NotificationManagerCompat nmc = NotificationManagerCompat.from(this);
        nmc.notify(999, builder.build());


    }

    /*  Get Current Battery Level */

    public int getBatteryLevel(Intent batteryStatus) {

        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);

        return level;
    }

    /* Get Device Status of Charging */

    public boolean isCharging(Intent batteryStatus) {

        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        return status == BatteryManager.BATTERY_STATUS_CHARGING;


    }

    /* Set Alarm For Battery Notification  */

    public void setAlarm() {

        i = new Intent(context, BatteryAlarmReceiver.class);
        pi = PendingIntent.getBroadcast(context, 001, i, 0);

        AlarmManager am = (AlarmManager) getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC, 1, pi);
    }

}


