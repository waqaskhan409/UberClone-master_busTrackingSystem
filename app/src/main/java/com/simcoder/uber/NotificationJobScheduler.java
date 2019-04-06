package com.simcoder.uber;

import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobScheduler extends JobService {
    private static final String TAG = "NotificationJobSchedule";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("notifications");


    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.d(TAG, "onStartJob: Succcesfully start");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.d(TAG, "run: kjjkjkjkjkjkj");



                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.i)
                                notificationLauncher(dataSnapshot.child("message_title").getValue().toString(),
                                        dataSnapshot.child("message_detail").getValue().toString());
                                Log.d(TAG, "onDataChange: on Change Called");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    Thread.sleep(1000);
                    }catch(InterruptedException e){
                        e.printStackTrace();
                    }

                jobFinished(jobParameters, true);
            }
        }).start();



        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.d(TAG, "onStopJob: Stop");
        return false;
    }
    void notificationLauncher(String message_title, String value){
        Log.d(TAG, "notificationLauncher: Launched");
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
        notification.setSmallIcon(android.R.drawable.ic_notification_clear_all);
        notification.setContentTitle(message_title);
        notification.setContentText(value);
        notification.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));
        notification.setPriority(NotificationCompat.PRIORITY_HIGH);
        notification.setCategory(NotificationCompat.CATEGORY_MESSAGE);
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(1, notification.build());
    }
}
