package com.simcoder.uber;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import java.nio.channels.Channel;

public class AppNotification extends Application {
    public static final String CHANEL_1 = "Channel 1";
    @Override
    public void onCreate() {
        super.onCreate();
        notifyNotication();
    }

    private void notifyNotication() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    CHANEL_1,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            notificationChannel.setDescription("alisudaoshdajshdalksdajkh");
        }
    }
}
