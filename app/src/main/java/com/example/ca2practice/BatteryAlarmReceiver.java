package com.example.ca2practice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class BatteryAlarmReceiver extends BroadcastReceiver {

    private MediaPlayer mp;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onReceive(Context context, Intent intent) {

        mp= MediaPlayer.create(context, R.raw.alarm);
        mp.start();


    }
}
