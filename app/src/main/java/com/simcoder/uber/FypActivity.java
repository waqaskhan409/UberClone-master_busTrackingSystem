package com.simcoder.uber;

import android.app.Activity;
import android.app.Application;

import com.google.firebase.FirebaseApp;

public class FypActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
