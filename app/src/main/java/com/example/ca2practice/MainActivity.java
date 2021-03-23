package com.example.ca2practice;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


private Button mStart , mStop;
private JobScheduler mScheduler ; private JobInfo mInfo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStart = findViewById(R.id.startJob);
        mStart.setOnClickListener(v -> {

            startJob();


        });

        mStop = findViewById(R.id.stopJob);
        mStop.setOnClickListener(v -> {

            stopJob();
            
        });


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void stopJob() {
        mScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);
        mScheduler.cancel(001);
        Toast.makeText(this, "Job Schedule Cancelled", Toast.LENGTH_SHORT).show();


    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void startJob() {

        /* Create Job Scheduler*/


        mScheduler = (JobScheduler)getSystemService(JOB_SCHEDULER_SERVICE);

        ComponentName cn = new ComponentName(this,BatteryCheckJobService.class);

        /* Create Job Info */

 mInfo = new JobInfo.Builder(001,cn)
         .setPeriodic(20 * 60 * 1000)  // Set A Job to Repeat every 20 min
         .setPersisted(true)
         .build();

        int scheduleResultCode = mScheduler.schedule(mInfo);

        if(scheduleResultCode == JobScheduler.RESULT_SUCCESS ){
            Toast.makeText(this, "Job Scheduled Successfully", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Job Scheduling Failed", Toast.LENGTH_SHORT).show();

        }


    }
}